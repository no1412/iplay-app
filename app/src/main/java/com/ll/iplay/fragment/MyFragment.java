package com.ll.iplay.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ll.iplay.activity.LoginActivity;
import com.ll.iplay.activity.R;
import com.ll.iplay.common.Constants;
import com.ll.iplay.gson.User;
import com.ll.iplay.handler.UserHandler;

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
    }


}
