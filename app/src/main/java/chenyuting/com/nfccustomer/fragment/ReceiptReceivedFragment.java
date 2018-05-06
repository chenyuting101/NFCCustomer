package chenyuting.com.nfccustomer.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import chenyuting.com.nfccustomer.models.Coupon;
import chenyuting.com.nfccustomer.models.Item;
import chenyuting.com.nfccustomer.tool.DataBaseTool;
import chenyuting.com.nfccustomer.tool.DataTransferTool;
import chenyuting.com.nfccustomer.tool.Parameter;
import chenyuting.com.nfccustomer.tool.SharedPreferenceTool;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.sample.R;

public class ReceiptReceivedFragment extends Fragment implements ScreenShotable {
    private String TAG = "ReceiptReceivedFragment";
    private View view;
    private View containerView;
    private Bitmap bitmap;
    private TextView textView, couponTv,totalCostTV;
    private ImageView imageView1, imageView2, qrcodeIV;
    private TextView shopNameTV, shopAddressTV, phoneNumberTV, receiptDateTv,receiptTimeTV, receiptNumberTv
            ,descriptorTv;
    private LinearLayout itemListLL;
    private SharedPreferenceTool sharedPreferenceTool;
    private Context context;
    public ReceiptReceivedFragment(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.w(TAG, "onViewCreated");
        this.containerView = view.findViewById(R.id.container);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.w(TAG, "onCreatedView");
        View view = inflater.inflate(R.layout.fragment_receipt, container, false);
        sharedPreferenceTool = new SharedPreferenceTool();
        shopNameTV = (TextView) view.findViewById(R.id.receipt_tv_shopname);
        shopAddressTV = (TextView) view.findViewById(R.id.receipt_tv_shop_address);
        phoneNumberTV = (TextView) view.findViewById(R.id.receipt_tv_shop_phonenumber);
        receiptDateTv = (TextView) view.findViewById(R.id.receipt_tv_date);
        receiptTimeTV = (TextView) view.findViewById(R.id.receipt_tv_time);
        couponTv = (TextView) view.findViewById(R.id.fragment_receipt_coupon_tv);
        totalCostTV = (TextView) view.findViewById(R.id.fragment_receipt_total_cost_tv);

        receiptNumberTv = (TextView) view.findViewById(R.id.receipt_tv_number);
        descriptorTv = (TextView) view.findViewById(R.id.receipt_tv_descriptor);
        qrcodeIV = (ImageView) view.findViewById(R.id.receipt_im_qrcode);
        itemListLL = (LinearLayout) view.findViewById(R.id.receipt_ll_item_list);
        receivedNewReceipt();
        return view;
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                ReceiptReceivedFragment.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void receivedNewReceipt(){
        Log.w(TAG, "receivedNewReceipt");
        //SharedPreferenceTool sharedPreferenceTool = new SharedPreferenceTool();
        String receiptData = sharedPreferenceTool.read(Parameter.K_RECEIPT_DATA,this.context);
        if(receiptData.equals(Parameter.NULL_RECEIPT)) return;
        //String pictureDataStr = sharedPreferenceTool.read(Parameter.RECEIPT_PICTURE, this.context);
        //just for test---------------
//        Bitmap pictureData = DataTransferTool.Bytes2Bitmap(pictureDataStr.getBytes());
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.qrcode);
//        Bitmap newBitmap = DataTransferTool.Bytes2Bitmap( (DataTransferTool.HexStringToByteArray(
//                DataTransferTool.ByteArrayToHexString(DataTransferTool.Bitmap2Bytes(bitmap))).toString()).getBytes());

        //Log.w(TAG, "bitmap to string "+DataTransferTool.Bitmap2Bytes(newBitmap).toString());
        try {

            JSONObject object = new JSONObject(receiptData);
            String shopId = object.getString(Parameter.K_SHOP_ID);
            shopNameTV.setText(object.getString(Parameter.K_SHOPE_NAME));
            shopAddressTV.setText(object.getString(Parameter.K_SHOPE_ADDRESS));
            phoneNumberTV.setText(object.getString(Parameter.K_SHOPE_PHONE_NUMBER));
            receiptDateTv.setText(object.getString(Parameter.K_RECEIPT_DATE));
            receiptTimeTV.setText(object.getString(Parameter.K_RECEIPT_TIME));
            receiptNumberTv.setText(object.getString(Parameter.K_RECEIPT_ID));
            ArrayList<Item>  itemList = new ArrayList<Item>();
            JSONArray items = object.getJSONArray(Parameter.K_SALES_ITEM);
            for(int i=0;i<items.length();i++){
                Item item = new Item();
                item.setName(items.getJSONObject(i).getString(Parameter.K_ITEM_NAME));
                item.setId(items.getJSONObject(i).getString(Parameter.K_ITEM_ID));
                item.setQuantity(items.getJSONObject(i).getString(Parameter.K_ITEM_QUANTITY));
                item.setOriginalPrice(items.getJSONObject(i).getString(Parameter.K_ITEM_ORIGINAL_PRICE));
                item.setDiscount(items.getJSONObject(i).getString(Parameter.K_ITEM_DISCOUNT));
                item.setPrice(items.getJSONObject(i).getString(Parameter.K_ITEM_PRICE));
                itemList.add(item);
            }

            totalCostTV.setText(object.getString(Parameter.K_TOTAL_COST));

            ArrayList<Coupon>  couponList = new ArrayList<Coupon>();
            JSONArray coupons = object.getJSONArray(Parameter.K_COUPON);

            String text = "";
            //DataBaseTool dataBaseTool = new DataBaseTool();
            for(int i=0;i<coupons.length();i++){
                Coupon coupon = new Coupon();
                coupon.setShopId(shopId);
                JSONObject couponJson = coupons.getJSONObject(i);
                coupon.setNum(Integer.parseInt(couponJson.getString(Parameter.K_RULE_NUMBER)));
                coupon.setType(couponJson.getString(Parameter.K_COUPON_TYPE));
                coupon.setId(couponJson.getString(Parameter.K_COUPON_ID));
                coupon.setDescription(couponJson.getString(Parameter.K_DESCRIPTION));
                coupon.setServerPictureURL(couponJson.getString(Parameter.K_SERVER_PICTURE_URL));
                text += "You get "+coupon.getNum()+" "+coupon.getId()+"\n";
                couponTv.setText(text);
                couponList.add(coupon);
                //dataBaseTool.insertCoupon(this.getActivity(),coupon);
                //insert coupon
                //dataBaseTool.initiateDataBase(getActivity());
                //dataBaseTool.insertCoupon(getActivity(),coupon);

            }

            descriptorTv.setText(object.getString(Parameter.K_RECEIPT_DESCRIPTION));


            //Bitmap bitmap = DataTransferTool.convertStringToIcon(object.getString(Parameter.K_RECEIPT_QRCODE));
//            TextView itemNameTV = new TextView(this.context);
//            itemNameTV.setLayoutParams(new LinearLayout.LayoutParams(
//                    LayoutParams.MATCH_PARENT,
//                    LayoutParams.WRAP_CONTENT));
//            itemNameTV.setText("Milk");
//            itemNameTV.setTextColor(Color.BLACK);
//            itemNameTV.setTextSize(12);
//            itemListLL.addView(itemNameTV);

//            Button button = new Button(this.context);
//            button.setText("button");
//            itemListLL.addView(button);

            setItemList(itemList);
            //qrcodeIV.setImageBitmap(pictureData);
            //qrcodeIV.setImageResource(R.drawable.qrcode);
//            qrcodeIV.setImageBitmap(bitmap);
//            descriptorTv.setText("lsjflajflaksjflaksjfl;aksjfkajfdlkajf\nlsafjlasjfla;sjf\nslfjlajflaksjflas\n" +
//                    "alkfjlakjfla;ksjfl;ajfla;kjf\nalskjflakfjlakjflak fjflkajdslkfjalskfjla;skdjfk\naljfl;asfjlaksdjflaksj\n" +
//                    "aslkfdjalkfjalkjffwejflkdjsowofjwij\nasldfjwoijiewjfaoflkjjfi\nwoidjldfwoaijni dsljfeiajsliefjjlejkfelj\n"+"lsjflajflaksjflaksjfl;aksjfkajfdlkajf\nlsafjlasjfla;sjf\nslfjlajflaksjflas\n" +
//                    "alkfjlakjfla;ksjfl;ajfla;kjf\nalskjflakfjlakjflak fjflkajdslkfjalskfjla;skdjfk\naljfl;asfjlaksdjflaksj\n" +
//                    "aslkfdjalkfjalkjffwejflkdjsowofjwij\nasldfjwoijiewjfaoflkjjfi\nwoidjldfwoaijni dsljfeiajsliefjjlejkfelj\n"+"lsjflajflaksjflaksjfl;aksjfkajfdlkajf\nlsafjlasjfla;sjf\nslfjlajflaksjflas\n" +
//                    "alkfjlakjfla;ksjfl;ajfla;kjf\nalskjflakfjlakjflak fjflkajdslkfjalskfjla;skdjfk\naljfl;asfjlaksdjflaksj\n" +
//                    "aslkfdjalkfjalkjffwejflkdjsowofjwij\nasldfjwoijiewjfaoflkjjfi\nwoidjldfwoaijni dsljfeiajsliefjjlejkfelj\n"+"lsjflajflaksjflaksjfl;aksjfkajfdlkajf\nlsafjlasjfla;sjf\nslfjlajflaksjflas\n" +
//                    "alkfjlakjfla;ksjfl;ajfla;kjf\nalskjflakfjlakjflak fjflkajdslkfjalskfjla;skdjfk\naljfl;asfjlaksdjflaksj\n" +
//                    "aslkfdjalkfjalkjffwejflkdjsowofjwij\nasldfjwoijiewjfaoflkjjfi\nwoidjldfwoaijni dsljfeiajsliefjjlejkfelj\n"+"lsjflajflaksjflaksjfl;aksjfkajfdlkajf\nlsafjlasjfla;sjf\nslfjlajflaksjflas\n" +
//                    "alkfjlakjfla;ksjfl;ajfla;kjf\nalskjflakfjlakjflak fjflkajdslkfjalskfjla;skdjfk\naljfl;asfjlaksdjflaksj\n" +
//                    "aslkfdjalkfjalkjffwejflkdjsowofjwij\nasldfjwoijiewjfaoflkjjfi\nwoidjldfwoaijni dsljfeiajsliefjjlejkfelj\n"+"lsjflajflaksjflaksjfl;aksjfkajfdlkajf\nlsafjlasjfla;sjf\nslfjlajflaksjflas\n" +
//                    "alkfjlakjfla;ksjfl;ajfla;kjf\nalskjflakfjlakjflak fjflkajdslkfjalskfjla;skdjfk\naljfl;asfjlaksdjflaksj\n" +
//                    "aslkfdjalkfjalkjffwejflkdjsowofjwij\nasldfjwoijiewjfaoflkjjfi\nwoidjldfwoaijni dsljfeiajsliefjjlejkfelj\n"+"lsjflajflaksjflaksjfl;aksjfkajfdlkajf\nlsafjlasjfla;sjf\nslfjlajflaksjflas\n" +
//                    "alkfjlakjfla;ksjfl;ajfla;kjf\nalskjflakfjlakjflak fjflkajdslkfjalskfjla;skdjfk\naljfl;asfjlaksdjflaksj\n" +
//                    "aslkfdjalkfjalkjffwejflkdjsowofjwij\nasldfjwoijiewjfaoflkjjfi\nwoidjldfwoaijni dsljfeiajsliefjjlejkfelj\n"+"lsjflajflaksjflaksjfl;aksjfkajfdlkajf\nlsafjlasjfla;sjf\nslfjlajflaksjflas\n" +
//                    "alkfjlakjfla;ksjfl;ajfla;kjf\nalskjflakfjlakjflak fjflkajdslkfjalskfjla;skdjfk\naljfl;asfjlaksdjflaksj\n" +
//                    "aslkfdjalkfjalkjffwejflkdjsowofjwij\nasldfjwoijiewjfaoflkjjfi\nwoidjldfwoaijni dsljfeiajsliefjjlejkfelj\n");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void setItemList(ArrayList<Item> itemList){


        for(int i=0; i<itemList.size();i++) {
            TextView itemNameTV = new TextView(this.context);
//        ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
            //itemNameTV.setLayoutParams(vlp);
            Log.w(TAG, "add view here");
            itemNameTV.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            //itemNameTV.setText("Milk");
            itemNameTV.setText(itemList.get(i).getName());
            itemNameTV.setTextColor(Color.BLACK);
            itemNameTV.setTextSize(14);
            itemListLL.addView(itemNameTV);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            LinearLayout view = new LinearLayout(this.context);
            view.setLayoutParams(lp);//设置布局参数
            view.setOrientation(LinearLayout.HORIZONTAL);// 设置子View的Linearlayout// 为垂直方向布局
            // itemListLL.addView(view);
            //定义子View中两个元素的布局
//        ViewGroup.LayoutParams vlp = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        ViewGroup.LayoutParams vlp2 = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT,1.0f);


            TextView itemCode = new TextView(this.getActivity());
            itemCode.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT, 1));
            itemCode.setTextSize(R.dimen.receipt_tv_size_item);
            itemCode.setTextSize(14);
            //itemCode.setText("A101010");
            itemCode.setText(itemList.get(i).getId());
            itemCode.setTextColor(Color.BLACK);

            TextView itemQuantity = new TextView(this.getActivity());
            itemQuantity.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT, 1));
            itemQuantity.setTextSize(14);
            //itemQuantity.setText("1");
            itemQuantity.setText(itemList.get(i).getQuantity());
            itemQuantity.setTextColor(Color.BLACK);

            TextView itemOriginalPrice = new TextView(this.getActivity());
            itemOriginalPrice.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT, 1));
            itemOriginalPrice.setTextSize(14);
            //itemOriginalPrice.setText("100");
            itemOriginalPrice.setText(itemList.get(i).getOriginalPrice());
            itemOriginalPrice.setTextColor(Color.BLACK);

            TextView itemDiscount = new TextView(this.getActivity());
            itemDiscount.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT, 1));
            itemDiscount.setTextSize(14);
            //itemDiscount.setText("0");
            itemDiscount.setTextColor(Color.BLACK);

            TextView itemPrice = new TextView(this.getActivity());
            itemPrice.setLayoutParams(new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT, 1));
            itemPrice.setTextSize(14);
            //itemPrice.setText("100");
            itemPrice.setText(itemList.get(i).getPrice());
            itemPrice.setTextColor(Color.BLACK);

            view.addView(itemCode);//将TextView 添加到子View 中
            view.addView(itemQuantity);//将TextView 添加到子View 中
            view.addView(itemOriginalPrice);//将TextView 添加到子View 中
            view.addView(itemDiscount);//将TextView 添加到子View 中
            view.addView(itemPrice);//将TextView 添加到子View 中
            itemListLL.addView(view);
        }

    }
}
