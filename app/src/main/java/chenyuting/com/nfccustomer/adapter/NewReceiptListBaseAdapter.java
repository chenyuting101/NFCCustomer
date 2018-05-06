package chenyuting.com.nfccustomer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import chenyuting.com.nfccustomer.models.Shop;
import chenyuting.com.nfccustomer.tool.DataBaseTool;
import chenyuting.com.nfccustomer.tool.Parameter;
import yalantis.com.sidemenu.sample.R;

/**
 * Created by chenyuting on 11/30/17.
 */

public class NewReceiptListBaseAdapter extends BaseAdapter {
    private Context con;
    private ArrayList<JSONObject> receiptList;
    public NewReceiptListBaseAdapter(Context con){
        this.con = con;
    }

    public ArrayList<JSONObject> getReceiptList() {
        return receiptList;
    }

    public void setReceiptList(ArrayList<JSONObject> receiptList) {
        this.receiptList = receiptList;
    }

    @Override
    public int getCount() {
        return receiptList.size();
    }

    @Override
    public Object getItem(int position) {

            return receiptList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) convertView= LayoutInflater.from(con).inflate(R.layout.new_receipt_list_item, null);
        TextView receiptId = (TextView) convertView.findViewById(R.id.new_receipt_list_item_receipt_id_tv);
        TextView receiptTime = (TextView) convertView.findViewById(R.id.new_receipt_list_item_receipt_time_tv);
        TextView receiptTotalcost = (TextView) convertView.findViewById(R.id.new_receipt_list_item_receipt_totalcost_tv);
        //Coupon coupon = (Coupon) this.getItem(position);
        try {
            DataBaseTool dataBaseTool = new DataBaseTool();

            JSONObject object = receiptList.get(position);
            //Shop shop = dataBaseTool.getShopByShopId(this.con,advertisement.getString(Parameter.K_SHOP_ID));
            receiptTime.setText(object.getString(Parameter.K_RECEIPT_DATE)+" "+object.getString(Parameter.K_RECEIPT_TIME));
            receiptTotalcost.setText(object.getString(Parameter.K_TOTAL_COST));
            receiptId.setText(object.getString(Parameter.K_RECEIPT_ID));
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

