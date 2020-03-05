package com.leicx.payment.entity.customer;

/**
 * @author daxiong
 * @date 2019-11-14 15:01
 */
public class CustomerPaymentMethodResponseEntity {
    private String type;
    private String token;
    private String token_type;
    private String state;
    private String holder_name;
    private String expiration_date;
    private String last_4_digits;
    private String vendor;
    private String created;
    private String bin_number;
    // 卡号唯一验证
    private String fingerprint;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHolder_name() {
        return holder_name;
    }

    public void setHolder_name(String holder_name) {
        this.holder_name = holder_name;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getLast_4_digits() {
        return last_4_digits;
    }

    public void setLast_4_digits(String last_4_digits) {
        this.last_4_digits = last_4_digits;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getBin_number() {
        return bin_number;
    }

    public void setBin_number(String bin_number) {
        this.bin_number = bin_number;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }
}
