package com.netease.nim.demo.mangement.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.netease.nim.demo.bean.ActivityBean;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.demo.task.activity.ActivityDetailActivity;
import com.netease.nim.demo.task.activity.TaskDetailActivity;

import com.netease.nim.uikit.common.ui.imageview.CircleImageView;

import java.io.Serializable;
import java.util.List;

public class ActivityManagementAdapter extends BaseAdapter {

    public List<ActivityBean> list;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClick onItemClick;
    private TaskBean task;

    public ActivityManagementAdapter(Context context,List<ActivityBean> list,  TaskBean task, OnItemClick onItemClick) {
        this.list = list;
        this.context = context;
        this.onItemClick = onItemClick;
        this.task = task;
        inflater= LayoutInflater.from(context);
    }

    public ActivityManagementAdapter(Context context, List<ActivityBean> list, OnItemClick onItemClick ) {
        this.list = list;
        this.context = context;
        this.onItemClick=onItemClick;
        inflater= LayoutInflater.from(context);
    }

    public ActivityManagementAdapter(List<ActivityBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater= LayoutInflater.from(context);
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
        if (activity.getEdit())//编辑视图
             convertView = edit(position, convertView, parent, activity);
        else//显示视图
            convertView = show(position, convertView, parent, activity);

        return convertView;
    }

    private View edit(final int position, View convertView, ViewGroup parent, final ActivityBean activity) {
        final ViewHolder_Edit holder;//判断是否有缓存
        if (convertView == null||!ViewHolder_Edit.class.isInstance(convertView.getTag())) {
            //通过LayoutInflate实例化布局
            convertView = inflater.inflate(R.layout.activity_item_edit_layout, parent, false);
            holder = new ViewHolder_Edit(convertView);


            convertView.setTag(holder);
        } else {
            //通过tag找到缓存的布局
           holder= (ViewHolder_Edit) convertView.getTag();
        }
        holder.et_name.setText(activity.getAname());
        holder.et_content.setText(activity.getContent());
        holder.et_result.setText(activity.getAresult());
        holder.tv_start.setText(activity.getStartDate());
        holder.tv_end.setText(activity.getEndDate());

        holder.btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setAname(holder.et_name.getText().toString());
                list.get(position).setContent(holder.et_content.getText().toString());
                list.get(position).setAresult(holder.et_result.getText().toString());
                list.get(position).setStartDate(holder.tv_start.getText().toString());
                list.get(position).setEndDate(holder.tv_end.getText().toString());
                list.get(position).setEdit(false);
                onItemClick.onModify(position,activity);
                refresh();

            }
        });
        holder.ibtn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               list.remove(position);
//                list.get(position).setEdit(false);
                onItemClick.onDelete(position,activity);
                refresh();

            }
        });
        holder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setEdit(false);
                refresh();
            }
        });
        return convertView;
    }

    /**
     * 展示视图
     * @param position
     * @param convertView
     * @param parent
     * @param activity
     * @return
     */
    private View show(final int position, View convertView, ViewGroup parent, final ActivityBean activity) {
        final ViewHolder_Show holder;//判断是否有缓存
        if (convertView == null||!ViewHolder_Show.class.isInstance(convertView.getTag())) {
            //通过LayoutInflate实例化布局
            convertView = inflater.inflate(R.layout.activity_item_show_layout, parent, false);
            holder = new ViewHolder_Show(convertView);


            convertView.setTag(holder);
        } else {
            //通过tag找到缓存的布局
            holder= (ViewHolder_Show) convertView.getTag();
        }
        holder.tv_name.setText(activity.getAname());
        holder.tv_state.setText(getState(activity.getAstate()));
        holder.tv_creator.setText("我");
        holder.tv_createDate.setText(activity.getStartDate());
//        holder.tv_type.setText(task.getTtype());

        /**
         * 进入编辑模式
         */
        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setEdit(true);
                refresh();
            }
        });
        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ActivityDetailActivity.class);
                intent.putExtra("task", (Serializable) task);
                intent.putExtra("activity",activity);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
    private void refresh() {
        this.notifyDataSetChanged();
    }

    /**
     * 展示视图
     */
    class ViewHolder_Show {
        TextView tv_createDate;
        TextView tv_name;
        TextView tv_creator;
//        TextView tv_type;
        Button  btn_edit;
        Button  btn_detail;
        TextView tv_state;
        public ViewHolder_Show(View itemView) {
            tv_createDate=itemView.findViewById(R.id.tv_createDate);
            tv_creator=itemView.findViewById(R.id.tv_creator);
            tv_name=itemView.findViewById(R.id.tv_name);
//            tv_type=itemView.findViewById(R.id.tv_type);
            btn_edit=itemView.findViewById(R.id.btn_edit);
            btn_detail=itemView.findViewById(R.id.btn_detail);
            tv_state=itemView.findViewById(R.id.tv_state);
        }
    }

    /**
     * 编辑视图
     */
    class ViewHolder_Edit {
        EditText et_content;
        EditText et_name;
        EditText et_result;
        TextView tv_start;
        TextView tv_end;
        Button  btn_save;
        Button btn_cancel;
        CircleImageView btn_startDate;
        CircleImageView btn_endDate;
        ImageButton ibtn_del;
        public ViewHolder_Edit(View itemView) {
            et_name=itemView.findViewById(R.id.et_name);
            et_content=itemView.findViewById(R.id.et_content);
            et_result=itemView.findViewById(R.id.et_result);
            tv_start=itemView.findViewById(R.id.tv_start);
            tv_end=itemView.findViewById(R.id.tv_end);
            btn_save=itemView.findViewById(R.id.btn_save);
            btn_startDate=itemView.findViewById(R.id.btn_startDate);
            btn_endDate=itemView.findViewById(R.id.btn_endDate);
            ibtn_del=itemView.findViewById(R.id.ibtn_del);
            btn_cancel=itemView.findViewById(R.id.btn_cancel);

        }
    }




    public interface  OnItemClick{
        void onModify(int index, ActivityBean task);
        void onDelete(int index, ActivityBean task);

    }

    private  String getState(int state)
    {
        String s="";
        switch (state)
        {
            case 0:
                s="未开始";
                break;
            case 1:
                s="进行中";;
                break;
            case 2:
                s="已完成";
                break;
            case 3:
                s="已过期";
                break;

        }
        return s;
    }
}
