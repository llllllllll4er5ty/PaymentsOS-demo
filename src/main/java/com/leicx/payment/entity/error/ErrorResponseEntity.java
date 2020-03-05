package com.leicx.payment.entity.error;

/**
 * 请求错误返回的response实体
 *
 * @author daxiong
 * @date 2019-11-21 15:56
 */
public class ErrorResponseEntity {
    private String more_info;
    private String category;
    private String description;

    public String getMore_info() {
        return more_info;
    }

    public void setMore_info(String more_info) {
        this.more_info = more_info;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
