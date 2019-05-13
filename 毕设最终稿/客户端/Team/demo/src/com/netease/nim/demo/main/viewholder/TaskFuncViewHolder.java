package com.netease.nim.demo.main.viewholder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.contact.activity.BlackListActivity;
import com.netease.nim.demo.main.activity.SystemMessageActivity;
import com.netease.nim.demo.main.activity.TeamListActivity;
import com.netease.nim.demo.main.reminder.ReminderItem;
import com.netease.nim.demo.main.reminder.ReminderManager;
import com.netease.nim.uikit.business.contact.core.item.AbsContactItem;
import com.netease.nim.uikit.business.contact.core.item.ItemTypes;
import com.netease.nim.uikit.business.contact.core.model.ContactDataAdapter;
import com.netease.nim.uikit.business.contact.core.viewholder.AbsContactViewHolder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 主页 任务 顶部功能项 的viewholder
 * 如：添加任务 搜索任务
 */
public class TaskFuncViewHolder extends AbsContactViewHolder<FuncViewHolder.FuncItem> implements ReminderManager.UnreadNumChangedCallback  {
    private static ArrayList<WeakReference<ReminderManager.UnreadNumChangedCallback>> sUnreadCallbackRefs = new ArrayList<>();


    private Button button;
    private Set<ReminderManager.UnreadNumChangedCallback> callbacks = new HashSet<>();


    /**
     * 加载布局
     * @param inflater
     * @return
     */
    @Override
    public View inflate(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.func_tasks_item, null);
        this.button = view.findViewById(R.id.btn_func_item);
        return view;
    }

    @Override
    public void refresh(ContactDataAdapter adapter, int position, FuncViewHolder.FuncItem item) {

    }
    @Override
    public void onUnreadNumChanged(ReminderItem item) {

    }

    public final static class FuncItem extends AbsContactItem {
        private static final String TAG = "FuncItem";
        static final TaskFuncViewHolder.FuncItem ADD_TASK = new TaskFuncViewHolder.FuncItem();   //添加任务
        static final TaskFuncViewHolder.FuncItem SEARCH_TASK = new TaskFuncViewHolder.FuncItem();    //搜索任务


        @Override
        public int getItemType() {
            return ItemTypes.FUNC;
        }

        @Override
        public String belongsGroup() {
            return null;
        }


        public static List<AbsContactItem> provide() {
            List<AbsContactItem> items = new ArrayList<>();
            items.add(ADD_TASK);
            items.add(SEARCH_TASK);
            return items;
        }

        //功能项点击事件
        public static void handle(Context context, AbsContactItem item) {
            if (item == ADD_TASK) {
//                SystemMessageActivity.start(context);
                //启动添加任务活动
                Log.d(TAG, "handle:启动添加任务活动 ");
            }
            else if (item ==SEARCH_TASK) {
//                TeamListActivity.start(context, ItemTypes.TEAMS.NORMAL_TEAM);
                //启动搜索任务activity
                Log.d(TAG, "handle: 启动搜索任务活动");
            }
        }
    }
}
