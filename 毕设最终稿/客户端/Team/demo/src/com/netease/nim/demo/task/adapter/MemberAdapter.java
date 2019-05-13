package com.netease.nim.demo.task.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netease.nim.demo.R;
import com.netease.nim.demo.config.preference.Preferences;
import com.netease.nim.demo.contact.activity.UserProfileActivity;
import com.netease.nim.demo.login.bean.UserBean;
import com.netease.nim.demo.main.model.Extras;
import com.netease.nim.demo.task.activity.TaskDetailActivity;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.contact.selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.business.team.helper.TeamHelper;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.ui.imageview.CircleImageView;

import java.util.List;

/**
 * 主要用于加载成员头像
 */
public class MemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int CODE_ADD_USER =2 ;
    private Context context;
    public List<UserBean> list;
    private UserBean user;
    private LayoutInflater inflater;
    private boolean isEdit=true;
    public MemberAdapter(Context context, List<UserBean> list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
    }

    public MemberAdapter(Context context, List<UserBean> list,boolean isEdit) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
        this.isEdit=isEdit;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= inflater.inflate(R.layout.task_detail_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        ViewHolder holder= (ViewHolder) viewHolder;

        initClick(position, holder);
        if(position==list.size())
        {
            if(isEdit==false)  //不可添加成员
            {
                return;
            }
            holder.circleImageView.setImageResource(com.netease.nim.uikit.R.drawable.nim_team_member_add_normal);
            holder.tv_username.setText("添加");
            return;
        }
        user=list.get(position);
        //加载任务成员头像
        if(user.getIcon().equals(""))
        {

            holder.circleImageView.setImageResource(com.netease.nim.uikit.R.drawable.nim_avatar_default);
        }
        else{
            Glide.with(context).load(user.getIcon()).into(holder.circleImageView);
        }
        String name=user.getAccid().equals(Preferences.getUserAccount())?"我":user.getName();
        holder.tv_username.setText(name);

    }

    private void initClick(final int position, ViewHolder holder) {
        holder.circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==list.size())
                {
                    //给任务添加成员
                    ContactSelectActivity.Option advancedOption = TeamHelper.getCreateContactSelectOption(null, 50);
                    String [] selectedUids=new String[list.size()];
                    for(int i=0;i<list.size();i++)
                    {
                        selectedUids[i]= list.get(i).getAccid();
                    }
                    ContactSelectActivity.startActivityForResult(context,advancedOption,CODE_ADD_USER,selectedUids);
                }
                else
                {
                    //查看成员信息
                    Intent intent1=new Intent(context,UserProfileActivity.class);
                    intent1.putExtra(Extras.EXTRA_ACCOUNT,list.get(position).getAccid());
                    context.startActivity(intent1);
                }

            }
        });
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return isEdit?list.size()+1:list.size();
    }



    class ViewHolder extends  RecyclerView.ViewHolder {
        private CircleImageView circleImageView;
        private TextView tv_username;
        public ViewHolder(View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.headerImage);
            tv_username= itemView.findViewById(R.id.tv_username);

        }
    }
}
