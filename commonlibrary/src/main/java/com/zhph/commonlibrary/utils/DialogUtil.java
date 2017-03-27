package com.zhph.commonlibrary.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhph.commonlibrary.R;
import com.zhph.commonlibrary.utils.interfaces.DialogInterface;


/**
 * Created by json on 2016/3/10 0010.
 */
public class DialogUtil {
    private Context mContext;

    private Dialog promptDlg;//呼叫、取消对话框
    private Dialog toastDlg;
    private Dialog loadingDlg; // 等待对话框
    public Dialog loadingTextDlg; // 等待对话框
    private Dialog loadingUpDlg;

    public Dialog upApkDlg;
    public Dialog billItemDlg;

    public DialogUtil(Context mContext) {
        this.mContext = mContext;
    }


    /**
     * 确定、取消提示框
     *
     * @param phoneNum    手机号码内容
     * @param inter       确定取消接口
     * @param surebtstr   确定按钮文字
     * @param isSingle    是否是单行显示
     * @param msg         信息提示文字
     * @param canclebtstr 取消按钮文字
     */
    public void promptDlg(final String phoneNum, String msg, String surebtstr, String canclebtstr, boolean isSingle,
                          final DialogInterface inter) {
        if (promptDlg != null && promptDlg.isShowing()) {
            promptDlg.dismiss();
            return;
        }
        View view = (View) LayoutInflater.from(mContext).inflate(R.layout.dialog_prompt, null);
        promptDlg = new Dialog(mContext, R.style.FullHeightDialog);
        promptDlg.setContentView(view);

        /** 设置选择框居中显示 */
        Window window = promptDlg.getWindow();
        window.setGravity(Gravity.CENTER);
        /** 得到 全部屏幕的大小 宽和高 （width，height） */
        Display d = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        // System.out.println("W + H = "+d.getWidth()+" + "+d.getHeight());

        int width = d.getWidth();
        int height = d.getHeight();
        /** 得到dialog布局的总布局 */
        LinearLayout promptLayout = (LinearLayout) view.findViewById(R.id.rl_parent_prompt);
        android.view.ViewGroup.LayoutParams params = (android.view.ViewGroup.LayoutParams) promptLayout
                .getLayoutParams();
        /** 设置此dialog的布局大小 */
        params.width = CommonUtil.dip2px(mContext, 290);
        params.height = CommonUtil.dip2px(mContext, 200);
        // System.out.println("Ww + Hh = "+params.width+" + "+params.height);
        promptLayout.setLayoutParams(params);

        TextView contentTxt = (TextView) view.findViewById(R.id.tv_phonenum);
        TextView msgTxt = (TextView) view.findViewById(R.id.tv_msg);
        msgTxt.setText(msg);
        contentTxt.setText(phoneNum);
        TextView promptSure_tv = (TextView) view.findViewById(R.id.tv_prompt_sure);
        if (isSingle) {
            msgTxt.setVisibility(View.VISIBLE);
            contentTxt.setVisibility(View.GONE);
        } else {
            msgTxt.setVisibility(View.VISIBLE);
            contentTxt.setVisibility(View.VISIBLE);
        }
        promptSure_tv.setText(surebtstr);
        promptSure_tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (inter != null)
                    if (!TextUtils.isEmpty(phoneNum)) {
                        //如果电话号码有内容就回传
                        inter.sure(phoneNum);
                    } else {
                        //没有内容就传空
                        inter.sure(null);
                    }
            }
        });

        TextView promptCancle_tv = (TextView) view.findViewById(R.id.tv_prompt_cancel);
        promptCancle_tv.setText(canclebtstr);
        promptCancle_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inter != null)
                    inter.cancel(null);
            }
        });
        promptDlg.setCanceledOnTouchOutside(false);
        promptDlg.setCancelable(true);
        promptDlg.show();
    }

    /**
     * 确定、取消对话框消失
     */
    public void promptDlgdismiss() {
        if (promptDlg != null && promptDlg.isShowing()) {
            promptDlg.dismiss();
        }
    }

    /**
     * 确定对话框
     *
     * @param surebtstr 按钮上的文字
     * @param inter
     */
    public void toastDlg(String surebtstr, final DialogInterface inter) {
        toastDlg(surebtstr, inter, null, null, null);
    }


    /**
     * 确定对话框
     *
     * @param surebtstr 按钮上的文字
     * @param inter
     * @param text      头部的文字
     * @param text2     中间的文字2
     * @param text3     中间的文字3
     */
    public void toastDlg(String surebtstr, final DialogInterface inter, String text, String text2, String text3) {
        if (toastDlg != null && toastDlg.isShowing()) {
            toastDlg.dismiss();
            return;
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_erweima, null);
        TextView textView = (TextView) view.findViewById(R.id.textView);

        if (!TextUtils.isEmpty(text)) {
            textView.setText(text);
        }

        TextView textView2 = (TextView) view.findViewById(R.id.textView2);

        if (!TextUtils.isEmpty(text2)) {
            textView2.setText(text2);
        }
        TextView textView3 = (TextView) view.findViewById(R.id.textView3);

        if (!TextUtils.isEmpty(text3)) {
            textView3.setText(text3);
        }

        toastDlg = new Dialog(mContext, R.style.FullHeightDialog);
        toastDlg.setContentView(view);

        /** 设置选择框居中显示 */
        Window window = toastDlg.getWindow();
        window.setGravity(Gravity.CENTER);
        /** 得到 全部屏幕的大小 宽和高 （width，height） */
        Display d = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        // System.out.println("W + H = "+d.getWidth()+" + "+d.getHeight());

        int width = d.getWidth();
        int height = d.getHeight();
        /** 得到dialog布局的总布局 */
        RelativeLayout promptLayout = (RelativeLayout) view.findViewById(R.id.rl_parent_toast);
        android.view.ViewGroup.LayoutParams params = (android.view.ViewGroup.LayoutParams) promptLayout
                .getLayoutParams();
        /** 设置此dialog的布局大小 */
        params.width = CommonUtil.dip2px(mContext, 290);
        params.height = CommonUtil.dip2px(mContext, 200);
        // System.out.println("Ww + Hh = "+params.width+" + "+params.height);
        promptLayout.setLayoutParams(params);

        TextView promptSure_tv = (TextView) view.findViewById(R.id.tv_toast_sure);
        promptSure_tv.setText(surebtstr);
        promptSure_tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (inter != null)
                    inter.sure(null);
            }
        });

        toastDlg.setOnKeyListener(new android.content.DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(android.content.DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                } else {
                    return false; //默认返回 false，这里false不能屏蔽返回键，改成true就可以了
                }
            }
        });

        toastDlg.setCanceledOnTouchOutside(false);
        toastDlg.setCancelable(true);
        toastDlg.show();
    }


    /**
     * 确定对话框消失
     */
    public void toastDlgdismiss() {
        if (toastDlg != null && toastDlg.isShowing()) {
            toastDlg.dismiss();
        }
    }


    /**
     * 等待对话框
     */
    @SuppressWarnings("deprecation")
    public void loadingDlg() {
        if (loadingDlg != null && loadingDlg.isShowing()) {
            loadingDlg.dismiss();
            return;
        }
        View view = (View) LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null);
        loadingDlg = new Dialog(mContext, R.style.FullHeightDialog);
        loadingDlg.setContentView(view);
        /** 设置选择框居中显示 */
        Window window = loadingDlg.getWindow();
        window.setGravity(Gravity.CENTER);
        /** 得到 全部屏幕的大小 宽和高 （width，height） */
        Display d = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        // System.out.println("W + H = "+d.getWidth()+" + "+d.getHeight());

        @SuppressWarnings("deprecation") int width = d.getWidth();
        int height = d.getHeight();
        /** 得到dialog布局的总布局 */
        LinearLayout loadingLayout = (LinearLayout) view.findViewById(R.id.loadingdlg_ll);
        android.view.ViewGroup.LayoutParams params = (android.view.ViewGroup.LayoutParams) loadingLayout
                .getLayoutParams();
        /** 设置此dialog的布局大小站总布局的 宽的3/7 , 高的 3/5 */
        params.width = width * 1 / 3;
        params.height = height * 2 / 5;
        // System.out.println("Ww + Hh = "+params.width+" + "+params.height);
        loadingLayout.setLayoutParams(params);

        loadingDlg.setOnKeyListener(new android.content.DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(android.content.DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                } else {
                    return false; //默认返回 false，这里false不能屏蔽返回键，改成true就可以了
                }
            }
        });
        loadingDlg.setCancelable(false);
        loadingDlg.setCanceledOnTouchOutside(false);
        loadingDlg.show();
    }

    /**
     * 等待文本对话框
     */
    @SuppressWarnings("deprecation")
    public void loadingTextDlg(String text) {
        if (loadingTextDlg != null && loadingTextDlg.isShowing()) {
            loadingTextDlg.dismiss();
            return;
        }
        View view = (View) LayoutInflater.from(mContext).inflate(R.layout.dialog_text, null);
        loadingTextDlg = new Dialog(mContext, R.style.FullHeightDialog);

        TextView textView = (TextView) view.findViewById(R.id.tv_dlg_text);
        textView.setText(text);

        loadingTextDlg.setContentView(view);
        /** 设置选择框居中显示 */
        Window window = loadingTextDlg.getWindow();
        window.setGravity(Gravity.CENTER);
        /** 得到 全部屏幕的大小 宽和高 （width，height） */
        Display d = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        // System.out.println("W + H = "+d.getWidth()+" + "+d.getHeight());

        @SuppressWarnings("deprecation") int width = d.getWidth();
        int height = d.getHeight();
        /** 得到dialog布局的总布局 */
        LinearLayout loadingLayout = (LinearLayout) view.findViewById(R.id.ll_dlg_text);
        android.view.ViewGroup.LayoutParams params = (android.view.ViewGroup.LayoutParams) loadingLayout
                .getLayoutParams();
        /** 设置此dialog的布局大小站总布局的 宽的3/7 , 高的 3/5 */
        params.width = width * 1 / 3;
        params.height = height * 2 / 5;
        // System.out.println("Ww + Hh = "+params.width+" + "+params.height);
        loadingLayout.setLayoutParams(params);


        loadingTextDlg.setCancelable(false);
        loadingTextDlg.setCanceledOnTouchOutside(false);
        loadingTextDlg.show();
    }


    /**
     * 等待对话文本框消失
     */
    public void loadingTextDlgdismiss() {
        if (loadingTextDlg != null && loadingTextDlg.isShowing()) {
            loadingTextDlg.dismiss();
        }
    }


    /**
     * 等待对话框消失
     */
    public void loadingDlgdismiss() {
        if (loadingDlg != null && loadingDlg.isShowing()) {
            loadingDlg.dismiss();
        }
    }


    /**
     * 等待更新对话框消失
     */
    public void loadingUpDlgdismiss() {
        if (loadingUpDlg != null && loadingUpDlg.isShowing()) {
            loadingUpDlg.dismiss();
        }
    }

    /**
     * 等待更新对话框
     */
    @SuppressWarnings("deprecation")
    public void loadingUpDlg(String text) {
        if (loadingUpDlg != null && loadingUpDlg.isShowing()) {
            loadingUpDlg.dismiss();
            return;
        }
        View view = (View) LayoutInflater.from(mContext).inflate(R.layout.dialog_upload, null);
        loadingUpDlg = new Dialog(mContext, R.style.FullHeightDialog);


        TextView textView = (TextView) view.findViewById(R.id.tv_up_text);
        textView.setText(text);

        loadingUpDlg.setContentView(view);
        /** 设置选择框居中显示 */
        Window window = loadingUpDlg.getWindow();
        window.setGravity(Gravity.CENTER);
        /** 得到 全部屏幕的大小 宽和高 （width，height） */
        Display d = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        // System.out.println("W + H = "+d.getWidth()+" + "+d.getHeight());

        @SuppressWarnings("deprecation") int width = d.getWidth();
        int height = d.getHeight();
        /** 得到dialog布局的总布局 */
        LinearLayout loadingLayout = (LinearLayout) view.findViewById(R.id.ll_upload_text);
        android.view.ViewGroup.LayoutParams params = (android.view.ViewGroup.LayoutParams) loadingLayout
                .getLayoutParams();
        /** 设置此dialog的布局大小站总布局的 宽的3/7 , 高的 3/5 */
        params.width = width * 1 / 3;
        params.height = height * 2 / 5;
        // System.out.println("Ww + Hh = "+params.width+" + "+params.height);
        loadingLayout.setLayoutParams(params);

        loadingUpDlg.setCancelable(false);
        loadingUpDlg.setCanceledOnTouchOutside(false);
        loadingUpDlg.show();
    }


    /**
     * 升级对话框消失
     */
    public void updataDlgdismiss() {
        if (upApkDlg != null && upApkDlg.isShowing()) {
            upApkDlg.dismiss();
        }
    }


    /**
     * 升级对话框
     *
     * @param surebtstr  按钮的文字
     * @param inter      点击事件
     * @param version    版本号
     * @param clientText 更新信息
     */
    public void updataDlg(String surebtstr, final DialogInterface inter, String version, String clientText) {
        if (upApkDlg != null && upApkDlg.isShowing()) {
            upApkDlg.dismiss();
            return;
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_updata_apk, null);

        if (clientText != null) {
            String[] updateInfos = clientText.split("\\|");
            //更新信息
            ListView update_infos = (ListView) view.findViewById(R.id.update_infos);
            //            update_infos.setAdapter(new UpateInfoAdapter(mContext, updateInfos));
        }

        //版本
        TextView textViewVersion = (TextView) view.findViewById(R.id.dlg_upapk_version);

        if (!TextUtils.isEmpty(version)) {
            textViewVersion.setText("V" + version + "s版本");
        }
        upApkDlg = new Dialog(mContext, R.style.FullHeightDialog);
        upApkDlg.setContentView(view);

        /** 设置选择框居中显示 */
        Window window = upApkDlg.getWindow();
        window.setGravity(Gravity.CENTER);
        /** 得到 全部屏幕的大小 宽和高 （width，height） */
        Display d = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        // System.out.println("W + H = "+d.getWidth()+" + "+d.getHeight());

        int width = d.getWidth();
        int height = d.getHeight();
        /** 得到dialog布局的总布局 */
        LinearLayout promptLayout = (LinearLayout) view.findViewById(R.id.ll_upapk);
        android.view.ViewGroup.LayoutParams params = (android.view.ViewGroup.LayoutParams) promptLayout
                .getLayoutParams();
        /** 设置此dialog的布局大小 */
        params.width = CommonUtil.dip2px(mContext, 290);
        params.height = CommonUtil.dip2px(mContext, 400);
        // System.out.println("Ww + Hh = "+params.width+" + "+params.height);
        promptLayout.setLayoutParams(params);

        Button promptSure_tv = (Button) view.findViewById(R.id.bt_dlg_upapk);
        promptSure_tv.setText(surebtstr);
        promptSure_tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (inter != null)
                    inter.sure(null);
            }
        });

        upApkDlg.setOnKeyListener(new android.content.DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(android.content.DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                } else {
                    return false; //默认返回 false，这里false不能屏蔽返回键，改成true就可以了
                }
            }
        });

        upApkDlg.setCanceledOnTouchOutside(false);
        upApkDlg.setCancelable(true);
        upApkDlg.show();
    }


    /**
     * 账单对话框
     *
     * @param repayStatus 账单每期状态
     * @param inter       点击事件
     * @param date        还款时间
     * @param tbody       弹框 文本后面的文字 记得 加 换行符
     */
    public void billItemDlg(String repayStatus, final DialogInterface inter, String date, String tbody) {
        if (billItemDlg != null && billItemDlg.isShowing()) {
            billItemDlg.dismiss();
            return;
        }


        //                            "502101：结清
        //                            502102：未结清
        //                            502103：逾期
        //                            502104：本息结清
        boolean isHuanKuan = true;
        switch (repayStatus) {
            case "502101":
                isHuanKuan = true;
                break;
            case "502102":
                isHuanKuan = false;
                break;
            case "502103":
                isHuanKuan = false;
                break;
            case "502104":
                isHuanKuan = false;
                break;
        }


        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_bill_item, null);


        LinearLayout mLlDlgBill = (LinearLayout) view.findViewById(R.id.ll_dlg_bill);
        TextView mTvDlgBillTitle = (TextView) view.findViewById(R.id.tv_dlg_bill_title);
        TextView mTvDlgBillText = (TextView) view.findViewById(R.id.tv_dlg_bill_text);
        TextView mTvDlgBillDate = (TextView) view.findViewById(R.id.tv_dlg_bill_date);
        TextView mTvDlgBillTh = (TextView) view.findViewById(R.id.tv_dlg_bill_th);
        TextView mTvDlgBillTbody = (TextView) view.findViewById(R.id.tv_dlg_bill_tbody);
        ImageView mImgDlgBillClose = (ImageView) view.findViewById(R.id.img_dlg_bill_close);

        TextView tvDlgBillQuhuankuan = (TextView) view.findViewById(R.id.tv_dlg_bill_quhuankuan);

        if (!TextUtils.isEmpty(date)) {
            String tempDate = "还款时间：" + date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
            mTvDlgBillDate.setText(tempDate);
        }

        LogUtil.e("tbdoy:", tbody + "===========tbody");

        mTvDlgBillTbody.setText(tbody);

        // 判断是否逾期
        if (TextUtils.equals(repayStatus, "502103")) {
            mLlDlgBill.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.img_bill_item_balck));
            mTvDlgBillTitle.setText("逾期");
            mTvDlgBillText.setText("您的账单已逾期，请您尽快还款");
            // 弹框 文本前面的文字 记得加 换行符
            mTvDlgBillTh.setText("应还本金：\n应还利息：\n逾期罚息：\n应还手续费：\n逾期天数： ");
            tvDlgBillQuhuankuan.setText("去还款");
        } else {
            mLlDlgBill.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.img_bill_item_red));
            //判断是否还款
            if (isHuanKuan) {
                mTvDlgBillTitle.setText("已还款");
                mTvDlgBillText.setText("您本期账单已出还清");
                tvDlgBillQuhuankuan.setText("本期已还");
                tvDlgBillQuhuankuan.setEnabled(false);
            } else {
                mTvDlgBillTitle.setText("还款中");
                mTvDlgBillText.setText("您本期账单已出，请您尽快还款");
                tvDlgBillQuhuankuan.setText("去还款");
            }
            mTvDlgBillTh.setText("应还本金：\n应还利息：");
        }


        mImgDlgBillClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inter != null)
                    inter.cancel(null);
            }
        });
        tvDlgBillQuhuankuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inter != null)
                    inter.sure(null);
            }
        });

        billItemDlg = new Dialog(mContext, R.style.FullHeightDialog);
        billItemDlg.setContentView(view);

        /** 设置选择框居中显示 */
        Window window = billItemDlg.getWindow();
        window.setGravity(Gravity.CENTER);
        /** 得到 全部屏幕的大小 宽和高 （width，height） */
        Display d = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        // System.out.println("W + H = "+d.getWidth()+" + "+d.getHeight());

        int width = d.getWidth();
        int height = d.getHeight();
        /** 得到dialog布局的总布局 */
        RelativeLayout promptLayout = (RelativeLayout) view.findViewById(R.id.rl_dlg_bill);
        android.view.ViewGroup.LayoutParams params = (android.view.ViewGroup.LayoutParams) promptLayout
                .getLayoutParams();
        /** 设置此dialog的布局大小 */
        params.width = CommonUtil.dip2px(mContext, 290);
        params.height = CommonUtil.dip2px(mContext, 400);
        // System.out.println("Ww + Hh = "+params.width+" + "+params.height);
        promptLayout.setLayoutParams(params);


        billItemDlg.setOnKeyListener(new android.content.DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(android.content.DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                } else {
                    return false; //默认返回 false，这里false不能屏蔽返回键，改成true就可以了
                }
            }
        });

        billItemDlg.setCanceledOnTouchOutside(false);
        billItemDlg.setCancelable(true);
        billItemDlg.show();
    }


}
