package com.zhph.commonlibrary.views.editTextView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.example.library.BandCardEditText;
import com.example.library.BankInfo;
import com.zhph.commonlibrary.R;
import com.zhph.commonlibrary.utils.CommonUtil;


/**
 * Created by liangwenke on 2016/10/8.
 * <p>
 * 带清除文本和自动格式化银行卡号的EditText，每四位增加一个空格，并根据银行卡号判断该银行卡归属的银行及卡别
 */


/**
 * 使用方法：tv = (TextView) findViewById(R.id.tv);
 * editText = (BandCardEditText) findViewById(R.id.et);
 * editText.setBankCardListener(new BandCardEditText.BankCardListener() {
 *
 * @Override public void success(String name) {
 * tv.setText("所属银行：" + name);
 * }
 * @Override public void failure() {
 * tv.setText("所属银行：");
 * }
 * });
 */
public class BankCardEditText extends EditText implements
        View.OnFocusChangeListener, TextWatcher {
    private boolean shouldStopChange = false;
    private final String WHITE_SPACE = "-";
    private Drawable mClearDrawable;
    private BandCardEditText.BankCardListener listener;
    private boolean isvisible = true;
    private Context context;
    public BankCardEditText(Context context) {
        this(context, null);
    }

    public BankCardEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public BankCardEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
        // setShakeAnimation();
    }

    public void setIsvisible(boolean isvisible) {
        this.isvisible = isvisible;
    }

    private void init() {
        format(getText());
        shouldStopChange = false;
        setFocusable(true);
        setEnabled(true);
        setFocusableInTouchMode(true);
        addTextChangedListener(new BankCardEditText.CardTextWatcher());
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources()
                    .getDrawable(R.drawable.emotionstore_progresscancelbtn);
        }
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isvisible && getCompoundDrawables()[2] != null) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                boolean touchable = event.getX() > (getWidth()
                        - getPaddingRight() - mClearDrawable.getIntrinsicWidth())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable && BankCardEditText.this.hasFocus()) {//获得焦点
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!isvisible) {
            return;
        }
        mClearDrawable.setBounds(0, 0, CommonUtil.dip2px(context,15), CommonUtil.dip2px(context,15));
        this.setCompoundDrawablePadding(30);//设置图片和text之间的间距
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int count,
                              int after) {
        setClearIconVisible(s.length() > 0);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }


    class CardTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            format(editable);
        }
    }

    private void format(Editable editable) {
        if (shouldStopChange) {
            shouldStopChange = false;
            return;
        }

        shouldStopChange = true;

        String str = editable.toString().trim().replaceAll(WHITE_SPACE, "");
        int len = str.length();
        int courPos;

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append(str.charAt(i));
            if (i == 3 || i == 7 || i == 11 || i == 15) {
                if (i != len - 1)
                    builder.append(WHITE_SPACE);
            }
        }
        courPos = builder.length();
        setText(builder.toString());
        setSelection(courPos);
        if (listener != null) {
            if (isBankCard()) {
                char[] ss = getBankCardText().toCharArray();
                listener.success(BankInfo.getNameOfBank(ss, 0));
            } else {
                listener.failure();
            }
        }
    }

    public String getBankCardText() {
        return getText().toString().trim().replaceAll(" ", "");
    }

    public boolean isBankCard() {
        return checkBankCard(getBankCardText());
    }

    /**
     * 校验银行卡卡号
     */
    public boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     */
    public char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (TextUtils.isEmpty(nonCheckCodeCardId)
                || !nonCheckCodeCardId.matches("\\d+")
                || nonCheckCodeCardId.length() < 16
                || nonCheckCodeCardId.length() > 19) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    public void setBankCardListener(BandCardEditText.BankCardListener listener) {
        this.listener = listener;
    }

    public interface BankCardListener {
        void success(String name);

        void failure();
    }
}
