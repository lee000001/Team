package com.netease.nim.demo.mangement.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.NotificationBean;

import java.util.List;

public class NotificationAdapter extends BaseAdapter{
     Context context;
     List<NotificationBean> list;



    public NotificationAdapter(Context context, List<NotificationBean> list) {
        this.context = context;
        this.list = list;


    }

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final NotificationBean notificationBean=list.get(position);
        ViewHolder holder=null;
        if (convertView==null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.notification_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder= (ViewHolder) convertView.getTag();
        }
//        holder.tv_name.setText(activity.getAname());
//        holder.tv_content.setText(activity.getContent());
//        holder.tv_result.setText(activity.getAresult());
//        String state;
//        //任务状态 0 未开始 ，1正在进行，2完成，3过期
//        switch (activity.getAstate())
//        {
//            case 0:
//                state="未开始";
//                break;
//            case 1:
//                state="正在进行";
//                break;
//            case 2:
//                state="完成";
//                break;
//                default:
//                    state="未知";
//                    break;
//        }
//
//        holder.tv_state.setText(state);
//        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context, ActivityDetailActivity.class);
//                intent.putExtra("activity",activity);
//                context.startActivity(intent);
//            }
//        });

        return convertView;
    }


    class ViewHolder{
        TextView tv_name;
        TextView tv_content;
        TextView tv_result;
        TextView tv_state;
        Button btn_detail;


        public ViewHolder(View convertView) {

            btn_detail=convertView.findViewById(R.id.btn_detail);
            tv_name = convertView.findViewById(R.id.tv_name);
            tv_content = convertView.findViewById(R.id.tv_content);
            tv_result = convertView.findViewById(R.id.tv_result);
            tv_state=convertView.findViewById(R.id.tv_state);
        }
    }
}

