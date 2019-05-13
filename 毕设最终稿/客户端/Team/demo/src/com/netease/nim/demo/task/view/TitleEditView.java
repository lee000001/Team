package com.netease.nim.demo.task.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.netease.nim.demo.R;

/**
 * 带标签的文本输入框
 */
public class TitleEditView extends RelativeLayout {


    private TextView tv_left;



    private EditText et_right;

    public TitleEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_editview, this, true);
        tv_left=findViewById(R.id.tv_left);
        et_right=findViewById(R.id.et_right);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.LRTextView);
        if (attributes != null) {
            //设置文字
            CharSequence left_text = attributes.getText(R.styleable.LRTextView_left_text);
            tv_left.setText(left_text);
            CharSequence right_text = attributes.getText(R.styleable.LRTextView_right_text);
            et_right.setText(right_text);
            et_right.setBackgroundResource(R.drawable.editview);
        }
        attributes.recycle();

    }

    public TextView getTv_left() {
        return tv_left;
    }
    public EditText getEt_right() {
        return et_right;
    }





}