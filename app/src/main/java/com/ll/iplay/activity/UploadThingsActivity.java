package com.ll.iplay.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ll.iplay.common.Constants;
import com.ll.iplay.gson.User;
import com.ll.iplay.handler.UserHandler;
import com.ll.iplay.util.BitmapUtils;
import com.ll.iplay.util.FileUtils;
import com.ll.iplay.util.HttpUtil;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;

import java.io.File;
import java.io.IOException;

import jp.wasabeef.richeditor.RichEditor;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadThingsActivity extends AppCompatActivity implements OnClickListener {

    private static final String PHOTO_FILE_NAME = "take_photo.jpg";

    private EditText uploadTitle;
    private LinearLayout camearLayout, galleryLayout;
    private ImageView uploadBg, uploadBgImage, backImage, publishImage;
    private RichEditor mEditor;

    private Form form;

    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_things);
        initView();
        setListener();
        validateForm();
    }


    private void initView() {
        uploadTitle = (EditText) findViewById(R.id.id_upload_title);
        backImage = (ImageView) findViewById(R.id.id_back_image);
        publishImage = (ImageView) findViewById(R.id.id_publish_image);
        uploadBg = (ImageView) findViewById(R.id.id_upload_bg);
        uploadBgImage = (ImageView) findViewById(R.id.id_upload_bg_image);
        mEditor = (RichEditor) findViewById(R.id.id_upload_content);
        camearLayout = (LinearLayout) findViewById(R.id.id_layout_camera);
        galleryLayout = (LinearLayout) findViewById(R.id.id_layout_gallery);
        mEditor.setPlaceholder("请填写内容");
    }

    public void setListener() {
        uploadBgImage.setOnClickListener(this);
        backImage.setOnClickListener(this);
        publishImage.setOnClickListener(this);
        camearLayout.setOnClickListener(this);
        galleryLayout.setOnClickListener(this);
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
               content = text;
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
     * 从相机获取
     */
    public void camera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/post/",
                            PHOTO_FILE_NAME)));
        }
        startActivityForResult(intent, 9999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String url = Constants.REQUEST_PREFIX + "upload/uploadImg";
        if (requestCode == 9998 && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                Log.d("uri", data.getData().toString());
                File file = saveAndNoUpload(FileUtils.getRealFilePath(this, uri));
                HttpUtil.sendOkHttpRequestFileByPost(url, HttpUtil.MEDIA_TYPE_JPG, file, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UploadThingsActivity.this, Constants.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String responseText = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mEditor.insertImage(responseText, "");//第二个参数为图片描述
                            }
                        });
                    }
                });
                //mEditor.insertImage(file.getAbsolutePath(), "");//第二个参数为图片描述
            }
        }
        if (requestCode == 9999 && resultCode == RESULT_OK) {
            File file = new File(Environment.getExternalStorageDirectory() + "/post/",
                    PHOTO_FILE_NAME);
            File newFile  = saveAndNoUpload(file.getAbsolutePath());
            HttpUtil.sendOkHttpRequestFileByPost(url, HttpUtil.MEDIA_TYPE_JPG, newFile, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(UploadThingsActivity.this, Constants.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseText = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mEditor.insertImage(responseText, "");//第二个参数为图片描述
                        }
                    });
                }
            });
        }
        if (requestCode == 9997 && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                Log.d("uri", data.getData().toString());
                File file = saveAndNoUpload(FileUtils.getRealFilePath(this, uri));
            }
        }
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

    public void publish() {
        boolean flag = form.validate();
        if (flag) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            User user = UserHandler.handleUserLoginResponse(prefs.getString(Constants.USER, null));
            if (user != null) {

            }

        } else {
            Toast.makeText(UploadThingsActivity.this, Constants.FORM_WRONG_MSG, Toast.LENGTH_LONG).show();
        }

    }

    private void validateForm() {
        // 1. 先创建个表单Form类用来装控件
        form = new Form();
    }

    public void back() {
        UploadThingsActivity.this.finish();
        Validate titleValidate = new Validate(uploadTitle);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_back_image :
                back();
                break;
            case R.id.id_publish_image :
                publish();
                break;
            case R.id.id_layout_camera :
                camera();
                break;
            case R.id.id_layout_gallery :
                gallery(9998);
                break;
            case R.id.id_upload_bg_image :
                gallery(9997);
                break;
            default:
                break;
        }
    }
}
