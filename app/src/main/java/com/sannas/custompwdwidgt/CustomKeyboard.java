package com.sannas.custompwdwidgt;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/6/5.
 */

public class CustomKeyboard extends LinearLayout implements View.OnClickListener {
    private CustomerKeyboardClickListener mListener;

    public CustomKeyboard(Context context) {
        this(context, null);
    }

    public CustomKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.ui_customer_keyboard, this);
        setChildViewOnclick(this);
    }

    /**
     * 给自定义键盘的子View设置点击事件
     */
    private void setChildViewOnclick(ViewGroup parent) {
        int childCount = parent.getChildCount();  //获取整个自定义键盘的每个子元素
        for (int i = 0; i < childCount; i++) {
            // 不断的递归设置点击事件
            View view = parent.getChildAt(i);
            if (view instanceof ViewGroup) {   //若子元素为ViewGroup，则继续递归调用为子元素添加监听事件。
                setChildViewOnclick((ViewGroup) view);
                continue;
            }
            view.setOnClickListener(this);   //为子元素添加监听事件
        }
    }

    /**
     * 只要点击了自定义的键盘，就会响应该事件，并判断点击的是点击了什么
     * @param v
     */
    @Override
    public void onClick(View v) {
        View clickView = v;
        if (clickView instanceof TextView) {
            // 如果点击的是TextView
            String number = ((TextView) clickView).getText().toString();
            if (!TextUtils.isEmpty(number)) {
                if (mListener != null) {
                    // 回调
                    mListener.click(number);
                }
            }
        } else if (clickView instanceof ImageView) {
            //如果点击的使ImageView，就判断是确认还是删除
            switch (clickView.getId()){
                case R.id.confirm:
                    //回调监听的confirm方法
                    mListener.confirm();
                    break;
                case R.id.cancel:
                    //回调监听的cancel方法
                    mListener.cancel();
                    break;
            }

        }
    }

    /**
     * 设置键盘的点击回调监听
     */
    public void setOnCustomerKeyboardClickListener(CustomerKeyboardClickListener listener) {
        this.mListener = listener;
    }

    /**
     * 点击键盘的回调监听
     */
    public interface CustomerKeyboardClickListener {
        public void click(String number);
        public void confirm();
        public void cancel();
    }
}
