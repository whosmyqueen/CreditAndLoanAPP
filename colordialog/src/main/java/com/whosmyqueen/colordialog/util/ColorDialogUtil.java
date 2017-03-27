package com.whosmyqueen.colordialog.util;

import android.content.Context;
import android.support.annotation.DrawableRes;

import com.whosmyqueen.colordialog.ColorDialog;
import com.whosmyqueen.colordialog.PromptDialog;
import com.whosmyqueen.colordialog.contants.DialogType;

/**
 * Created by whosmyqueen on 2016/11/1.
 */

public class ColorDialogUtil {

    private PromptDialog promptDlg;
    private ColorDialog colorDialog;
    private PromptDialog loadingDlg; // 等待对话框
    private PromptDialog loadingTextDlg; // 等待对话框
    private PromptDialog loadingUpDlg; // 等待对话框


    /**
     * 创建指定类型的弹出框
     *
     * @param context    上下文
     * @param dialogType 弹出框类型
     * @param title      标题
     * @param content    内容
     * @param okBtnText  按钮文字
     * @return
     */
    public void showPromptDialog(Context context, DialogType dialogType, String title, String content, String okBtnText, PromptDialog.OnPositiveListener onPositiveListener) {
        promptDlg = new PromptDialog(context).setDialogType(dialogType).setAnimationEnable(true).setTitleText(title).setContentText(content).setPositiveListener(okBtnText, onPositiveListener);
        promptDlg.show();
    }

    /**
     * 隐藏指定类型的弹出框
     */
    public void hidePromptDialog() {
        if (promptDlg != null && promptDlg.isShowing()) {
            promptDlg.dismiss();
        }
    }


    /**
     * 弹出color
     *
     * @param context
     * @param title
     * @param content
     * @param drawId
     * @return
     */
    public void showColorDialog(Context context, String title, String content, @DrawableRes int drawId, String yes, ColorDialog.OnPositiveListener onPositiveListener, String cancel, ColorDialog.OnNegativeListener onNegativeListener) {
        colorDialog = new ColorDialog(context);
        colorDialog.setTitle(title);
        colorDialog.setContentText(content);
        colorDialog.setContentImage(drawId);
        colorDialog.setPositiveListener(yes, onPositiveListener).setNegativeListener(cancel, onNegativeListener).setCancelable(false);
        colorDialog.show();
    }

    /**
     * 弹出color
     *
     * @param context
     * @param title
     * @param content
     * @return
     */
    public void showColorDialog(Context context, String title, String content, ColorDialog.OnPositiveListener onPositiveListener) {
        colorDialog = new ColorDialog(context);
        colorDialog.setTitle(title);
        colorDialog.setContentText(content);
        colorDialog.setPositiveListener("确定", onPositiveListener).setCancelable(false);
        colorDialog.show();
    }

    /**
     * 隐藏ColorDialog 弹出框
     */
    public void hideColorDialog() {
        if (colorDialog != null && colorDialog.isShowing()) {
            colorDialog.dismiss();
        }
    }
}
