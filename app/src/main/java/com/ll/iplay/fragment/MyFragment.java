package com.ll.iplay.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ll.iplay.activity.LoginActivity;
import com.ll.iplay.activity.R;
import com.ll.iplay.activity.UserInforSettingActivity;
import com.ll.iplay.common.Constants;
import com.ll.iplay.gson.User;
import com.ll.iplay.handler.UserHandler;
import com.ll.iplay.util.BitmapUtils;
import com.ll.iplay.util.FileUtils;
import com.ll.iplay.util.HttpUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by ll on 2017/3/12.
 */

public class MyFragment extends Fragment {

    private TextView userInforSetting, userLogout, userNickName;
    private ImageView userImage;

    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_center,container, false);
        userInforSetting = (TextView) view.findViewById(R.id.id_user_infor_settings);
        userLogout = (TextView) view.findViewById(R.id.id_user_logout);
        userImage = (ImageView) view.findViewById(R.id.id_user_head_pic);
        userNickName = (TextView) view.findViewById(R.id.id_user_nick_name);

        setListener();
        setData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        User user = UserHandler.handleUserLoginResponse(prefs.getString(Constants.USER, null));
        if (user != null) {
            Glide
                .with(this)
                .load(user.getHeadPicUrl())
                .centerCrop() // this cropping technique scales the image so that it fills the requested bounds and then crops the extra.
                .into(userImage);
            userNickName.setText(user.getNickName());
        } else {
            Toast.makeText(getContext(), "用户数据丢失", Toast.LENGTH_SHORT).show();
        }
    }

    private void setListener() {

        userInforSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UserInforSettingActivity.class);
                startActivity(intent);
            }
        });
        userLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                editor.remove(Constants.USER);
                editor.apply();
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                Toast.makeText(getContext(), "退出成功", Toast.LENGTH_LONG).show();
            }
        });
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallery(9999);
            }
        });
    }
    /**
     * 从相册获取
     */
    public void gallery(int requestCode) {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, requestCode);
    }
    /**
     * 压缩图片
     */
    private File saveAndNoUpload(String imageUrl) {
        Bitmap bitmap = BitmapUtils.getimage(imageUrl);
        File file = BitmapUtils.saveBitmap(bitmap, "post", System.currentTimeMillis() + ".jpg");
        bitmap.recycle();
        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String url = Constants.REQUEST_PREFIX + "user/uploadUserImg";
        if (requestCode == 9999 && resultCode == getActivity().RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                Log.d("uri", data.getData().toString());
                File file = saveAndNoUpload(FileUtils.getRealFilePath(getContext(), uri));
                HttpUtil.sendOkHttpRequestFileByPost(url, HttpUtil.MEDIA_TYPE_JPG, file, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), Constants.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String responseText = response.body().string();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide
                                    .with(getContext())
                                    .load(responseText)
                                    .centerCrop()
                                    .into(userImage);
                            }
                        });
                        String url = Constants.REQUEST_PREFIX + "user/updateUserInfos";
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                        User user = UserHandler.handleUserLoginResponse(prefs.getString(Constants.USER, null));
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("userId",String.valueOf(user.getId()));
                        params.put("headPicUrl", responseText);
                        params.put("appKey", Constants.APP_KEY);
                        HttpUtil.sendOkHttpRequestByPost(url, params, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), "修改失败", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String responseText = response.body().string();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                                        editor.putString(Constants.USER, responseText);
                                        editor.apply();
                                        Toast.makeText(getContext(), "修改成功", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }
    }
}
