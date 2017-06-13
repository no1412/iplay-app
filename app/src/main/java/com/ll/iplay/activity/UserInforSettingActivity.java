package com.ll.iplay.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ll.iplay.common.Constants;
import com.ll.iplay.gson.User;
import com.ll.iplay.handler.UserHandler;
import com.ll.iplay.util.StringUtil;

public class UserInforSettingActivity extends AppCompatActivity {

    private TextView userNickName, userEmail, userPhone;

    private LinearLayout userNickNameLayout, userEmailLayout, userPhoneLayout;

    private ImageView backImage;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor_settings);
        initDate();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        user = UserHandler.handleUserLoginResponse(prefs.getString(Constants.USER, null));
        if (user != null) {
            userNickName.setText(user.getNickName());
            userEmail.setText(user.getEmail());
            userPhone.setText(user.getPhoneNumber());
            if (StringUtil.isEmpty(user.getEmail())) {
                userEmail.setText("未填写");
                userEmail.setTextColor(Color.parseColor("#941AE6"));
            } else {
                userEmail.setTextColor(Color.parseColor("#858585"));
            }
        }
    }

    private void setListener() {
        userNickNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ModifyUserNickNameActivity.class);
                intent.putExtra("userNickName", user.getNickName());
                startActivity(intent);
            }
        });
        userEmailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ModifyUserEmailActivity.class);
                intent.putExtra("userEmail", user.getEmail());
                startActivity(intent);
            }
        });
        userPhoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ModifyUserPhoneActivity.class);
                intent.putExtra("userPhone", user.getPhoneNumber());
                startActivity(intent);
            }
        });
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInforSettingActivity.this.finish();
            }
        });
    }

    public void initDate() {
        userNickName = (TextView) findViewById(R.id.id_user_nick_name);
        userEmail = (TextView) findViewById(R.id.id_user_email);
        userPhone = (TextView) findViewById(R.id.id_user_phone);
        userNickNameLayout = (LinearLayout) findViewById(R.id.id_user_nick_name_layout);
        userEmailLayout = (LinearLayout) findViewById(R.id.id_user_email_layout);
        userPhoneLayout = (LinearLayout) findViewById(R.id.id_user_phone_layout);
        backImage = (ImageView) findViewById(R.id.id_back_image);

        //读取SharedPreference中的user数据
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        user = UserHandler.handleUserLoginResponse(prefs.getString(Constants.USER, null));
        if (user != null) {
            userNickName.setText(user.getNickName());
            userEmail.setText(user.getEmail());
            userPhone.setText(user.getPhoneNumber());
            if (StringUtil.isEmpty(user.getEmail())) {
                userEmail.setText("未填写");
                userEmail.setTextColor(Color.parseColor("#941AE6"));
            }
        } else {
            Toast.makeText(this, "用户数据丢失", Toast.LENGTH_SHORT).show();
        }
    }
}
