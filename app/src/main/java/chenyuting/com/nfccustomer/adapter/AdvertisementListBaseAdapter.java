package chenyuting.com.nfccustomer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import chenyuting.com.nfccustomer.fragment.AdvertisementFragment;
import chenyuting.com.nfccustomer.models.Coupon;
import chenyuting.com.nfccustomer.models.Shop;
import chenyuting.com.nfccustomer.tool.DataBaseTool;
import chenyuting.com.nfccustomer.tool.Parameter;
import yalantis.com.sidemenu.sample.R;

/**
 * Created by chenyuting on 11/11/17.
 */

public class AdvertisementListBaseAdapter extends BaseAdapter {
    private Context con;
    private JSONArray advertiseList;
    private String TAG = "AdvertisementListBaseAdapter";
    public AdvertisementListBaseAdapter(Context con){
        this.con = con;
    }

    public JSONArray getAdvertiseList() {
        return advertiseList;
    }

    public void setAdvertiseList(JSONArray advertiseList) {
        this.advertiseList = advertiseList;
    }

    @Override
    public int getCount() {
        return advertiseList.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return advertiseList.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) convertView= LayoutInflater.from(con).inflate(R.layout.advertisement_list_item, null);
        TextView advertisementId = (TextView) convertView.findViewById(R.id.advertisement_list_item_advertisement_id_tv);
        TextView shopID = (TextView) convertView.findViewById(R.id.advertisement_list_item_shop_id_tv);
        TextView shopName = (TextView) convertView.findViewById(R.id.advertisement_list_item_shopname_tv);
        ImageView useStar = (ImageView) convertView.findViewById(R.id.advertisement_list_item_use_star);
        //Coupon coupon = (Coupon) this.getItem(position);
        try {
            DataBaseTool dataBaseTool = new DataBaseTool();

            JSONObject advertisement = advertiseList.getJSONObject(position);
            Shop shop = dataBaseTool.getShopByShopId(this.con,advertisement.getString(Parameter.K_SHOP_ID));
            advertisementId.setText(advertisement.getString(Parameter.K_ADVERTISEMENT_ID));
            shopID.setText(shop.getId());
            shopName.setText(shop.getName());

            //set use star
            DataBaseTool dataBaseTool1 = new DataBaseTool();
            int couponNum = dataBaseTool.isCouponExist(con, advertisement.getString(Parameter.K_COUPON_ID));
            int requireNum = advertisement.getInt(Parameter.K_RULE_NUMBER);
            Log.w(TAG, "COUPON NUM" +couponNum +"requirenun"+requireNum);
            if(couponNum > requireNum) useStar.setImageResource(R.drawable.can_use_star);
            else useStar.setImageResource(R.drawable.can_not_use_star);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Bitmap bitmap = this.getLoacalBitmap(video.getImageUri());
        return convertView;
    }
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}

