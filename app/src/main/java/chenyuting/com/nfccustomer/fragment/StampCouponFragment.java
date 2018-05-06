package chenyuting.com.nfccustomer.fragment;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Calendar;

import chenyuting.com.nfccustomer.adapter.CouponListBaseAdapter;
import chenyuting.com.nfccustomer.adapter.RuleListBaseAdapter;
import chenyuting.com.nfccustomer.adapter.ShopListBaseAdapter;
import chenyuting.com.nfccustomer.adapter.StampCouponListBaseAdapter;
import chenyuting.com.nfccustomer.models.Coupon;
import chenyuting.com.nfccustomer.models.Rule;
import chenyuting.com.nfccustomer.models.Shop;
import chenyuting.com.nfccustomer.tool.AccountStorage;
import chenyuting.com.nfccustomer.tool.DataBaseTool;
import chenyuting.com.nfccustomer.tool.DataTransferTool;
import chenyuting.com.nfccustomer.tool.Parameter;
import chenyuting.com.nfccustomer.tool.SharedPreferenceTool;
import yalantis.com.sidemenu.sample.R;

/**
 * Created by chenyuting on 11/10/17.
 */

public class StampCouponFragment extends Fragment {


    private ListView shopLV,couponLV;
    private ArrayList<Shop> shopList;
    private ArrayList<Coupon> couponList;
    private LinearLayout couponLL;
    private ImageView backIV;
    private DataBaseTool dataBaseTool;
    private ShopListBaseAdapter shopListBaseAdapter;
    private StampCouponListBaseAdapter stampCouponListBaseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_coupon_stamp, container, false);
        shopLV = (ListView)rootView.findViewById(R.id.fragment_coupon_stamp_shop_lv);
        couponLV = (ListView)rootView.findViewById(R.id.fragment_coupon_stamp_coupon_lv);
        couponLL = (LinearLayout) rootView.findViewById(R.id.fragment_coupon_stamp_coupon_ll);
        backIV = (ImageView) rootView.findViewById(R.id.fragment_coupon_stamp_coupon_back_iv);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couponLL.setVisibility(View.GONE);
                shopLV.setVisibility(View.VISIBLE);
            }
        });

        dataBaseTool = new DataBaseTool();
        shopList = dataBaseTool.getShopsByUserAccount(getActivity(),AccountStorage.GetAccount(this.getActivity()));
        shopListBaseAdapter = new ShopListBaseAdapter(this.getActivity());
        shopListBaseAdapter.setShopList(shopList);
        shopLV.setAdapter(shopListBaseAdapter);
        shopLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                couponList = dataBaseTool.getCouponByShopId(getActivity(),shopList.get(position).getId());
                stampCouponListBaseAdapter = new StampCouponListBaseAdapter(getActivity());
                stampCouponListBaseAdapter.setCouponList(couponList);
                couponLV.setAdapter(stampCouponListBaseAdapter);
                couponLL.setVisibility(View.VISIBLE);
                shopLV.setVisibility(View.GONE);

            }
        });
        return rootView;
    }

}
