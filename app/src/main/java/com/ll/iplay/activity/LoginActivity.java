package com.ll.iplay.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ll.iplay.common.Constants;
import com.ll.iplay.gson.User;
import com.ll.iplay.handler.UserHandler;
import com.ll.iplay.util.HttpUtil;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.LengthValidator;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;
import com.throrinstudio.android.common.libs.validator.validator.PhoneValidator;

import org.litepal.util.Const;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private TextView registerTip;
    private EditText phoneEditText, passwordEditText;
    private Button loginBtn;
    private Form form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        initView();
        setListener();
        validateForm();
        if (prefs.getString("user", null) != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initView() {
        registerTip = (TextView) findViewById(R.id.id_register_tip);
        loginBtn = (Button) findViewById(R.id.id_login_btn);
        phoneEditText = (EditText) findViewById(R.id.id_user_phone);
        passwordEditText = (EditText) findViewById(R.id.id_user_password);
    }

    private void setListener() {
        registerTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = form.validate();
                if (flag) {
                    String url = Constants.REQUEST_PREFIX + "user/verifyUserLogin";
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("phoneNumber", phoneEditText.getText().toString().trim());
                    params.put("password", passwordEditText.getText().toString().trim());
                    params.put("appKey", Constants.APP_KEY);
                    HttpUtil.sendOkHttpRequestByPost(url, params, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "服务器异常!", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String responseText = response.body().string();
                            User user = UserHandler.handleUserLoginResponse(responseText);
                            if (user.getResponseCode().equals(Constants.SUCCESS) ) {
                                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit();
                                editor.putString(Constants.USER, responseText);
                                editor.apply();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "登录成功!", Toast.LENGTH_LONG).show();
                                    }
                                });
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LoginActivity.this, "手机号或者密码不正确", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });

                } else {
                    Toast.makeText(LoginActivity.this, Constants.FORM_WRONG_MSG, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void validateForm() {

        // 1. 先创建个表单Form类用来装控件
        form = new Form();
        Validate phoneValidate = new Validate(phoneEditText);
        Validate passwordValidate = new Validate(passwordEditText);

        phoneValidate.addValidator(new NotEmptyValidator(this,R.string.phone_not_empty));
        phoneValidate.addValidator(new PhoneValidator(this));
        phoneValidate.addValidator(new LengthValidator(this, 11, 11, R.string.phone_length_error));
        passwordValidate.addValidator(new NotEmptyValidator(this,R.string.password_not_empty));
        passwordValidate.addValidator(new LengthValidator(this,6,-1,R.string.password_error_msg));

        form.addValidates(phoneValidate);
        form.addValidates(passwordValidate);
    }

}
