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

import com.squareup.picasso.Picasso;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import chenyuting.com.nfccustomer.models.Coupon;
import chenyuting.com.nfccustomer.models.Shop;
import chenyuting.com.nfccustomer.tool.Parameter;
import yalantis.com.sidemenu.sample.R;

/**
 * Created by chenyuting on 11/11/17.
 */

public class StampCouponListBaseAdapter extends BaseAdapter {
    private Context con;
    private ArrayList<Coupon> couponList;
    private String TAG ="StampCouponListBaseAdapter";
    public StampCouponListBaseAdapter(Context con){
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
        if(convertView == null) convertView= LayoutInflater.from(con).inflate(R.layout.stam_coupon_list_item, null);
        TextView couponId = (TextView) convertView.findViewById(R.id.stamp_coupon_item_coupon_id);
        TextView couponNumber = (TextView) convertView.findViewById(R.id.stamp_coupon_item_coupon_number);
        TextView couponDescription = (TextView) convertView.findViewById(R.id.stamp_coupon_item_coupon_description);
        ImageView couponIV = (ImageView) convertView.findViewById(R.id.stamp_coupon_list_item_iv);
        Coupon coupon = (Coupon) this.getItem(position);
        //Bitmap bitmap = this.getLoacalBitmap(video.getImageUri());
        couponId.setText(coupon.getId());
        couponNumber.setText(""+coupon.getNum());
        couponDescription.setText(coupon.getDescription());
        System.out.println(TAG + coupon.getServerPictureURL());
        Log.w(TAG, coupon.getServerPictureURL());
        Picasso.with(con).load(coupon.getServerPictureURL()).placeholder(R.drawable.back).into(couponIV);//coupon.getServerPictureURL()


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

