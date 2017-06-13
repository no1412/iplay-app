package com.ll.iplay.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ll.iplay.common.Constants;
import com.ll.iplay.gson.User;
import com.ll.iplay.handler.UserHandler;
import com.ll.iplay.util.HttpUtil;
import com.ll.iplay.util.StringUtil;
import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.EmailValidator;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ModifyUserEmailActivity extends AppCompatActivity {

    private String originalEmail;
    private EditText userEmail;
    private TextView finishTextView;
    private ImageView backImageView;
    private Form form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user_email);
        originalEmail =  getIntent().getStringExtra("userEmail");
        initView();
        setListener();
        validateForm();
    }

    private void setListener() {
        finishTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = form.validate();
                if(flag){
                    String userEmaileValue = userEmail.getText().toString();
                    if (originalEmail.equals(userEmaileValue) && StringUtil.isNotEmpty(userEmaileValue)) {
                        Toast.makeText(v.getContext(), "邮箱没有改动", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String url = Constants.REQUEST_PREFIX + "user/updateUserInfos";
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                    User user = UserHandler.handleUserLoginResponse(prefs.getString(Constants.USER, null));
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("userId",String.valueOf(user.getId()));
                    params.put("userEmail", userEmaileValue);
                    params.put("appKey", Constants.APP_KEY);
                    HttpUtil.sendOkHttpRequestByPost(url, params, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ModifyUserEmailActivity.this, "修改失败", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String responseText = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ModifyUserEmailActivity.this).edit();
                                    editor.putString(Constants.USER, responseText);
                                    editor.apply();
                                    Toast.makeText(ModifyUserEmailActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                                    ModifyUserEmailActivity.this.finish();
                                }
                            });
                        }
                    });
                }
            }
        });
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyUserEmailActivity.this.finish();
            }
        });
    }

    public void initView() {
        userEmail = (EditText) findViewById(R.id.id_user_email);
        backImageView = (ImageView) findViewById(R.id.id_back_image);
        finishTextView = (TextView) findViewById(R.id.id_finish);
        if (StringUtil.isNotEmpty(originalEmail)) {
            userEmail.setText(originalEmail);
        }
    }
    private void validateForm() {
        // 1. 先创建个表单Form类用来装控件
        form = new Form();
        Validate emailValidator = new Validate(userEmail);
        emailValidator.addValidator(new NotEmptyValidator(this,R.string.email_not_empty));
        emailValidator.addValidator(new EmailValidator(this,R.string.email_type_error));

        form.addValidates(emailValidator);
    }
}
