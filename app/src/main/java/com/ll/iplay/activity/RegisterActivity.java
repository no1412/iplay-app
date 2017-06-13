package com.ll.iplay.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ll.iplay.common.Constants;
import com.ll.iplay.util.HttpUtil;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.ConfirmValidator;
import com.throrinstudio.android.common.libs.validator.validator.LengthValidator;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;
import com.throrinstudio.android.common.libs.validator.validator.PhoneValidator;
import com.throrinstudio.android.common.libs.validator.validator.RangeValidator;
import com.throrinstudio.android.common.libs.validator.validator.RegExpValidator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextView loginTip;
    private EditText phoneEditText, nickNameEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private Form form;

    //错误代码
    private static final String PHONE_NUMBER_EXIST_ERROR_CODE = "10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inttView();
        setListener();
        validateForm();
    }

    private void inttView() {
        loginTip = (TextView) findViewById(R.id.id_login_tip);
        phoneEditText = (EditText) findViewById(R.id.id_user_phone);
        nickNameEditText = (EditText) findViewById(R.id.id_user_nick_name);
        passwordEditText = (EditText) findViewById(R.id.id_user_password);
        confirmPasswordEditText = (EditText) findViewById(R.id.id_user_confirm_password);
        registerButton = (Button) findViewById(R.id.id_register_btn);
    }

    private void setListener() {
        loginTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = form.validate();
                if(flag){
                    String url = Constants.REQUEST_PREFIX + "user/userRegister";
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("phoneNumber", phoneEditText.getText().toString().trim());
                    params.put("nickName", nickNameEditText.getText().toString().trim());
                    params.put("password", passwordEditText.getText().toString().trim());
                    params.put("appKey", Constants.APP_KEY);
                    HttpUtil.sendOkHttpRequestByPost(url, params, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, Constants.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String responseText = response.body().string();
                            if (responseText.equals(PHONE_NUMBER_EXIST_ERROR_CODE)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        phoneEditText.setError("该手机号已存在");
                                        Toast.makeText(RegisterActivity.this, "该手机号已存在！", Toast.LENGTH_LONG).show();
                                    }
                                });
                            } else if (responseText.equals(Constants.SUCCESS)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(RegisterActivity.this, "出现错误", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this, "请按要求填写!", Toast.LENGTH_LONG).show();
                }
            }
        });
        phoneEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String url = Constants.REQUEST_PREFIX + "user/verifyPhoneNumberIsExist?appKey=" + Constants.APP_KEY + "&phoneNumber=" + phoneEditText.getText().toString().trim();
                    HttpUtil.sendOkHttpRequest(url, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, Constants.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String responseText = response.body().string();
                            if (Constants.SUCCESS.equals(responseText)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        phoneEditText.setError("该手机号已存在");
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    private void validateForm() {
        // 1. 先创建个表单Form类用来装控件
        form = new Form();
        Validate phoneValidate = new Validate(phoneEditText);
        Validate nickNameValidate = new Validate(nickNameEditText);
        Validate passwordValidate = new Validate(passwordEditText);
        Validate confirmPasswordValidate = new Validate(confirmPasswordEditText);

        phoneValidate.addValidator(new NotEmptyValidator(this,R.string.phone_not_empty));
        phoneValidate.addValidator(new PhoneValidator(this));
        phoneValidate.addValidator(new LengthValidator(this, 11, 11, R.string.phone_length_error));
        nickNameValidate.addValidator(new NotEmptyValidator(this,R.string.nick_name_not_empty));
        nickNameValidate.addValidator(new LengthValidator(this,1,20,R.string.nick_name_error_msg));
        passwordValidate.addValidator(new NotEmptyValidator(this,R.string.password_not_empty));
        passwordValidate.addValidator(new LengthValidator(this,6,-1,R.string.password_error_msg));
        confirmPasswordValidate.addValidator(new NotEmptyValidator(this,R.string.confirm_password_not_empty));
        confirmPasswordValidate.addValidator(new ConfirmValidator(this, passwordEditText, R.string.confirm_password_error_msg));
        confirmPasswordValidate.addValidator(new LengthValidator(this,6,-1,R.string.confirm_password_length_error_msg));

        form.addValidates(phoneValidate);
        form.addValidates(nickNameValidate);
        form.addValidates(passwordValidate);
        form.addValidates(confirmPasswordValidate);
    }
}
