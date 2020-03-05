package com.leicx.payment.entity;

/**
 * 发送token请求的实体
 *
 * @author daxiong
 * @date 2019-11-11 14:59
 */
public class TokenRequestEntity {
    private String token_type;
    private String credit_card_cvv;
    private String card_number;
    private String expiration_date;
    private String holder_name;
    private BillingAddress billing_address;

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getCredit_card_cvv() {
        return credit_card_cvv;
    }

    public void setCredit_card_cvv(String credit_card_cvv) {
        this.credit_card_cvv = credit_card_cvv;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getHolder_name() {
        return holder_name;
    }

    public void setHolder_name(String holder_name) {
        this.holder_name = holder_name;
    }

    public BillingAddress getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(BillingAddress billing_address) {
        this.billing_address = billing_address;
    }
}
