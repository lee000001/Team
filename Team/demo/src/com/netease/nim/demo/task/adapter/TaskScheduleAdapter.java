package com.netease.nim.demo.task.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.ActivityBean;
import com.netease.nim.demo.task.view.LRTextView;
import com.netease.nim.uikit.common.ui.imageview.CircleImageView;

import java.util.Date;
import java.util.List;

/**
 * 任务进度 时光轴效果的适配器
 */
public class TaskScheduleAdapter extends RecyclerView.Adapter<TaskScheduleAdapter.MyViewHolder> {

    private  Context context;
    public List<ActivityBean> dataList;

    private LayoutInflater layoutInflater;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private OnClickListener onClickListener;

    public TaskScheduleAdapter(Context context, List<ActivityBean> dataList) {
        this.dataList = dataList;
        layoutInflater = LayoutInflater.from(context);
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.activity_item_schedule, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ActivityBean activityBean=dataList.get(position);
        holder.lr_name.setContent(activityBean.getAname());
        holder.lr_start.setContent(activityBean.getStartDate());
        holder.lr_end.setContent(activityBean.getEndDate());
        holder.tv_content.setText(activityBean.getContent());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onClickListener!=null)
                    onClickListener.onClick(view,dataList.get(position));
            }
        });

       if(activityBean.getAstate()!=2){
           Resources resources = context.getResources();
           Drawable drawable = resources.getDrawable(R.drawable.ic_uncheck_circle);
           holder.circleImageView.setImageDrawable(drawable);
       }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private LRTextView lr_name;
        private LRTextView lr_start;
        private LRTextView lr_end;
        private TextView tv_content;
        private View view;
        private CircleImageView circleImageView;

        MyViewHolder(View itemView) {
            super(itemView);
            tv_content =itemView.findViewById(R.id.tv_content);
            lr_name =itemView.findViewById(R.id.lr_name);
            lr_start=itemView.findViewById(R.id.lr_start);
            lr_end =itemView.findViewById(R.id.lr_end);
            circleImageView=itemView.findViewById(R.id.image_state);
            view=itemView;

        }
    }
    public interface  OnClickListener{
        void onClick(View view, ActivityBean activityBean);
    }


}
