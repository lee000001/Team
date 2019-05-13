package com.netease.nim.demo.task.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.bean.TaskBean;
import com.netease.nim.uikit.common.ui.imageview.CircleImageView;

import java.util.List;

public class TaskDataAdapter extends BaseAdapter {

    private Context context;
    private List<TaskBean> list;
    TaskBean task;
    private LayoutInflater inflater;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private OnClickListener onClickListener;

    public TaskDataAdapter(Context context, List<TaskBean> list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return  list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        task=list.get(i);
        if (view == null) {
            view = inflater.inflate(R.layout.task_item, viewGroup, false); //加载布局
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) view.getTag();
        }

        holder.tv_task_type.setText(task.getTtype());
        holder.tv_task_name.setText(task.getTname());
        holder.tv_task_creator.setText(task.getTcreator());
        holder.tv_task_createDate.setText(task.getTcreateDate());
        Resources resources = context.getResources();
        Drawable drawable = null;
        switch (task.getTstate())
        {
            case 0:
               drawable = resources.getDrawable(R.drawable.dot_blue_24px);
               break;
            case 1:
                drawable = resources.getDrawable(R.drawable.dot_red_24px);
                break;
            case 2:
                drawable = resources.getDrawable(R.drawable.dot_green_24px);
                break;
            case 3:
                drawable = resources.getDrawable(R.drawable.dot_pink_24px);
                break;

        }
       holder.circleImageView.setImageDrawable(drawable);
        if(onClickListener!=null)
        {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(task);
                }
            });
        }
        return view;
    }


    class ViewHolder {
        TextView tv_task_createDate;
        TextView tv_task_name;
        TextView tv_task_creator;
        TextView tv_task_type;
        CardView cardView;
        CircleImageView circleImageView;
        public ViewHolder(View itemView) {
            tv_task_createDate=itemView.findViewById(R.id.tv_task_creatDate);
            tv_task_creator=itemView.findViewById(R.id.tv_task_creator);
            tv_task_name=itemView.findViewById(R.id.tv_task_name);
            tv_task_type=itemView.findViewById(R.id.tv_task_type);
            cardView=itemView.findViewById(R.id.cardview);
            circleImageView=itemView.findViewById(R.id.img_state);
        }
    }
    public interface  OnClickListener{
        void onClick(TaskBean task);
    }

}
