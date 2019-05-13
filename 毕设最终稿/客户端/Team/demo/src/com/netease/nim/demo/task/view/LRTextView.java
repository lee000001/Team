package com.netease.nim.demo.task.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.nim.demo.R;

public class LRTextView extends RelativeLayout {


    private TextView tv_left;
    private TextView tv_right;

    public LRTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.lr_textview, this, true);
        tv_left=findViewById(R.id.tv_left);
        tv_right=findViewById(R.id.tv_right);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.LRTextView);
        if (attributes != null) {
            //设置文字
            CharSequence left_text = attributes.getText(R.styleable.LRTextView_left_text);
            tv_left.setText(left_text);
            CharSequence right_text = attributes.getText(R.styleable.LRTextView_right_text);
            tv_right.setText(right_text);
        }
        attributes.recycle();

    }

    public TextView getTv_left() {
        return tv_left;
    }

    public TextView getTv_right() {
        return tv_right;
    }


    public void  setContent(String content){
        tv_right.setText(content);
    }


}