package com.netease.nim.demo.mangement.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.ActivityBean;
import com.netease.nim.demo.bean.MessageBean;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.demo.task.activity.ActivityDetailActivity;
import com.netease.nim.demo.task.activity.TaskDetailActivity;
import com.netease.nim.demo.task.helper.ActivityHelper;
import com.netease.nim.demo.task.helper.TaskHepler;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.ui.imageview.CircleImageView;

import java.util.List;

import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChangeMessageAdapter extends BaseAdapter {
    private List<MessageBean> list;
    private MessageBean messageBean;
    private Context context;
    private LayoutInflater inflater;
    private static final String TAG = "ChangeMessageAdapter";
    private ActivityBean activityBean;
    private TaskBean task;

    public ChangeMessageAdapter( Context context,List<MessageBean> list) {
        this.list = list;
        this.context=context;
        inflater=LayoutInflater.from(context);
//        getActivity();
//        getTask();
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
//        if(messageBean.getIsRead()==1){ //0表示服务器中未发送，1表示已发送用户未读，2表示用户已读
//            holder.btn_read.setVisibility(View.INVISIBLE);
//        }
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
                getActivity(messageBean);
            }
        });



        return view;
    }

    private void getTask() {
        TaskHepler.getRxService()
                .getTaskById(messageBean.getMid_tid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TaskBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onNext(TaskBean taskBean) {
                        task=taskBean;
                    }
                });
    }


    private void getActivity(MessageBean msg) {
        ActivityHelper.getRxService()
                .getActivityById(msg.getMid_aid())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ActivityBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onNext(ActivityBean ActivityBean) {

                        activityBean=ActivityBean;
                        Intent intent=new Intent(context,ActivityDetailActivity.class);
//                        intent.putExtra("task",task);
                        intent.putExtra("activity",activityBean);
                        context.startActivity(intent);
                    }
                });
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


}
