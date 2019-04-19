package com.netease.nim.demo.task.adapter;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.ActivityBean;
import com.netease.nim.demo.login.bean.UserBean;
import com.netease.nim.demo.task.api.OnStepClick;
import com.netease.nim.demo.task.helper.ActivityHelper;
import com.netease.nim.uikit.common.ui.imageview.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.logging.SimpleFormatter;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddActivityListAdapter extends BaseAdapter{
     Context context;
     List<ActivityBean> list;
     private OnDeleteClickListener onDeleteClickListener;
    private LinearLayoutManager linearLayoutManager;
    private MemberAdapter memberAdapter;


    private static final String TAG = "AddActivityListAdapter";
    public AddActivityListAdapter(Context context, List<ActivityBean> list, OnDeleteClickListener onDeleteClickListener) {
        this.context = context;
        this.list = list;
        this.onDeleteClickListener = onDeleteClickListener;
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
        ActivityBean activity=list.get(position);
        ViewHolder holder=null;
        if (convertView==null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.step_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder= (ViewHolder) convertView.getTag();
        }

        holder.tv_name.setText(activity.getAname());
        SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
        holder.tv_start.setText(activity.getStartDate());
        holder.tv_end.setText(activity.getEndDate());
        holder.tv_content.setText(activity.getContent());
        holder.tv_result.setText(activity.getAresult());
        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteClickListener.onDelete(position);
            }
        });
        getMember(holder,activity);
        return convertView;
    }

    private void getMember(final ViewHolder holder, ActivityBean activity) {

        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        if(activity.getSelectedMembers()==null||activity.getSelectedMembers().size()==0)
        {
            memberAdapter = new MemberAdapter(context,new ArrayList<UserBean>());
            holder.recyclerView.setAdapter(memberAdapter);
        }
        else
        {
            //从数据库中获取用户信息
            ActivityHelper.getRxService()
                    .getUsers(activity.getSelectedMembers())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<UserBean>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError: ");
                        }

                        @Override
                        public void onNext(List<UserBean> userBeans) {
                            memberAdapter = new MemberAdapter(context,userBeans);
                            holder.recyclerView.setAdapter(memberAdapter);
                        }
                    });
        }

    }


    class ViewHolder{
        TextView tv_end;
        TextView tv_start;
        TextView tv_name;
        TextView tv_content;
        TextView tv_result;
        CircleImageView btn_del;
        RecyclerView recyclerView;

        public ViewHolder(View convertView) {
            tv_end = convertView.findViewById(R.id.tv_end);
            tv_start = convertView.findViewById(R.id.tv_start);
            tv_name = convertView.findViewById(R.id.tv_name);
            tv_content = convertView.findViewById(R.id.tv_content);
            tv_result = convertView.findViewById(R.id.tv_result);
            btn_del = convertView.findViewById(R.id.btn_del);
            recyclerView=convertView.findViewById(R.id.recyclerview_member);
        }
    }
    public interface  OnDeleteClickListener
    {
        void onDelete(int index);
    }
}

