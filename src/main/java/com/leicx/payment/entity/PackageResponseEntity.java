package com.leicx.payment.entity;

/**
 * 同一接收响应返回的实体，httpclient封装使用
 *
 * @author daxiong
 * @date 2019-11-21 16:10
 */
public class PackageResponseEntity {
    private Integer code;
    private String response;

    public PackageResponseEntity(Integer code, String response) {
        this.code = code;
        this.response = response;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
