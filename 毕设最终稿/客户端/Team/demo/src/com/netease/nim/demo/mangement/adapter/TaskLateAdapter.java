package com.netease.nim.demo.mangement.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.ActivityBean;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.demo.mangement.DataHolder;

import java.util.List;


/**

 * Created by Administrator on 2017/7/13.

 */



public class TaskLateAdapter extends BaseAdapter {



    private List<DataHolder> mList;

    private Context mContext;

    private LayoutInflater mInflater;
    private  TaskBean task;



    public TaskLateAdapter(Context context, List<DataHolder> list){

        mList = list;

        mContext = context;

        mInflater = LayoutInflater.from(context);


    }

    @Override

    public int getCount() {

        return mList.size();

    }



    @Override

    public Object getItem(int position) {

        return mList.get(position);

    }



    @Override

    public long getItemId(int position) {

        return position;

    }



    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        final ActivityBean activity=mList.get(position).activity;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.task_late_item, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);

        }else {
            holder = (ViewHolder)convertView.getTag();

        }

        holder.tv_end.setText(activity.getEndDate());
        holder.tv_name.setText("活动："+activity.getAname());
        holder.tv_content.setText(activity.getContent());
        holder.mCheckBox.setChecked(mList.get(position).checked);
//        holder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(mContext, ActivityDetailActivity.class);
//                intent.putExtra("task",task);
//                intent.putExtra("activity",activity);
//                mContext.startActivity(intent);
//            }
//        });
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mList.get(position).checked=isChecked;
                notifyDataSetChanged();
            }
        });
        return convertView;

    }



    public class ViewHolder{
        public TextView tv_name;
        public TextView tv_end;
        public TextView tv_content;
        public CheckBox mCheckBox;
        public View view;
        public ViewHolder(View view) {
            this.view=view;
            tv_name=view.findViewById(R.id.tv_name);
            tv_content=view.findViewById(R.id.tv_content);
            tv_end=view.findViewById(R.id.tv_endDate);
            mCheckBox=view.findViewById(R.id.checkbox);
        }
    };



}

