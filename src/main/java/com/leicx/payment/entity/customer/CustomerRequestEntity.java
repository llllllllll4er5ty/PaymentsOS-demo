package com.leicx.payment.entity.customer;

/**
 * 用户请求实体
 *
 * @author daxiong
 * @date 2019-11-14 14:40
 */
public class CustomerRequestEntity {
    private String customer_reference;
    private String email;

    public String getCustomer_reference() {
        return customer_reference;
    }

    public void setCustomer_reference(String customer_reference) {
        this.customer_reference = customer_reference;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
