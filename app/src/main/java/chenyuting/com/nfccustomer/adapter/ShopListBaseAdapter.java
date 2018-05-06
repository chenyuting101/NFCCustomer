package chenyuting.com.nfccustomer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import chenyuting.com.nfccustomer.models.Coupon;
import chenyuting.com.nfccustomer.models.Shop;
import yalantis.com.sidemenu.sample.R;

/**
 * Created by chenyuting on 11/11/17.
 */

public class ShopListBaseAdapter extends BaseAdapter {
    private Context con;
    private ArrayList<Shop> shopList;
    public ShopListBaseAdapter(Context con){
        this.con = con;
    }

    public ArrayList<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(ArrayList<Shop> shopList) {
        this.shopList = shopList;
    }

    @Override
    public int getCount() {
        return shopList.size();
    }

    @Override
    public Object getItem(int position) {
        return shopList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) convertView= LayoutInflater.from(con).inflate(R.layout.shop_list_item, null);
        TextView shopId = (TextView) convertView.findViewById(R.id.shop_list_item_shop_id);
        TextView shopName = (TextView) convertView.findViewById(R.id.shop_list_item_shop_name);
        TextView shopAddress = (TextView) convertView.findViewById(R.id.shop_list_item_shop_address);
        TextView shopPhoneNo = (TextView) convertView.findViewById(R.id.shop_list_item_shop_phone_no);
        Shop shop = (Shop) this.getItem(position);
        //Bitmap bitmap = this.getLoacalBitmap(video.getImageUri());
        shopId.setText(shop.getId());
        shopName.setText(shop.getName());
        shopAddress.setText(shop.getAddress());
        shopPhoneNo.setText(shop.getPhoneNumber());
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

