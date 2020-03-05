package com.leicx.payment.entity;

/**
 * 包含有关使用客户存储的卡片信息的实体
 * Contains indicators pertaining to the use of a customer's stored card information
 * @author daxiong
 * @date 2019-12-16 09:25
 */
public class ReuseCustomerCardInfo {
    /**
     * 可选值:
     *  consent_transaction:
     *      首次交易，在此交易中，客户同意将存储的卡片信息用于随后的客户发起的交易，或随后由商人发起的计划外的交易
     *  recurring_consent_transaction:
     *      最初的交易，在此交易中，客户同意为随后的预定(重复)交易使用存储的卡信息
     *  recurring_subsequent_transaction:
     *      使用已存储的卡片信息并按固定的时间间隔进行处理的一系列事务中的一个事务
     *  cof_cardholder_initiated_transaction:
     *      用于客户发起的卡片-文件交易
     *  cof_merchant_initiated_transaction:
     *      用于由您(作为商家)发起的计划外的卡片-文件交易
     *
     *  注意:
     *      如果事务是循环事务，并且每次付款的金额相同，
     *      那么建议将该事务标记为循环事务(在这种情况下，应该传递一个recurring_subsequent_transaction值)。
     *      如果每个循环付款的金额不同，
     *      那么建议将该事务标记为“商家发起的”事务(在这种情况下，您应该传递一个cof_merchant_initiated_transaction值)
     */
    private String card_entry_mode;

    /**
     * 标识客户同意使用存储的付款凭证来处理付款的初始请求的ID。处理事务的提供者可能返回此ID(并非所有提供者都返回此ID)。
     * 如果返回，则在所有后续事务请求中传递它。
     */
    private String cof_consent_transaction_id;

    public String getCard_entry_mode() {
        return card_entry_mode;
    }

    public void setCard_entry_mode(String card_entry_mode) {
        this.card_entry_mode = card_entry_mode;
    }

    public String getCof_consent_transaction_id() {
        return cof_consent_transaction_id;
    }

    public void setCof_consent_transaction_id(String cof_consent_transaction_id) {
        this.cof_consent_transaction_id = cof_consent_transaction_id;
    }
}
