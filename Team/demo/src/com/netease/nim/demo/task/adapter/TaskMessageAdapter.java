package com.netease.nim.demo.task.adapter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.MessageBean;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.uikit.common.ui.imageview.CircleImageView;

import java.util.List;

public class TaskMessageAdapter extends BaseAdapter {
    private List<MessageBean> list;
    private MessageBean messageBean;
    private LayoutInflater inflater;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
     final ViewHolder holder;
        messageBean = list.get(position);
        if (view == null) {
            view = inflater.inflate(R.layout.change_message_item, parent, false); //加载布局
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) view.getTag();
        }
        if(messageBean.getIsRead()==1){ //0表示服务器中未发送，1表示已发送用户未读，2表示用户已读
            holder.btn_read.setVisibility(View.INVISIBLE);
        }
        holder.tv_msg.setText(messageBean.getMsg());
        holder.btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btn_read.setVisibility(View.INVISIBLE); //已读按钮隐藏
                //设为已读
            }
        });
        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return view;
    }


    class ViewHolder {
        TextView tv_msg;
        Button btn_read;
        Button btn_detail;
        public ViewHolder(View itemView) {
            tv_msg=itemView.findViewById(R.id.tv_msg);
            btn_read=itemView.findViewById(R.id.btn_read);
            btn_detail=itemView.findViewById(R.id.btn_detail);
        }
    }
    public interface  OnClickListener{
        void onClick(TaskBean task);
    }

}
