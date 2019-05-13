package com.netease.nim.demo.mangement.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.NotificationBean;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.demo.task.activity.TaskDetailActivity;

import java.util.List;

public class TaskCalenderAdapter extends BaseAdapter{
     Context context;
     List<TaskBean> list;

    public TaskCalenderAdapter(Context context, List<TaskBean> list) {
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
        final TaskBean task=list.get(position);
        ViewHolder holder=null;
        if (convertView==null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.task_item_calender,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(task.getTname());
        holder.tv_content.setText(task.getTcontent());
        String state;
        //任务状态 0 未开始 ，1正在进行，2完成，3过期
        switch (task.getTstate())
        {
            case 0:
                state="未开始";
                break;
            case 1:
                state="进行中";
                break;
            case 2:
                state="已完成";
                break;
            case 3:
                state="已过期";
                break;
                default:
                    state="未知";
                    break;
        }

        holder.tv_state.setText(state);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, TaskDetailActivity.class);
                intent.putExtra("task",task);
                context.startActivity(intent);
            }
        });
        return convertView;
    }


    class ViewHolder{
        TextView tv_name;
        TextView tv_content;
        TextView tv_state;
        View view;
        public ViewHolder(View convertView) {
            tv_name = convertView.findViewById(R.id.tv_name);
            tv_content = convertView.findViewById(R.id.tv_content);
            tv_state=convertView.findViewById(R.id.tv_state);
            view=convertView;
        }
    }
}

