package com.leicx.payment.entity;

/**
 * 支付实体
 *
 * @author daxiong
 * @date 2019-11-11 15:33
 */
public class PaymentRequestEntity {
    private Double amount;
    private String currency;
    private BillingAddress billing_address;
    private Order order;
    // 支付商品描述
    private String statement_soft_descriptor;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BillingAddress getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(BillingAddress billing_address) {
        this.billing_address = billing_address;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getStatement_soft_descriptor() {
        return statement_soft_descriptor;
    }

    public void setStatement_soft_descriptor(String statement_soft_descriptor) {
        this.statement_soft_descriptor = statement_soft_descriptor;
    }
}
