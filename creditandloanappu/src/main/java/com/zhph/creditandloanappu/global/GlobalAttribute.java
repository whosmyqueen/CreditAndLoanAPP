package com.zhph.creditandloanappu.global;

/**
 * 作者：machao on 2016/3/15 11:55
 * Created by Administrator on 2016/3/15.
 */
public interface GlobalAttribute {
    /**
     * 加密key
     */
    String ENCODE_KEY = "4A1AA30CF30DFCC86707E9D3DF118BCF";


    String CATCH_USER_INFO = "catchUserInfo";

    /**
     * 商户
     */
    String BUSINESS = "Business";

    /**
     * 门店
     */
    String STORE = "Store";

    /**
     * 产品
     */
    String PRODUCT = "Product";

    /**
     * 渠道
     */
    String CHANNEL = "channel";


    /**
     * 登陆成功标记
     */
    String USER_NAME = "user_name";

    /**
     * 立即分期页面的标记
     */
    String STAGING_SPREADSHEET_ACTIVITY = "StagingSpreadsheetActivity";

    /**
     * 分期试算界面标记
     */
    String STAGING_ACTIVITY = "stagingActivity";

    /**
     * 民族的标记
     */
    String NATION = "nation";

    /**
     * 客户类型
     */
    String CUST_TYPE = "cust_type";


    /**
     * 住房性质
     */
    String HOUSE = "house";


    /**
     * 户籍省
     */
    String UNIT_PROV = "unitProv";


    /**
     * 所属行业
     */
    String INDUSTRY = "industry";


    /**
     * 户籍市
     */
    String REG_CITY = "regCity";

    /**
     * 户籍区
     */
    String REG_AREA = "regArea";

    /**
     * 客户类型
     */
    String CUST_TYPE_KEY = "cust_type_key";

    /**
     * 关系标记
     */
    String RELATION = "relation";

    /**
     * 全部合同页面标记
     */
    String ALL_CONTRACT_ACTIVITY = "AllContractActivity";


    /**
     * 订单状态对象key
     */
    String LOAN_STATE_KEY = "loanState";

    String REAL_NAME = "realname";

    /**
     * 是否实名 认证标记
     */
    String AUTONYM = "autonym";

    /**
     * 分页每页个数
     */
    int PAGE_SIZE = 5;

    /**
     * 进件号标识
     */
    String LOAN_APPLY_KEY = "loanApplyKey";

    /**
     * 页面是否禁用
     */
    String VIEW_IS_FORBIDDEN = "isChange";

    /**
     * 订单时间
     */
    String ORDER_DATE = "orderDate";
    /**
     * 审核状态名
     */
    String AUDIT_STATE_NAME = "auditStateName";
    /**
     * 品牌
     */
    String BRAND = "brand";
    /**
     * 商品名称
     */
    String GOODS_NAME = "goodsName";
    /**
     * 审核结果
     */
    String AUDIT_RESULT = "auditResult";
    /**
     * 审核状态
     */
    String AUDIT_STATE = "auditState";
    /**
     * 审核状态
     */
    String IS_SIGNED = "isSigned";

    /**
     * 文件类型名
     */
    String LIB_TYPE = "libType";
    /**
     * 图片文件
     */
    String IMAGE_FILE = "imageFile";

    /**
     * 用户编号
     */
    String CUST_NO = "custNo";

    /**
     * 数据下标
     */
    String INDEX = "index";

    /**
     * 签名code
     */
    int SIGN_CODE = 1001;


    /**
     * 身份认证code
     */
    int IDENTITY_AURHENTICATION_CODE = 2001;

    /**
     * 身份认证状态
     */
    String IS_IDENTITY_AURHENTICATION_STATE = "isIdentityAuthenticationState";

    /**
     * 身份认证通过的标记
     */
    String USER_ATTESTATION_STATE = "user_attestation_state";

    /**
     * 登录账号标记
     */
    String LOGIN_NAME = "userPhone";

    /**
     * 是否下一步1,0不是
     */
    String IS_NEXT_TAP = "flagintent";

    /**
     * 二维码key
     */
    String TWO_CODE = "twoCode";

    String TAG = "tag";

    /**
     * 婚姻关系
     */
    String maritalRelationship = "maritalrelationship";
    /**
     * 认证类型
     */
    String AUDIT_TYPE = "authenticationType";
}
