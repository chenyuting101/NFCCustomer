package chenyuting.com.nfccustomer.models;

/**
 * Created by chenyuting on 11/11/17.
 */

public class Rule {
    private String forType;
    private String ruleType;
    private String ruleID;
    private String couponID;
    private String validDateFrom;
    private String validDateTo;
    private String number;
    private String totalCost;//用分号分开
    private String exchangeProudctID;

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getForType() {
        return forType;
    }

    public void setForType(String forType) {
        this.forType = forType;
    }

    public String getCouponID() {
        return couponID;
    }

    public void setCouponID(String couponID) {
        this.couponID = couponID;
    }

    public String getRuleID() {
        return ruleID;
    }

    public void setRuleID(String ruleID) {
        this.ruleID = ruleID;
    }

    public String getValidDateFrom() {
        return validDateFrom;
    }

    public void setValidDateFrom(String validDateFrom) {
        this.validDateFrom = validDateFrom;
    }

    public String getValidDateTo() {
        return validDateTo;
    }

    public void setValidDateTo(String validDateTo) {
        this.validDateTo = validDateTo;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExchangeProudctID() {
        return exchangeProudctID;
    }

    public void setExchangeProudctID(String exchangeProudctID) {
        this.exchangeProudctID = exchangeProudctID;
    }
}
