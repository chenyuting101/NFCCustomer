package chenyuting.com.nfccustomer.models;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by chenyuting on 11/1/17.
 */

public class Receipt {
    private Bitmap qrcode;
    private Shop shop;
    private ArrayList<Item> itemList;
    private String receiptDescription;
    private String paymentDescription;
    private String couponDescription;
    private String receiptNumber;
    private String receiptDate;
    private String receiptTime;
    private String receiptQRcode;
    private String isUploaded;
    private String isBarcodeCache;
    private String barcodeUri;
    private String barcodeCacheUri;



    public Bitmap getQrcode() {
        return qrcode;
    }

    public void setQrcode(Bitmap qrcode) {
        this.qrcode = qrcode;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getReceiptQRcode() {
        return receiptQRcode;
    }

    public void setReceiptQRcode(String receiptQRcode) {
        this.receiptQRcode = receiptQRcode;
    }

    public String getReceiptTime() {
        return receiptTime;
    }

    public void setReceiptTime(String receiptTime) {
        this.receiptTime = receiptTime;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    public String getReceiptDescription() {
        return receiptDescription;
    }

    public void setReceiptDescription(String receiptDescription) {
        this.receiptDescription = receiptDescription;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public String getCouponDescription() {
        return couponDescription;
    }

    public void setCouponDescription(String couponDescription) {
        this.couponDescription = couponDescription;
    }

    public String getIsUploaded() {
        return isUploaded;
    }

    public void setIsUploaded(String isUploaded) {
        this.isUploaded = isUploaded;
    }

    public String getIsBarcodeCache() {
        return isBarcodeCache;
    }

    public void setIsBarcodeCache(String isBarcodeCache) {
        this.isBarcodeCache = isBarcodeCache;
    }

    public String getBarcodeUri() {
        return barcodeUri;
    }

    public void setBarcodeUri(String barcodeUri) {
        this.barcodeUri = barcodeUri;
    }

    public String getBarcodeCacheUri() {
        return barcodeCacheUri;
    }

    public void setBarcodeCacheUri(String barcodeCacheUri) {
        this.barcodeCacheUri = barcodeCacheUri;
    }
}
