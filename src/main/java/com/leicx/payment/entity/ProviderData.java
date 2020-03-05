package com.leicx.payment.entity;

/**
 * 与paymentos集成的provider返回的信息
 * @author daxiong
 * @date 2019-12-12 18:13
 */
public class ProviderData {
    private String provider_name;
    private String response_code;
    private String description;
    private String transaction_id;

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }
}
