package com.leicx.payment.entity;

/**
 * @author daxiong
 * @date 2019-11-11 15:45
 */
public class TokenResponseEntity extends BaseResponseEntity {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenResponseEntity{" +
                "token='" + token + '\'' +
                '}';
    }
}
