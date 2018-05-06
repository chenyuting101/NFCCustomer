package chenyuting.com.nfccustomer.models;

/**
 * Created by chenyuting on 11/12/17.
 */

public class UseCouponRecord {
    private String advertisementId;
    private String shopName;
    private String couponId;
    private String useNum;
    private String exchangeProductId;
    private String dateTime;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getExchangeProductId() {
        return exchangeProductId;
    }

    public void setExchangeProductId(String exchangeProductId) {
        this.exchangeProductId = exchangeProductId;
    }

    public String getUseNum() {
        return useNum;
    }

    public void setUseNum(String useNum) {
        this.useNum = useNum;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(String advertisementId) {
        this.advertisementId = advertisementId;
    }
}
