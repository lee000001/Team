package com.netease.nim.demo.mangement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.uikit.common.ui.imageview.CircleImageView;

import java.util.List;

public class TaskManagementAdapter extends BaseAdapter {
    public List<TaskBean> list;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClick onItemClick;

    public TaskManagementAdapter(Context context, List<TaskBean> list, OnItemClick onItemClick ) {
        this.list = list;
        this.context = context;
        this.onItemClick=onItemClick;
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

        TaskBean task=list.get(position);
        if (task.getEdit())//编辑视图
             convertView = edit(position, convertView, parent, task);
        else//显示视图
            convertView = show(position, convertView, parent, task);

        return convertView;
    }

    private View edit(final int position, View convertView, ViewGroup parent, final TaskBean task) {
        final ViewHolder_Edit holder;//判断是否有缓存
        if (convertView == null||!ViewHolder_Edit.class.isInstance(convertView.getTag())) {
            //通过LayoutInflate实例化布局
            convertView = inflater.inflate(R.layout.task_item_edit_layout, parent, false);
            holder = new ViewHolder_Edit(convertView);


            convertView.setTag(holder);
        } else {
            //通过tag找到缓存的布局
           holder= (ViewHolder_Edit) convertView.getTag();
        }
        holder.et_task_name.setText(task.getTname());
        holder.et_task_content.setText(task.getTcontent());
        holder.et_task_result.setText(task.getTresult());
        holder.tv_start.setText(task.getTstartDate());
        holder.tv_end.setText(task.getTendDate());

        holder.btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setTname(holder.et_task_name.getText().toString());
                list.get(position).setTcontent(holder.et_task_content.getText().toString());
                list.get(position).setTresult(holder.et_task_result.getText().toString());
                list.get(position).setTstartDate(holder.tv_start.getText().toString());
                list.get(position).setTendDate(holder.tv_end.getText().toString());
                list.get(position).setEdit(false);
                onItemClick.onModify(position,task);
                refresh();

            }
        });
        holder.ibtn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               list.remove(position);
                list.get(position).setEdit(false);
                onItemClick.onDelete(position,task);
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
     * @param task
     * @return
     */
    private View show(final int position, View convertView, ViewGroup parent, TaskBean task) {
        final ViewHolder_Show holder;//判断是否有缓存
        if (convertView == null||!ViewHolder_Show.class.isInstance(convertView.getTag())) {
            //通过LayoutInflate实例化布局
            convertView = inflater.inflate(R.layout.task_item_show_layout, parent, false);
            holder = new ViewHolder_Show(convertView);


            convertView.setTag(holder);
        } else {
            //通过tag找到缓存的布局
            holder= (ViewHolder_Show) convertView.getTag();
        }
        holder.tv_task_name.setText(task.getTname());
        holder.tv_state.setText(getState(task.getTstate()));
        holder.tv_task_creator.setText(task.getTcreator());
        holder.tv_task_createDate.setText(task.getTcreateDate());
        holder.tv_task_type.setText(task.getTtype());

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

        return convertView;
    }
    private void refresh() {
        this.notifyDataSetChanged();
    }

    /**
     * 展示视图
     */
    class ViewHolder_Show {
        TextView tv_task_createDate;
        TextView tv_task_name;
        TextView tv_task_creator;
        TextView tv_task_type;
        Button  btn_edit;
        TextView tv_state;
        public ViewHolder_Show(View itemView) {
            tv_task_createDate=itemView.findViewById(R.id.tv_task_creatDate);
            tv_task_creator=itemView.findViewById(R.id.tv_task_creator);
            tv_task_name=itemView.findViewById(R.id.tv_task_name);
            tv_task_type=itemView.findViewById(R.id.tv_task_type);
            btn_edit=itemView.findViewById(R.id.btn_edit);
            tv_state=itemView.findViewById(R.id.tv_state);
        }
    }

    /**
     * 编辑视图
     */
    class ViewHolder_Edit {
        EditText et_task_content;
        EditText et_task_name;
        EditText et_task_result;
        TextView tv_start;
        TextView tv_end;
        Button  btn_save;
        Button btn_cancel;
        CircleImageView btn_startDate;
        CircleImageView btn_endDate;
        ImageButton ibtn_del;
        public ViewHolder_Edit(View itemView) {
            et_task_name=itemView.findViewById(R.id.et_task_name);
            et_task_content=itemView.findViewById(R.id.et_task_content);
            et_task_result=itemView.findViewById(R.id.et_task_result);
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
        void onModify(int index, TaskBean task);
        void onDelete(int index, TaskBean task);

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
