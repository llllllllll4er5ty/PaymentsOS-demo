package com.leicx.payment.entity;

/**
 * 捕获响应的状态结果
 *   "result": {
 *         "status": "Failed",
 *         "category": "payment_method_error",
 *         "description": "The payment method provided is not valid."
 *     },
 * @author daxiong
 * @date 2019-12-12 18:16
 */
public class AuthorizationResult {
    // 状态
    private String status;
    // 分类
    private String category;
    // 描述
    private String description;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
