package com.leicx.constant;

/**
 * payU支付字段常量
 *
 * @author daxiong
 * @date 2019-11-21 15:35
 */
public class PayuStringConstant {
    public static final String CHAR_SET = "UTF-8";

    // -------------请求头字段常量--------------
    public static final String APP_ID_KEY = "app_id";
    public static final String PRIVATE_KEY_KEY = "private_key";
    public static final String PUBLIC_KEY_KEY = "public_key";
    public static final String API_VERSION_KEY = "api-version";
    public static final String X_PAYMENTS_OS_ENV_KEY = "x-payments-os-env";
    public static final String IDEMPOTENCY_KEY_KEY = "idempotency_key";

    public static final String APP_ID_VALUE = "your appid";
    public static final String PRIVATE_KEY_VALUE = "your private key";
    public static final String PUBLIC_KEY_VALUE = "your public key";
    public static final String API_VERSION_VALUE = "1.3.0";
    // 运行环境，"live" or "test"
    public static final String X_PAYMENTS_OS_ENV_VALUE = "live";

}
