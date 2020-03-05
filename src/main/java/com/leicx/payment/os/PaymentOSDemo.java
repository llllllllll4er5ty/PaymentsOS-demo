package com.leicx.payment.os;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.leicx.payment.entity.AuthorizationRequestEntity;
import com.leicx.payment.entity.AuthorizationResult;
import com.leicx.payment.entity.BaseResponseEntity;
import com.leicx.payment.entity.BillingAddress;
import com.leicx.payment.entity.Order;
import com.leicx.payment.entity.PackageResponseEntity;
import com.leicx.payment.entity.PaymentMethod;
import com.leicx.payment.entity.PaymentRequestEntity;
import com.leicx.payment.entity.ProviderData;
import com.leicx.payment.entity.ReuseCustomerCardInfo;
import com.leicx.payment.entity.TokenRequestEntity;
import com.leicx.payment.entity.TokenResponseEntity;
import com.leicx.payment.entity.customer.CustomerPaymentMethodResponseEntity;
import com.leicx.payment.entity.customer.CustomerRequestEntity;
import com.leicx.util.HttpClientUtil;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * PaymentOS restful API demo
 * @author daxiong
 * @date 2019-11-11 14:36
 */
public class PaymentOSDemo {

    private static String TOKEN = "";
    private static String CUSTOMER_ID = "";

    // --------------请求地址常量---------------
    private static final String CREATE_TOKEN_URL = "https://api.paymentsos.com/tokens";
    private static final String CREATE_PAYMENT_URL = "https://api.paymentsos.com/payments";
    private String CREATE_AUTHORIZATION_URL = "https://api.paymentsos.com/payments/%s/authorizations";
    private String CREATE_CAPTURE_URL = "https://api.paymentsos.com/payments/%s/captures";
    private static final String CREATE_CUSTOEMR_URL = "https://api.paymentsos.com/customers";
    private String STORE_TOKEN_TO_CUSTOMER_URL = "https://api.paymentsos.com/customers/%s/payment-methods/%s";
    private String RETRIEVE_ALL_PAYMENT_METHODS_URL = "https://api.paymentsos.com/customers/%s/payment-methods";
    private String DELETE_PAYMENT_METHOD_URL = "https://api.paymentsos.com/customers/%s/payment-methods/%s";


    public static void main(String[] args) {
        authorizeAndCapture();
    }

    /**
     * 流程一：创建支付（授权和认证两步）
     * @author daxiong
     * @date 2019-11-12 09:43
     * @return void
     */
    private static void authorizeAndCapture() {

        /**
         * 最小交易金额：R$1
         */

        PaymentOSDemo paymentOSDemo = new PaymentOSDemo();
        // 1.token
        paymentOSDemo.createToken();
//        TOKEN = "c7277adb-e2bf-4b9f-a765-abaf9e5e4582";
        // 2.付款单
        paymentOSDemo.createPayment();
        // 3.授权
        paymentOSDemo.createAuthorization();
        // 4.捕获
//        paymentOSDemo.createCapture();
    }

    /**
     * 创建token
     * @author daxiong
     * @date 2019-11-11 15:24
     * @return void
     */
    @Test
    public void createToken() {
        // 信用卡账单地址
        BillingAddress billingAddress = new BillingAddress();
        // 国家
        billingAddress.setCountry("BRA");
        // 州
        billingAddress.setState("Santa Catarina (SC)");
        // 城市
        billingAddress.setCity("Joinville");
        // 街道
        billingAddress.setLine1("Rua Dona Francisca, 8300 -");

        TokenRequestEntity tokenRequestEntity = new TokenRequestEntity();
        /**
         * token类型：（必须）
         *  credit_card：信用卡，
         *  card_cvv_code：cvv码所属的token，
         *  billing_agreement：账单协议
         */
        tokenRequestEntity.setToken_type("credit_card");
        // 信用卡的cvv码
        tokenRequestEntity.setCredit_card_cvv("***");
        // 信用卡号（必须）
        tokenRequestEntity.setCard_number("*************");
        // 信用卡有效期
        tokenRequestEntity.setExpiration_date("02/26");
        // 持有人姓名（必须）
        tokenRequestEntity.setHolder_name("felipe p de souza");
        // 信用卡账单地址
        tokenRequestEntity.setBilling_address(billingAddress);
        String jsonString = JSON.toJSONString(tokenRequestEntity);

        PackageResponseEntity packageResponseEntity = HttpClientUtil.doTokenPost(CREATE_TOKEN_URL, jsonString);

        String responseStr = packageResponseEntity.getResponse();
        if (checkResponse(packageResponseEntity)) {
            TokenResponseEntity paymentResponseEntity = JSON.parseObject(responseStr, TokenResponseEntity.class);
            TOKEN = paymentResponseEntity.getToken();
        } else {
            throw new RuntimeException(responseStr);
        }
    }

    /**
     * 创建支付
     * @author daxiong
     * @date 2019-11-11 15:40
     * @return void
     */
    public void createPayment() {
        BillingAddress billingAddress = new BillingAddress();
        // 电话
//        billingAddress.setPhone("+1-541-754-3010");
        Order order = new Order();
        // 订单的id，自己生成
        order.setId("myorderid");

        PaymentRequestEntity paymentRequestEntity = new PaymentRequestEntity();
        // 交易金额（必须）
        paymentRequestEntity.setAmount(100D);
        // 币种（必须），巴西：BRL
        paymentRequestEntity.setCurrency("BRL");
        // 账单地址
        paymentRequestEntity.setBilling_address(billingAddress);
        // 交易描述
        paymentRequestEntity.setStatement_soft_descriptor("RunClub 5km race ticket");
        // 交易对应的订单
        paymentRequestEntity.setOrder(order);
        String jsonString = JSON.toJSONString(paymentRequestEntity);

        PackageResponseEntity packageResponseEntity = HttpClientUtil.doPost(CREATE_PAYMENT_URL, jsonString);
        String responseStr = packageResponseEntity.getResponse();
        if (checkResponse(packageResponseEntity)) {
            TokenResponseEntity paymentResponseEntity = JSON.parseObject(responseStr, TokenResponseEntity.class);
            String result = paymentResponseEntity.getId();
            // 设置生成授权的url
            CREATE_AUTHORIZATION_URL = String.format(CREATE_AUTHORIZATION_URL, result);
            // 设置生成捕获的url
            CREATE_CAPTURE_URL = String.format(CREATE_CAPTURE_URL, result);
        } else {
            throw new RuntimeException(responseStr);
        }

    }

    /**
     * 创建授权
     * @author daxiong
     * @date 2019-11-11 16:00
     * @return void
     */
    public void createAuthorization() {
        // 生成唯一标志码
        // 产生随机的referenceCode
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String reconciliationId = dateFormat.format(new Date());
        System.out.println("唯一标志：" + reconciliationId);

        // 支付方法
        PaymentMethod paymentMethod = new PaymentMethod();
        // 第一步生成的token
        paymentMethod.setToken(TOKEN);
        /**
         * 支付类型：
         *  tokenized (object)
         *  untokenized_alternative_payment (object)
         *  untokenized_credit_card (object) (payment_method_auth_charge)
         */
        paymentMethod.setType("tokenized");
        // 信用卡cvv码（必须）
        paymentMethod.setCredit_card_cvv("778");

        AuthorizationRequestEntity authorizationRequestEntity = new AuthorizationRequestEntity();
        // 授权关联的支付方法（必须）
        authorizationRequestEntity.setPayment_method(paymentMethod);

        // TODO NEW
        // 重用token信息
        ReuseCustomerCardInfo reuseCustomerCardInfo = new ReuseCustomerCardInfo();
        reuseCustomerCardInfo.setCard_entry_mode("cof_merchant_initiated_transaction");
        authorizationRequestEntity.setCof_transaction_indicators(reuseCustomerCardInfo);
        // TODO NEW

        // 生成的唯一码
        authorizationRequestEntity.setReconciliation_id(reconciliationId);
        String jsonString = JSON.toJSONString(authorizationRequestEntity);

        PackageResponseEntity packageResponseEntity = HttpClientUtil.doPost(CREATE_AUTHORIZATION_URL, jsonString);
        String responseStr = packageResponseEntity.getResponse();
        // TODO NEW
        if (checkResponse(packageResponseEntity)) {
            // 捕获这一步的返回有特殊情况，需要进一步判断status的值
            AuthorizationRequestEntity authorizationResponse = JSON.parseObject(responseStr, authorizationRequestEntity.getClass());
            AuthorizationResult result = authorizationResponse.getResult();
            String status = result.getStatus();
            if (!"Succeed".equals(status)) {
                ProviderData providerData = authorizationResponse.getProvider_data();
                String errorStr = providerData.getResponse_code();
                String description = providerData.getDescription();
                throw new RuntimeException("错误信息：" + errorStr + ", 捕获状态:" + description);
            }
            // TODO
        } else {
            throw new RuntimeException(responseStr);
        }
        // TODO NEW

    }

    /**
     * 创建捕获
     * @author daxiong
     * @date 2019-11-11 16:00
     * @return void
     */
    public void createCapture() {
        PackageResponseEntity packageResponseEntity = HttpClientUtil.doPost(CREATE_CAPTURE_URL, null);
        String responseStr = packageResponseEntity.getResponse();
        if (checkResponse(packageResponseEntity)) {
            // TODO
        } else {
            throw new RuntimeException(responseStr);
        }
    }

    @Test
    /**
     * 创建customer接口
     * @author daxiong
     * @date 2019-11-14 14:50
     * @return java.lang.String customer的id
     */
    public void createCustomer() {

        // 产生随机的referenceCode
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        String referenceCode = dateFormat.format(new Date());
        CustomerRequestEntity customerRequestEntity = new CustomerRequestEntity();
        // 用户唯一标志(必须)
        customerRequestEntity.setCustomer_reference(referenceCode);
        // 用户email地址
        customerRequestEntity.setEmail("john@travolta.com");
        String jsonString = JSON.toJSONString(customerRequestEntity);

        PackageResponseEntity packageResponseEntity = HttpClientUtil.doPost(CREATE_CUSTOEMR_URL, jsonString);

        String responseStr = packageResponseEntity.getResponse();
        if (checkResponse(packageResponseEntity)) {
            BaseResponseEntity baseResponseEntity = JSON.parseObject(responseStr, BaseResponseEntity.class);
            System.out.println("创建customer成功，id为：" + baseResponseEntity.getId());
            CUSTOMER_ID =  baseResponseEntity.getId();
        } else {
            throw new RuntimeException(responseStr);
        }

    }

    @Test
    /**
     * 将支付token与customer实体相关联
     * @author daxiong
     * @date 2019-11-14 14:50
     * @return
     */
    public void storeTokenToCustomer() {
//        String token = "9faba930-1bf9-4ac1-8f30-9e30f78f0cda";
//        String customerId = "f0d0e367-8b8c-44bf-bfb7-d7f2c69317d9";
        String url = String.format(STORE_TOKEN_TO_CUSTOMER_URL, CUSTOMER_ID, TOKEN);

        PackageResponseEntity packageResponseEntity = HttpClientUtil.doPost(url, null);

        String responseStr = packageResponseEntity.getResponse();
        if (checkResponse(packageResponseEntity)) {
            CustomerPaymentMethodResponseEntity e = JSON.parseObject(responseStr, CustomerPaymentMethodResponseEntity.class);
            System.out.println(
                    "卡号：" + e.getBin_number() +  "******" + e.getLast_4_digits() + "\n" +
                            "expiration：" + e.getExpiration_date() + "\n" +
                            "持有人：" + e.getHolder_name() + "\n" +
                            "token：" + e.getToken()  + "\n\n"
            );
        } else {
            throw new RuntimeException(responseStr);
        }

    }

    @Test
    /**
     * 获取某一customer的所有支付方法，遍历打印
     * @author daxiong
     * @date 2019-11-14 14:50
     * @return
     */
    public void RetrieveAllPaymentMethods() {
        String customerId = "f0d0e367-8b8c-44bf-bfb7-d7f2c69317d9";
        List<CustomerPaymentMethodResponseEntity> customerResponseEntities = getPaymentMethodsByCustomerId(customerId);
        if (customerResponseEntities != null && customerResponseEntities.size() > 0) {
            for (int i = 0; i < customerResponseEntities.size(); i++) {
                CustomerPaymentMethodResponseEntity e = customerResponseEntities.get(i);
                System.out.println(
                        "卡号：" + e.getBin_number() + "******" + e.getLast_4_digits() + "\n" +
                                "expiration：" + e.getExpiration_date() + "\n" +
                                "持有人：" + e.getHolder_name() + "\n" +
                                "token：" + e.getToken() + "\n"
                );
            }
        }
    }

    @Test
    /**
     * 检验卡号是否已经被授权添加
     * @author daxiong
     * @date 2019-11-14 14:50
     * @return
     */
    public void checkCardUnique() {
        String fingerPrint = "964849c3-8ab0-4c0b-85c7-4e15b8d9dd6c";
        String customerId = "b59d6e3a-b53b-49a9-ad33-60919fd6b237";
        List<CustomerPaymentMethodResponseEntity> customerResponseEntities = getPaymentMethodsByCustomerId(customerId);

        for (int i = 0; i < customerResponseEntities.size(); i++) {
            CustomerPaymentMethodResponseEntity e = customerResponseEntities.get(i);
            String str = e.getFingerprint();
            if (fingerPrint.equals(str)) {
                System.out.println("该卡号已经授权绑定");
                return;
            }
        }
        System.out.println("该卡号可以授权绑定");

    }

    /**
     * 通过客户id获取客户的支付方法
     * @author daxiong
     * @date 2019-11-21 17:35
     * @param customerId
     * @return java.util.List<com.leicx.payment.entity.customer.CustomerPaymentMethodResponseEntity>
     */
    public List<CustomerPaymentMethodResponseEntity> getPaymentMethodsByCustomerId(String customerId) {
        RETRIEVE_ALL_PAYMENT_METHODS_URL = String.format(RETRIEVE_ALL_PAYMENT_METHODS_URL, customerId);
        PackageResponseEntity packageResponseEntity = HttpClientUtil.doGet(RETRIEVE_ALL_PAYMENT_METHODS_URL);
        if (checkResponse(packageResponseEntity)) {
            // 成功
            List<CustomerPaymentMethodResponseEntity> customerResponseEntities = JSON.parseArray(packageResponseEntity.getResponse(), CustomerPaymentMethodResponseEntity.class);
            return customerResponseEntities;
        } else {
            throw new RuntimeException("请求错误");
        }
    }

    /**
     * 检查响应是否正确
     * @author daxiong
     * @date 2019-11-21 17:42
     * @param packageResponseEntity
     * @return boolean
     */
    public boolean checkResponse(PackageResponseEntity packageResponseEntity) {
        if (packageResponseEntity == null) {
            return false;
        }
        Integer code = packageResponseEntity.getCode();
        /**
         * 200 - OK
         * 201 - Created
         * 204 - 删除支付方法后返回的code值，官方文档上未做列举和说明，这里自己加上
         * 400 - Bad Request
         * 401 - Unauthorized   未授权
         * 404 - Not Found
         * 405 - Method Not Allowed 不允许的方法
         * 409 - Conflict   冲突
         * 500 - Server Error
         * 503 - Service Unavailable
         */
        if (code.equals(200) || code.equals(201) || code.equals(204)) {
            return true;
        }
        return false;
    }

    /**
     * 删除用户支付方法，删除该支付方法，则对应的token也被删除，只需删除本地数据即可
     * @author daxiong
     * @date 2019-12-12 09:29
     * @param customerId    用户id
     * @param token         卡的token
     * @return void
     */
    public void deletePaymentMethod(String customerId, String token) {
        String url = String.format(DELETE_PAYMENT_METHOD_URL, customerId, token);
        PackageResponseEntity packageResponseEntity = HttpClientUtil.doDelete(url);
        if (checkResponse(packageResponseEntity)) {
            // 成功
            System.out.println("删除成功");
        } else {
            throw new RuntimeException("请求错误");
        }
    }

    @Test
    public void testStoreTokenToCustomer() {
        createToken();
        createCustomer();
        storeTokenToCustomer();
    }

    //"token":"d04bdc81-9a9e-4cd5-868b-ef88bb075231"
    //"customerId":"6d842bfd-ea73-468b-82a0-0ae7dd322952"

    @Test
    public void testReuseCardInfo() {
        String customerId = "203fb426-dfa2-4a96-a513-3dd090249dd9";
        List<CustomerPaymentMethodResponseEntity> customerResponseEntities = getPaymentMethodsByCustomerId(customerId);

        CustomerPaymentMethodResponseEntity e = customerResponseEntities.get(0);
        String token = e.getToken();
        System.out.println(token);

        TOKEN = token;
        createPayment();
        createAuthorization();
    }
}
