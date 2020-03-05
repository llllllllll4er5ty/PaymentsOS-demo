package com.leicx.payment.entity;

/**
 * 创建授权实体
 *
 * @author daxiong
 * @date 2019-11-11 15:51
 */
public class AuthorizationRequestEntity {
    // -------------请求和响应都有的字段--------------
    private PaymentMethod payment_method;
    private String reconciliation_id;

    // -------------请求独有的字段--------------
    // 重用token所需的字段
    private ReuseCustomerCardInfo cof_transaction_indicators;

    // -------------响应独有的字段--------------
    private String id;
    private AuthorizationResult result;
    private Double amount;
    private ProviderData provider_data;

    public PaymentMethod getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(PaymentMethod payment_method) {
        this.payment_method = payment_method;
    }

    public String getReconciliation_id() {
        return reconciliation_id;
    }

    public void setReconciliation_id(String reconciliation_id) {
        this.reconciliation_id = reconciliation_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AuthorizationResult getResult() {
        return result;
    }

    public void setResult(AuthorizationResult result) {
        this.result = result;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ProviderData getProvider_data() {
        return provider_data;
    }

    public void setProvider_data(ProviderData provider_data) {
        this.provider_data = provider_data;
    }

    public ReuseCustomerCardInfo getCof_transaction_indicators() {
        return cof_transaction_indicators;
    }

    public void setCof_transaction_indicators(ReuseCustomerCardInfo cof_transaction_indicators) {
        this.cof_transaction_indicators = cof_transaction_indicators;
    }
}
