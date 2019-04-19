package com.netease.nim.demo.task.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.netease.nim.demo.task.api.OnAlterDialogSelected;

public class AlterDialogHelper {
    public static void initErrorDialog(String msg, Context context){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert = alert;
        alert.setTitle("温馨提示").setMessage(msg).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.create();
        alert.show();
    }

    public static void initSelectDialog(String msg, Context context, final OnAlterDialogSelected onAlterDialogSelected){
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("温馨提示").setMessage(msg).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onAlterDialogSelected.onComfirm();
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onAlterDialogSelected.onCancel();
                dialog.dismiss();
            }
        });
        alert.create();
        alert.show();

    }


}
