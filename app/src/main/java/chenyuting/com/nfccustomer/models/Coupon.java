package chenyuting.com.nfccustomer.models;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by chenyuting on 11/8/17.
 */

public class Coupon {
    private String shopId;
    private String id;
    private String description;
    private String type;
    private Uri localPictureURL;
    private String serverPictureURL;
    private ArrayList<Rule> rulesList;
    private int num;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Uri getLocalPictureURL() {
        return localPictureURL;
    }

    public void setLocalPictureURL(Uri localPictureURL) {
        this.localPictureURL = localPictureURL;
    }

    public ArrayList<Rule> getRulesList() {
        return rulesList;
    }

    public void setRulesList(ArrayList<Rule> rulesList) {
        this.rulesList = rulesList;
    }

    public String getServerPictureURL() {
        return serverPictureURL;
    }

    public void setServerPictureURL(String serverPictureURL) {
        this.serverPictureURL = serverPictureURL;
    }
}
