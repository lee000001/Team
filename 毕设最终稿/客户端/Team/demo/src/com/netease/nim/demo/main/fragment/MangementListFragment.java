package com.netease.nim.demo.main.fragment;

import android.os.Bundle;

import com.netease.nim.demo.R;
import com.netease.nim.demo.main.model.MainTab;

import com.netease.nim.demo.mangement.fragment.ManagementFragment;

/**
 * 主页集成“管理”界面
 */

public class MangementListFragment extends MainTabFragment {
    private ManagementFragment fragment;

    public MangementListFragment() {
        setContainerId(MainTab.MANGEMENT.fragmentId);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onCurrent(); // 触发onInit，提前加载

    }

    @Override
    protected void onInit() {
        addTaskFragment();  // 集成任务页面
    }



    // 将通讯录列表fragment动态集成进来。 开发者也可以使用在xml中配置的方式静态集成。
    private void addTaskFragment() {
        fragment = new ManagementFragment();
//        fragment.setContainerId(R.id.tasks_fragment);

        getFragmentManager().beginTransaction().add(R.id.mangement_fragment_container,fragment).commit();
//        UI activity = (UI) getActivity();
//
//
//        // 如果是activity从堆栈恢复，FM中已经存在恢复而来的fragment，此时会使用恢复来的，而new出来这个会被丢弃掉
//        fragment = (TasksFragment) activity.addFragment(fragment);

//         功能项定制
//        fragment.setContactsCustomization(new ContactsCustomization() {
//            @Override
//            public Class<? extends AbsContactViewHolder<? extends AbsContactItem>> onGetFuncViewHolderClass() {
//                return FuncViewHolder.class;
//            }
//
//            @Override
//            public List<AbsContactItem> onGetFuncItems() {
//                return FuncViewHolder.FuncItem.provide();
//            }
//
//            @Override
//            public void onFuncItemClick(AbsContactItem item) {
//                FuncViewHolder.FuncItem.handle(getActivity(), item);
//            }
//        });
    }

//    @Override
//    public void onCurrentTabClicked() {
//        // 点击切换到当前TAB
//        if (fragment != null) {
//            fragment.scrollToTop();
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        FuncViewHolder.unRegisterUnreadNumChangedCallback();
    }
}
