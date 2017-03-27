package com.zhph.commonlibrary.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

/**
 * Created by zwl on 16/9/30.
 */

public class CommonUtil {

    public static Context mBaseContext;

    public static LocationManager lm;
    private static AlertDialog dlg;
    private static SharedPreferences sp;
    private static String MESSAGE = "";


    public static void init(Context context) {
        mBaseContext = context;
    }

    /**
     * 判断集合是否为null或者0个元素
     *
     * @param c
     * @return
     */
    public static boolean isNullOrEmpty(Collection c) {
        return null == c || c.isEmpty();
    }

    public static void installApk(Context context, File file) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.fromFile(file),
                "applicationnd.android.package-archive");
        context.startActivity(i);
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    /**
     * 获取页面的跟布局
     *
     * @param context
     * @return
     */
    public static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }


    public static CharSequence trimEmptyString(CharSequence s) {
        if (s != null) {
            return s.toString().trim();
        }
        return "";
    }

    public static CharSequence replaceEmptyString(CharSequence s) {
        if (s != null) {
            return s.toString().replace(" ", "");
        }
        return "";
    }

    /**
     * 从sp中取出字符串数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public static Object get4SP(String key, Object defValue) {
        if (sp == null) {
            sp = mBaseContext.getSharedPreferences("guide", Context.MODE_PRIVATE);
        }

        if (defValue instanceof String) {
            return sp.getString(key, (String) defValue);
        } else if (defValue instanceof Boolean) {
            return sp.getBoolean(key, (boolean) defValue);
        } else if (defValue instanceof Float) {
            return sp.getFloat(key, (float) defValue);
        } else if (defValue instanceof Integer) {
            return sp.getInt(key, (int) defValue);
        } else if (defValue instanceof Long) {
            return sp.getLong(key, (long) defValue);
        } else {
            return null;
        }
    }

    /**
     * 将view 从它 的父view 中移除
     */
    public static void removeSelfFromParent(View view) {
        if (view != null) {
            if (view.getParent() != null && view.getParent() instanceof ViewGroup) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        }
    }

    /**
     * 返回查询条件
     *
     * @return
     */
    private static Criteria getCriteria() {
        Criteria criteria = new Criteria();
        //设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        //设置是否需要方位信息
        criteria.setBearingRequired(false);
        //设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    /**
     * dp与px转换关系
     * dp转换为px
     *
     * @param context
     * @param dp      * @return
     */
    public static int dip2px(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        // 四舍五入的操作
        return (int) (dp * density + 0.5);
    }

    /**
     * px转换为dp
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2pid(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }


    /**
     * 清除sp
     *
     */

    /**
     * 将字符串数据存入sp
     *
     * @param key
     * @param value
     */
    public static void set2SP(String key, Object value) {
        if (sp == null) {
            sp = mBaseContext.getSharedPreferences("guide", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sp.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (float) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (int) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (long) value);
        }
        editor.commit();
    }

    /**
     * 删除保存的所有信息
     */
    public static void clear() {
        sp = mBaseContext.getSharedPreferences("guide", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }


    /**
     * 检测手机号是否合法
     */
    public static boolean checkPhone(String phone_regist) {
        String phone = phone_regist.toString().replace(" ", "");
        Pattern p = Pattern.compile("^(1[0123456789]{10})|(0[0-9]{2,3}){0,1}" + "([2-9][0-9]{6,7})$");
        Matcher m = p.matcher(phone);
        return m.matches();
        //        return false;
    }

    /**
     * 检测手机号是否合法
     */
    public static boolean checkPhone(EditText phone_regist) {
        String phone = phone_regist.getText().toString().replace(" ", "");
        Pattern p = Pattern.compile("^(1[0123456789]{10})|(0[0-9]{2,3}){0,1}" + "([2-9][0-9]{6,7})$");
        Matcher m = p.matcher(phone);
        return m.matches();
        //        return false;
    }

    /**
     * 检测身份证是否合格
     *
     * @param phone_regist
     * @return
     */
    public static boolean sada(EditText phone_regist) {
        String phone = phone_regist.getText().toString().replace(" ", "");
        Pattern p = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
        Matcher m = p.matcher(phone);
        return m.matches();
        //        return false;
    }

    /**
     * 银行卡号校验
     */
    public static boolean isBankNumber(String bankNumber) {
        char[] cc = bankNumber.toCharArray();
        int[] n = new int[cc.length + 1];
        int j = 1;
        for (int i = cc.length - 1; i >= 0; i--) {
            n[j++] = cc[i] - '0';
        }
        int even = 0;
        int odd = 0;
        for (int i = 1; i < n.length; i++) {
            if (i % 2 == 0) {
                int temp = n[i] * 2;
                if (temp < 10) {
                    even += temp;
                } else {
                    temp = temp - 9;
                    even += temp;
                }
            } else {
                odd += n[i];
            }
        }

        int total = even + odd;
        if (total % 10 == 0)
            return true;
        return false;
    }

    /**
     * 检测密码是否合格
     */
    public static boolean checkPassWord(String pwd_regist) {
        String phone = pwd_regist.toString().trim().replace(" ", "");
        Pattern p = Pattern.compile("^\\S{6,16}$");
        //        Pattern p = Pattern.compile("^[\\@a-z0-9\\!\\#\\$\\%\\^\\&\\*\\.\\~]{6,16}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 检测银行卡号是否合法
     */
    public static boolean checkBankCard(String phone_regist) {
        String phone = phone_regist.replace("-", "");
        Pattern p = Pattern.compile("^(\\d{15}|\\d{16}|\\d{17}|\\d{18}|\\d{19}|\\d{20}|\\d{21})$");
        Matcher m = p.matcher(phone);
        return m.matches();
        //        return false;
    }

    /**
     * 获取设备唯一标识
     */
    public static String getDeviceUniqueID() {

        String uniqueId = "";
        // deviceId
        TelephonyManager telephonyManager = (TelephonyManager) mBaseContext.getSystemService(Context
                .TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        if (!TextUtils.isEmpty(deviceId)) {
            uniqueId += deviceId;
        } else {
            uniqueId += "null_";
            WifiManager wifi = (WifiManager) mBaseContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            String mac = info.getMacAddress();
            if (!TextUtils.isEmpty(mac)) { // Wifi Mac Address
                uniqueId += mac.replace(":", "");
            } else {
                uniqueId += "null_";
                String androidId = Settings.Secure.getString(mBaseContext.getContentResolver(), Settings.Secure
                        .ANDROID_ID);
                // ANDROID_ID
                if (androidId != null && !"9774d56d682e549c".equals(androidId.toLowerCase())) {
                    uniqueId += androidId;
                } else { // uuid
                    uniqueId += UUID.randomUUID();
                }
            }
        }
        // QLog.LOGD("udid:"+uniqueId);
        return uniqueId;
    }

    /**
     * 获取当前Apk的版本信息
     */
    public static PackageInfo getVersion() {


        PackageInfo info = null;

        PackageManager manager = mBaseContext.getPackageManager();

        try {

            info = manager.getPackageInfo(mBaseContext.getPackageName(), 0);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return info;
    }

    /**
     * 将json字符串转成Map,排序key
     *
     * @param jsonStr
     * @return
     */
    public static String convertJsonToMap(String jsonStr) {
        if (jsonStr == null) {
            return null;
        }
        StringBuffer paramStr = new StringBuffer();
        try {
            Gson gson = new Gson();
            TreeMap paramMap = gson.fromJson(jsonStr, TreeMap.class);
            if (null != paramMap) {
                Iterator it = paramMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    if (!"key".equals(entry.getKey())) {
                        paramStr.append(entry.getValue());
                    }
                }
            }

        } catch (Exception e) {

        }
        return paramStr.toString();
    }

    /**
     * 获取当钱应用的版本名称
     *
     * @return
     */
    public static String getAPKVersonName() throws PackageManager.NameNotFoundException {
        //        String versonName = null;
        //        PackageManager packageManager = getContext().getPackageManager();
        //        try {
        //            PackageInfo packageInfo = packageManager.getPackageInfo(getContext().getPackageName(), 0);
        //
        //            versonName = packageInfo.versionName;
        //        } catch (PackageManager.NameNotFoundException e) {
        //            e.printStackTrace();
        //        }
        //        return versonName;


        // 获取packagemanager的实例
        PackageManager packageManager = mBaseContext.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(mBaseContext.getPackageName(), PackageManager
                .GET_CONFIGURATIONS);
        String version = packInfo.versionName;
        String version2 = packInfo.packageName;
        int version3 = packInfo.versionCode;


        return version;
    }

    //    /**
    //     * 计算月供价格
    //     *
    //     * @param total      总价
    //     * @param firstPay   首付
    //     * @param monthNum   分期月数
    //     * @param serviceNum 服务费比例
    //     * @return
    //     */
    //    public static float getMonthPay(float total, float firstPay, int monthNum, float serviceNum) {
    //        float result;
    //        result = ((total - firstPay) + (total * serviceNum)) / monthNum;
    //        return result;
    //    }

    //    /**
    //     * 截取城市名称
    //     *
    //     * @param list
    //     * @return
    //     */
    //    public static List<CityInfoBean> splitCity(List<CityInfoBean> list) {
    ////        int k = 0;
    //        for (int i = 0; i < list.size() - 1; i++) {
    //            String cityName = list.get(i).getItemName();
    //            for (int j = 0; j < cityName.length() - 1; j++) {
    //                if ("省".equals(cityName.substring(j, j + 1))) {
    //                    Log.e("tag", cityName);
    //                    cityName = cityName.split("省")[1];
    //                    break;
    //                }
    //            }
    ////            for (int j = 0; j < cityName.length() - 3; j++) {
    ////                if ("自治区".equals(cityName.substring(j, j + 3))) {
    ////                    cityName = cityName.split("自治区")[1];
    ////                }
    ////            }
    //
    //            list.get(i).setItemName(cityName);
    //        }
    //
    //        return list;
    //    }

    /**
     * 隐藏键盘
     *
     * @param activity 当前页面所在
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) mBaseContext.getSystemService(mBaseContext
                .INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }

    }

    /**
     * 显示或者隐藏按钮
     */
    public static void hideAndShowBtn(ImageView imageView) {
        if (imageView.getVisibility() == View.VISIBLE) {
            imageView.setVisibility(View.INVISIBLE);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 检测系统版本是否为6.0
     */
    public static boolean canMakeSmores() {

        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);

    }

    /**
     * 检测系统版本是否为6.0以上
     */
    public static boolean checkIsMUp() {

        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);

    }

    /**
     * 复制单个文件
     *
     * @param srcFileName 待复制的文件名
     * @param overlay     如果目标文件存在，是否覆盖
     * @return 如果复制成功返回true，否则返回false
     */
    public static boolean copyFile(String srcFileName, String destFileName, boolean overlay) {
        File srcFile = new File(Environment.getExternalStorageDirectory(), srcFileName);

        // 判断源文件是否存在
        if (!srcFile.exists()) {
            LogUtils.e(mBaseContext, "源文件不存在");
            return false;
        } else if (!srcFile.isFile()) {
            LogUtils.e(mBaseContext, "不是一个文件");
            return false;
        }

        // 判断目标文件是否存在
        File destFile = new File(Environment.getExternalStorageDirectory(), destFileName);
        if (destFile.exists()) {
            // 如果目标文件存在并允许覆盖
            if (overlay) {
                // 删除已经存在的目标文件，无论目标文件是目录还是单个文件
                new File(destFileName).delete();
            }
        } else {
            // 如果目标文件所在目录不存在，则创建目录
            if (!destFile.getParentFile().exists()) {
                // 目标文件所在目录不存在
                if (!destFile.getParentFile().mkdirs()) {
                    // 复制文件失败：创建目标文件所在目录失败
                    return false;
                }
            }
        }

        // 复制文件
        int byteread = 0; // 读取的字节数
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];

            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @param activity
     * @param items
     * @param title
     */
    public static void popupDlg(final Activity activity, final CharSequence[] items, String title, final TextView
            textView) {
        dlg = new AlertDialog.Builder(activity).setTitle(title).setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 1) {
                    textView.setText(items[1]);
                } else {
                    textView.setText(items[0]);
                }
                dlg.dismiss();
            }
        }).create();
        dlg.show();
    }

    /**
     * 判断输入框是否为空
     *
     * @param editText
     * @param result
     */
    public static boolean judgeNull(EditText editText, String result) {
        String text = editText.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            ToastUtil.showToast(result);
            return false;
        } else if (text.contains("\"")) {
            ToastUtil.showToast("禁止输入 \" 符号");
            return false;
        }
        return true;
    }

    /**
     * 判断输入框是否为空
     *
     * @param textValue
     */
    public static boolean judgeNull(String textValue) {
        if (TextUtils.isEmpty(textValue.trim())) {
            return false;
        }
        return true;
    }

    /**
     * 判断验证码格式是否正确
     *
     * @param textValue
     * @return
     */
    public static boolean checkVerificationCode(String textValue) {
        if (textValue.trim().length() > 6 || textValue.trim().length() <= 3) {
            return false;
        }
        return true;
    }


    /**
     * 手机号码，固话均可
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;

        String expression = "^(0[0-9]{2,3}){0,1}([2-9][0-9]{7,8})$";
        //        String expression = "^(0[0-9]{2,3})?([2-9][0-9]{7,8})?$";
        CharSequence inputStr = phoneNumber;

        Pattern pattern = Pattern.compile(expression);

        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }

        return isValid;

    }

    /**
     * 判断用户是否选择
     *
     * @param textView
     * @param result
     */
    public static boolean judgeSelect(TextView textView, String result) {
        if (TextUtils.isEmpty(textView.getText().toString().trim())) {
            ToastUtil.showToast(result);
            return false;
        }
        return true;
    }


    /**
     * 家庭关系是否知晓弹出
     *
     * @param activity
     * @param items
     * @param title
     * @param textView
     */
    public static void popupDlgList1(final Activity activity, final String[] items, String title, final TextView
            textView) {
        dlg = new AlertDialog.Builder(activity).setTitle(title).setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                textView.setText((items[item]));

                dlg.dismiss();
            }
        }).create();
        dlg.show();
    }


    /**
     * 弹框多个
     *
     * @param activity
     * @param items
     * @param title
     * @param textView
     */
    public static void popupDlgList(final Activity activity, final String[] items, String title, final TextView
            textView) {
        dlg = new AlertDialog.Builder(activity).setTitle(title).setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                textView.setText((items[item].split(",")[0]));
                textView.setHint(items[item].split(",")[1]);
                dlg.dismiss();
            }
        }).create();
        dlg.show();
    }

    /**
     * 邮箱验证
     */


    //判断email格式是否正确
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))"
                + "([a-zA-Z]{2," +
                "4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }


    public static void popupDlgList(final Activity activity, final String[] items, String title, final TextView
            textView, final String[]
                                            codes) {
        dlg = new AlertDialog.Builder(activity).setTitle(title).setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                textView.setText(items[item]);
                textView.setHint(codes[item]);
                dlg.dismiss();
            }
        }).create();
        dlg.show();
    }

    public static void popupDlgList(final Activity activity, final String[] items, String title, DialogInterface
            .OnClickListener
            onClickListener) {
        dlg = new AlertDialog.Builder(activity).setTitle(title).setItems(items, onClickListener).create();
        dlg.show();
    }

    /**
     * 二进制转字符串
     *
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) // 二进制转字符串
    {
        StringBuffer sb = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                sb.append("0" + stmp);
            } else {
                sb.append(stmp);
            }
        }
        return sb.toString();
    }

    /**
     * 把二进制文件读入字节数组，如果没有内容，字节数组为null
     *
     * @param filePath
     * @return
     */
    public static byte[] read(String filePath) {

        try {

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath));
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream(in.available());


                byte[] data1 = new byte[1024];
                int len = 0;
                while ((len = in.read(data1)) != -1) {
                    b.write(data1);
                }
                //                in.read(data);
                return b.toByteArray();
            } finally {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 禁用页面所有控件
     *
     * @param viewGroup
     */
    public static void forViewChildren(ViewGroup viewGroup, int[] ids) {
        int count = viewGroup.getChildCount();
        boolean idOk = false;
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                if (vg.getChildCount() > 0) {
                    forViewChildren(vg, ids);
                }
            }
            for (int id : ids) {
                if (view.getId() == id) {
                    idOk = true;
                    break;
                }
            }
            if (!idOk)
                view.setEnabled(false);
        }
    }


    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "无版版号";
        }
    }


    public static boolean checkIdentity(EditText num) {
        return checkIdentity(num.getText().toString().trim());
    }

    /**
     * 身份证号码验证
     */
    public static boolean checkIdentity(String num) {


        String idcard = num.trim().replace(" ", "");

        /*"身份证验证通过!",
        "0身份证号码位数不对!",
		"0身份证号码出生日期超出范围或含有非法字符!",
		"0身份证号码校验错误!",
		"0身份证地区非法!"*/

        boolean iscd = false;

        boolean[] Errors = new boolean[]{true, false, false, false, false};


        //        String[] area = new String[]{11:"北京", 12:"天津", 13:"河北", 14:"山西", 15:"内蒙古", 21:"辽宁", 22:"吉林",
        // 23:"黑龙江", 31:
        //        "上海", 32:"江苏", 33:
        //        "浙江", 34:"安徽", 35:"福建", 36:"江西", 37:"山东", 41:"河南", 42:"湖北", 43:"湖南", 44:"广东", 45:"广西", 46:"海南",
        // 50:"重庆", 51:
        //        "四川", 52:"贵州", 53:"云南", 54:"西藏", 61:"陕西", 62:"甘肃", 63:"青海", 64:"宁夏", 65:"新疆", 71:"台湾", 81:"香港",
        // 82:"澳门", 91:
        //        "国外"};
        String area = "11, 12, 13, 14, 15, 21, 22, 23, 31, 32, 33, 34, 35, 36, 37, 41, 42, 43, 44, 45, 46, 50, 51, " +
                "52, 53, 54, 61, 62, " +
                "63, 64, 65, 71, 81, " + "82, 91";
        int Y;
        String JYM;
        int S;
        String M;
        String[] idcard_array = new String[]{};
        idcard_array = idcard.split("");

        String ereg = "";


        //        Pattern p = Pattern.compile("^[\\@a-z0-9\\!\\#\\$\\%\\^\\&\\*\\.\\~]{6,16}$");
        //地区检验

        if (idcard.length() >= 3) {
            if (!area.contains(idcard.substring(0, 2))) {
                iscd = Errors[4];
                return iscd;
            }

            //身份号码位数及格式检验
            switch (idcard.length()) {
                case 15:
                    if ((parseInt(idcard.substring(6, 8)) + 1900) % 4 == 0 || ((parseInt(idcard.substring(6, 8)) +
                            1900) % 100 == 0 &&
                            (parseInt(idcard.substring(6, 8)) + 1900) % 4 == 0)) {
                        ereg = "/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|" +
                                "(04|06|09|11)" +
                                "(0[1-9]|[1-2][0-9]|30)|02" + "(0[1-9]|[1-2][0-9]))[0-9]{3}$/";//测试出生日期的合法性
                    } else {
                        ereg = "/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|" +
                                "(04|06|09|11)" +
                                "(0[1-9]|[1-2][0-9]|30)|02" + "(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/";//测试出生日期的合法性
                    }
                    Pattern p = Pattern.compile(ereg);
                    Matcher m = p.matcher(idcard);
                    if (m.matches()) {
                        iscd = Errors[0];
                    } else {
                        iscd = Errors[2];
                    }
                    break;

                case 18:
                    //18位身份号码检测
                    //出生日期的合法性检查
                    //闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02
                    // (0[1-9]|[1-2][0-9]))
                    //平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02
                    // (0[1-9]|1[0-9]|2[0-8]))
                    if (parseInt(idcard.substring(6, 10)) % 4 == 0 || (parseInt(idcard.substring(6, 10)) % 100 == 0
                            && parseInt(idcard
                            .substring(6, 10)) % 4 == 0)) {
                        ereg = "^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|" +
                                "(04|06|09|11)" +
                                "(0[1-9]|[1-2][0-9]|30)|02" + "(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9X]$";//闰年出生日期的合法性正则表达式
                    } else {
                        ereg = "^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|" +
                                "(04|06|09|11)" +
                                "(0[1-9]|[1-2][0-9]|30)|02" + "(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9X]$";//平年出生日期的合法性正则表达式
                    }
                    Pattern p1 = Pattern.compile(ereg);
                    Matcher m1 = p1.matcher(idcard);

                    if (m1.matches()) {//测试出生日期的合法性
                        //计算校验位
                        //                    S = (parseInt(idcard_array[10])) * 7 + (parseInt(idcard_array[1]) +
                        // parseInt
                        // (idcard_array[11])) * 9 + (parseInt
                        // (idcard_array[2]) + parseInt(idcard_array[12])) * 10 + (parseInt(idcard_array[3]) +
                        // parseInt(idcard_array[13])
                        // ) * 5 + (parseInt
                        // (idcard_array[4]) + parseInt(idcard_array[14])) * 8 + (parseInt(idcard_array[5]) +
                        // parseInt(idcard_array[15]))
                        // * 4 + (parseInt
                        // (idcard_array[6]) + parseInt(idcard_array[16])) * 2 + parseInt(idcard_array[7]) * 1 +
                        // parseInt
                        // (idcard_array[8]) * 6 + parseInt
                        // (idcard_array[9]) * 3;
                        S = (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 7 + (parseInt(idcard_array[2])
                                + parseInt
                                (idcard_array[12])) * 9 +
                                (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 10 + (parseInt
                                (idcard_array[4]) + parseInt
                                (idcard_array[14])) * 5 +
                                (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 8 + (parseInt
                                (idcard_array[6]) + parseInt
                                (idcard_array[16])) * 4 +
                                (parseInt(idcard_array[7]) + parseInt(idcard_array[17])) * 2 + parseInt
                                (idcard_array[8]) * 1 + parseInt
                                (idcard_array[9]) * 6 +
                                parseInt(idcard_array[10]) * 3;
                        Y = S % 11;
                        JYM = "10X98765432";
                        M = JYM.substring(Y, Y + 1);//判断校验位

                        if (TextUtils.equals(M, idcard_array[18])) {
                            iscd = Errors[0]; //检测ID的校验位
                        } else {
                            iscd = Errors[3];
                        }
                    } else {
                        iscd = Errors[2];
                    }
                    break;
                default:
                    iscd = Errors[1];
                    break;
            }
        }

        return iscd;
    }


    public static void sendGPSInfo(String type, Activity activity) {

        Location location = CommonUtil.registerGPS(activity);
        if (location == null) {
            return;
        }
        JSONObject paramJson = new JSONObject();
        try {
            String custNo = CommonUtil.get4SP("custNo", "").toString();
            if (custNo == null || custNo.isEmpty()) {
                return;
            }
            paramJson.put("cust_no", custNo);
            paramJson.put("operation_type", type);
            paramJson.put("longitude", location.getLongitude());
            paramJson.put("latitude", location.getLatitude());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public static Location registerGPS(Activity activity) {
        lm = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

        //判断GPS是否正常启动
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(activity, "请开启GPS导航...", Toast.LENGTH_SHORT).show();
            //返回开启GPS导航设置界面
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            activity.startActivityForResult(intent, 0);
            return null;
        }

        //为获取地理位置信息时设置查询条件
        String bestProvider = lm.getBestProvider(getCriteria(), true);
        //获取位置信息
        //如果不设置查询要求，getLastKnownLocation方法传人的参数为LocationManager.GPS_PROVIDER
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager
                .PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager
                        .PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Location location = lm.getLastKnownLocation(bestProvider);
        //        Location location= lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        return location;
        //        updateView(location);
        //绑定监听，有4个参数
        //参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
        //参数2，位置信息更新周期，单位毫秒
        //参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
        //参数4，监听
        //备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新

        // 1秒更新一次，或最小位移变化超过1米更新一次；
        //注意：此处更新准确度非常低，推荐在service里面启动一个Thread，在run中sleep(10000);然后执行handler.sendMessage(),更新位置
        //        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
    }

    /**
     * 检测姓名格式
     */
    public static boolean checkName(String name) {
        //        if(Name.indexOf("\\n")!=-1||Name.indexOf("··")!=-1||Name.indexOf("..")!=-1||Name.indexOf("·.")
        // !=-1||Name.indexOf(".·")!=-1){
        //            return  false;
        //        }else if(Name.substring(0,1).equals(".")||Name.substring(0,1).equals("·")){
        //            return  false;
        //        }else if(Name.substring(Name.length()-1).equals(".")||Name.substring(Name.length()-1).equals("·")){
        //            return  false;
        //        }

        String ereg = "^[\\u4e00-\\u9fa5\\.·•]{1,16}$";
        Pattern p = Pattern.compile(ereg);
        Matcher m = p.matcher(name);
        return m.matches();

    }

    /**
     * 跳转到拨打电话界面的方法
     *
     * @param num 电话号码
     */
    public static void skipPhone(Context context, String num) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static CharSequence trimString(CharSequence s) {
        if (s != null) {
            return s.toString().replace(" ", "");
        }
        return "";
    }

    /**
     * 替换数字和逗号,点
     *
     * @param s
     * @return
     */
    public static String replaceNumAndBadChar(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        return s.replaceAll("\\d", "").replace(".", "").replace(",", "");

    }

    /**
     * 检测邮箱格式
     */
    public static boolean checkEmail(String phone_regist) {
        String phone = phone_regist.replace(" ", "");
        Pattern p = Pattern.compile("^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }


    public static void permissionSetting(Activity activity) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
        }
        activity.startActivity(localIntent);
    }

    public static String[] splitAddress(String adress) {
        String[] strings = new String[2];
        int cityIndex = adress.lastIndexOf("县");
        if (cityIndex == -1) {
            cityIndex = adress.lastIndexOf("区");
        }
        strings[0] = adress.substring(0, cityIndex + 1);
        strings[1] = adress.substring(cityIndex + 1, adress.length());
        return strings;
    }
}
