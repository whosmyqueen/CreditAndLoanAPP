package com.zhph.creditandloanappy.saripaar;

import android.content.Context;

import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.annotation.ValidateUsing;
import com.zhph.commonlibrary.utils.CommonUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2017/2/24.
 */
@ValidateUsing(Verification.VerificationRule.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Verification {

    String message() default "数据为空";

    String message2() default "格式错误";

    int sequence() default -1;

    int messageResId() default -1;

    VerificationType types() default VerificationType.text;

    boolean isEmpty() default false;

    class VerificationRule extends AnnotationRule<Verification, String> {

        private String currentMessage;

        protected VerificationRule(Verification rule) {
            super(rule);
        }

        @Override
        public boolean isValid(String data) {
            currentMessage = "";
            switch (mRuleAnnotation.types()) {
                case text:
                    return textVerification(data.replace(" ", ""));
                case phone:
                    return phoneVerification(data.replace(" ", ""));
                case name:
                    return nameVerification(data.replace(" ", ""));
                case idCard:
                    return idCardVerification(data.replace(" ", ""));
                case bankCard:
                    return bankCardVerification(data.replace(" ", ""));
                case email:
                   return emailVerification(data.replace(" ", ""));
                default:
                    return false;
            }

        }

        @Override
        public String getMessage(Context context) {
            return currentMessage;
        }

        /**
         * 名字检测
         *
         * @param data
         * @return
         */
        private Boolean nameVerification(String data) {
            if (CommonUtil.judgeNull(data)) {
                if (CommonUtil.checkName(data)) {
                    return true;
                } else {
                    currentMessage = mRuleAnnotation.message2();
                    return false;
                }
            } else {
                if (mRuleAnnotation.isEmpty()) {
                    return true;
                }
                currentMessage = mRuleAnnotation.message();
                return false;
            }
        }


        /**
         * 手机号检测
         *
         * @param data
         * @return
         */

        private Boolean phoneVerification(String data) {
            if (CommonUtil.judgeNull(data)) {
                if (CommonUtil.checkPhone(data)) {
                    return true;
                } else {
                    currentMessage = mRuleAnnotation.message2();
                    return false;
                }
            } else {
                if (mRuleAnnotation.isEmpty()) {
                    return true;
                }
                currentMessage = mRuleAnnotation.message();
                return false;
            }
        }

        /**
         * 身份证号检测
         *
         * @param data
         * @return
         */

        private Boolean idCardVerification(String data) {
            if (CommonUtil.judgeNull(data)) {
                if (CommonUtil.checkIdentity(data)) {
                    return true;
                } else {
                    currentMessage = mRuleAnnotation.message2();
                    return false;
                }
            } else {
                if (mRuleAnnotation.isEmpty()) {
                    return true;
                }
                currentMessage = mRuleAnnotation.message();
                return false;
            }
        }

        /**
         * 银行卡检测
         *
         * @param data
         * @return
         */

        private Boolean bankCardVerification(String data) {
            if (CommonUtil.judgeNull(data)) {
                if (CommonUtil.checkBankCard(data)) {
                    return true;
                } else {
                    currentMessage = mRuleAnnotation.message2();
                    return false;
                }
            } else {
                if (mRuleAnnotation.isEmpty()) {
                    return true;
                }
                currentMessage = mRuleAnnotation.message();
                return false;
            }
        }

        /**
         * 文字判空
         *
         * @param data
         * @return
         */

        private Boolean textVerification(String data) {
            if (CommonUtil.judgeNull(data)) {
                return true;
            } else {
                if (mRuleAnnotation.isEmpty()) {
                    return true;
                }
                currentMessage = mRuleAnnotation.message();
                return false;
            }
        }

        /**
         * 邮箱检测
         *
         * @param data
         * @return
         */

        private Boolean emailVerification(String data) {
            if (CommonUtil.judgeNull(data)) {
                if (CommonUtil.checkEmail(data)) {
                    return true;
                } else {
                    currentMessage = mRuleAnnotation.message2();
                    return false;
                }
            } else {
                if (mRuleAnnotation.isEmpty()) {
                    return true;
                }
                currentMessage = mRuleAnnotation.message();
                return false;
            }
        }
    }
}
