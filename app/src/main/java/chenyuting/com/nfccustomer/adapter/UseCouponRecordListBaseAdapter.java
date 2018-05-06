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

import chenyuting.com.nfccustomer.models.UseCouponRecord;
import yalantis.com.sidemenu.sample.R;

/**
 * Created by chenyuting on 11/11/17.
 */

public class UseCouponRecordListBaseAdapter extends BaseAdapter {
    private Context con;
    private ArrayList<UseCouponRecord> useCouponRecords;
    public UseCouponRecordListBaseAdapter(Context con){
        this.con = con;
    }

    public ArrayList<UseCouponRecord> getUseCouponRecords() {
        return useCouponRecords;
    }

    public void setUseCouponRecords(ArrayList<UseCouponRecord> useCouponRecords) {
        this.useCouponRecords = useCouponRecords;
    }

    @Override
    public int getCount() {
        return useCouponRecords.size();
    }

    @Override
    public Object getItem(int position) {
        return useCouponRecords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) convertView= LayoutInflater.from(con).inflate(R.layout.use_coupon_record_list_item, null);
        TextView advertisementId = (TextView) convertView.findViewById(R.id.use_coupon_record_list_item_advertisement_id_tv);
        TextView shopId = (TextView) convertView.findViewById(R.id.use_coupon_record_list_item_shop_name_tv);
        TextView useRule = (TextView) convertView.findViewById(R.id.use_coupon_record_use_rule_tv);
        TextView exchangeProductId = (TextView) convertView.findViewById(R.id.use_coupon_record_exchange_product_id_tv);
        TextView dateTime = (TextView) convertView.findViewById(R.id.use_coupon_record_exchange_date_tv);

        UseCouponRecord useCouponRecord = (UseCouponRecord) this.getItem(position);
        //Bitmap bitmap = this.getLoacalBitmap(video.getImageUri());
        advertisementId.setText(useCouponRecord.getAdvertisementId());
        shopId.setText(useCouponRecord.getShopName());
        useRule.setText(useCouponRecord.getUseNum()+" "+useCouponRecord.getCouponId());
        exchangeProductId.setText(useCouponRecord.getExchangeProductId());
        dateTime.setText(useCouponRecord.getDateTime());
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

