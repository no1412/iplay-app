package com.ll.iplay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.throrinstudio.android.common.libs.validator.Form;
import com.throrinstudio.android.common.libs.validator.Validate;
import com.throrinstudio.android.common.libs.validator.validator.NotEmptyValidator;
import com.throrinstudio.android.common.libs.validator.validator.PhoneValidator;
import com.throrinstudio.android.common.libs.validator.validator.RangeValidator;

public class RegisterActivity extends AppCompatActivity {

    private TextView loginTip;
    private EditText phoneEditText, nickNameEditText,passwordEditText;
    private Button registerButton;
    private Form form;

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

        registerButton = (Button) findViewById(R.id.id_register_btn);

        loginTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void setListener() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = form.validate();
                if(flag){
                    Toast.makeText(RegisterActivity.this, "验证成功！", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(RegisterActivity.this, "验证失败!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void validateForm() {
        // 1. 先创建个表单Form类用来装控件
        form = new Form();
        Validate phoneValidate = new Validate(phoneEditText);
        RangeValidator rangeValidator = new RangeValidator(this.getApplicationContext(),1,2);
        phoneValidate.addValidator(new NotEmptyValidator(this));
        phoneValidate.addValidator(new PhoneValidator(this,1));

        form.addValidates(phoneValidate);
    }
}
