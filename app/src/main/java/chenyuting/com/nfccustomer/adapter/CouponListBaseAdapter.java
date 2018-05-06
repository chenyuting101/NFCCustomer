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
import yalantis.com.sidemenu.sample.R;

/**
 * Created by chenyuting on 11/11/17.
 */

public class CouponListBaseAdapter extends BaseAdapter {
    private Context con;
    private ArrayList<Coupon> couponList;
    public CouponListBaseAdapter(Context con){
        this.con = con;
    }

    public ArrayList<Coupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(ArrayList<Coupon> couponList) {
        this.couponList = couponList;
    }

    @Override
    public int getCount() {
        return couponList.size();
    }

    @Override
    public Object getItem(int position) {
        return couponList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) convertView= LayoutInflater.from(con).inflate(R.layout.coupon_list_item, null);
        TextView ruleId = (TextView) convertView.findViewById(R.id.coupon_list_item_coupon_id_tv);
        TextView ruleType = (TextView) convertView.findViewById(R.id.coupon_list_item_coupon_description_tv);
        TextView couponNumber = (TextView) convertView.findViewById(R.id.coupon_list_item_coupon_number_tv);
        Coupon coupon = (Coupon) this.getItem(position);
        //Bitmap bitmap = this.getLoacalBitmap(video.getImageUri());
        ruleId.setText(coupon.getId());
        ruleType.setText(coupon.getDescription());
        couponNumber.setText(""+coupon.getNum());
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

