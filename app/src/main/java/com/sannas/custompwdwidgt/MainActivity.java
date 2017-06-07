package com.sannas.custompwdwidgt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CustomPwdWidget.PasswordFullListener,
        CustomKeyboard.CustomerKeyboardClickListener{

    private CustomPwdWidget pwdEdit;
    private CustomKeyboard keboard;
    private String mNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pwdEdit = (CustomPwdWidget) findViewById(R.id.pwdEdit);
        keboard = (CustomKeyboard) findViewById(R.id.keyboard);
        pwdEdit.setPasswordFullListener(this);
        keboard.setOnCustomerKeyboardClickListener(this);
    }
    @Override
    public void passwordFullListener(String number) {
        mNumber = number;
    }

    /**
     * 键盘的点击数字键的响应事件
     * @param number
     */
    @Override
    public void click(String number) {
        pwdEdit.addPassword(number);
    }

    /**
     * 点击确认键的回调事件
     */
    @Override
    public void confirm() {
        if(!TextUtils.isEmpty(mNumber)){
            mNumber = mNumber.substring(0,4);   //4为设置输入数字长度,截取输入的长度
            Toast.makeText(getApplicationContext(),mNumber,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 点击删除按键的回调事件
     */
    @Override
    public void cancel() {
        mNumber = null;
        pwdEdit.deleteLastPassword();
    }
}
