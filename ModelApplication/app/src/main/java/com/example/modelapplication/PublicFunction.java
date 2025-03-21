package com.example.modelapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Adapter;
import android.widget.Toast;

public class PublicFunction {
    public static void showConfirmDialog(Context c, int i, DialogCallback callback, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("刪除確認")
                .setMessage(msg)
                .setPositiveButton("確定", (dialog, which) -> {
                    callback.DeletItem(i);
                    dialog.dismiss();
                })
                .setNegativeButton("取消", (dialog, which) -> {
                    dialog.dismiss();
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
