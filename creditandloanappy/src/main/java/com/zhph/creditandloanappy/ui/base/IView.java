package com.zhph.creditandloanappy.ui.base;

import android.content.Intent;
import android.view.View;

/**
 * Created by 郑志辉 on 2017/2/20.
 */

public interface IView {

    void start2Activity(Intent intent);

    void showMessage(String msg);

    void showLoadDialog();

    void showLoadDialog(String loadingText);

    void dismissDialog();

    void setText(int id, CharSequence charSequence);

    void setHint(int id, CharSequence charSequence);

    /**
     * 获取view对象
     *
     * @param id
     * @return
     */
    View getView(int id);

    /**
     * 获取文本
     *
     * @param id
     * @return
     */
    CharSequence getTextZ(int id);

    CharSequence getHint(int id);

    /**
     * 设置选中状态
     *
     * @param id
     * @param checked
     */
    void setChecked(int id, boolean checked);

    /**
     * 获取选中状态
     *
     * @param id
     * @return
     */
    boolean getChecked(int id);
}
