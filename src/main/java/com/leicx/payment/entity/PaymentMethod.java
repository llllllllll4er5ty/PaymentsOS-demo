package com.leicx.payment.entity;

/**
 * 支付方式实体
 *
 * @author daxiong
 * @date 2019-11-11 15:52
 */
public class PaymentMethod {
    private String token;
    private String type;
    private String credit_card_cvv;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCredit_card_cvv() {
        return credit_card_cvv;
    }

    public void setCredit_card_cvv(String credit_card_cvv) {
        this.credit_card_cvv = credit_card_cvv;
    }
}
