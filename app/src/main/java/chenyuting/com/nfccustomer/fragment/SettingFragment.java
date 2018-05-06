package chenyuting.com.nfccustomer.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import chenyuting.com.nfccustomer.models.Coupon;
import chenyuting.com.nfccustomer.models.Item;
import chenyuting.com.nfccustomer.tool.DataBaseTool;
import chenyuting.com.nfccustomer.tool.Parameter;
import chenyuting.com.nfccustomer.tool.SharedPreferenceTool;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.sample.R;

/**
 * Created by chenyuting on 11/30/17.
 */

public class SettingFragment extends Fragment implements ScreenShotable {
    private String TAG = "SettingFragment";
    private View view;
    private View containerView;
    private Bitmap bitmap;
    private ImageView uploadDataIV, receiveAdIV, filterAdIV;
    private SharedPreferenceTool sharedPreferenceTool;
    private Context context;
    private String uploadData, receiveAd, filterAd;


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
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        sharedPreferenceTool = new SharedPreferenceTool();
        uploadDataIV = (ImageView) view.findViewById(R.id.fragment_setting_upload_data_to_server_iv);
        uploadData = SharedPreferenceTool.read(Parameter.K_SETTING_UPLOAD_DATA, this.getActivity());
        if(uploadData.equals(Parameter.V_TOGGLE_BUTTON_ON)) uploadDataIV.setImageResource(R.drawable.toggle_on);
        else uploadDataIV.setImageResource(R.drawable.toggle_off);

        receiveAdIV = (ImageView) view.findViewById(R.id.fragment_setting_receive_advertisement_iv);
        receiveAd = SharedPreferenceTool.read(Parameter.K_SETTING_RECEIVE_ADVERTISEMENT, this.getActivity());
        if(receiveAd.equals(Parameter.V_TOGGLE_BUTTON_ON)) receiveAdIV.setImageResource(R.drawable.toggle_on);
        else receiveAdIV.setImageResource(R.drawable.toggle_off);

        filterAdIV = (ImageView) view.findViewById(R.id.fragment_setting_filter_advertisement_iv);
        filterAd = SharedPreferenceTool.read(Parameter.K_SETTING_FILTER_ADVERTISEMENT, this.getActivity());
        if(filterAd.equals(Parameter.V_TOGGLE_BUTTON_ON)) filterAdIV.setImageResource(R.drawable.toggle_on);
        else filterAdIV.setImageResource(R.drawable.toggle_off);

        uploadDataIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uploadData.equals(Parameter.V_TOGGLE_BUTTON_ON)){
                    uploadData = Parameter.V_TOGGLE_BUTTON_OFF;
                    SharedPreferenceTool.write(Parameter.K_SETTING_UPLOAD_DATA,
                            Parameter.V_TOGGLE_BUTTON_OFF, getActivity());
                    uploadDataIV.setImageResource(R.drawable.toggle_off);
                }else{
                    uploadData = Parameter.V_TOGGLE_BUTTON_ON;
                    SharedPreferenceTool.write(Parameter.K_SETTING_UPLOAD_DATA,
                            Parameter.V_TOGGLE_BUTTON_ON, getActivity());
                    uploadDataIV.setImageResource(R.drawable.toggle_on);
                }
            }
        });

        receiveAdIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(receiveAd.equals(Parameter.V_TOGGLE_BUTTON_ON)){
                    receiveAd = Parameter.V_TOGGLE_BUTTON_OFF;
                    SharedPreferenceTool.write(Parameter.K_SETTING_RECEIVE_ADVERTISEMENT,
                            Parameter.V_TOGGLE_BUTTON_OFF, getActivity());
                    receiveAdIV.setImageResource(R.drawable.toggle_off);
                }else{
                    receiveAd = Parameter.V_TOGGLE_BUTTON_ON;
                    SharedPreferenceTool.write(Parameter.K_SETTING_RECEIVE_ADVERTISEMENT,
                            Parameter.V_TOGGLE_BUTTON_ON, getActivity());
                    receiveAdIV.setImageResource(R.drawable.toggle_on);
                }
            }
        });

        filterAdIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filterAd.equals(Parameter.V_TOGGLE_BUTTON_ON)){
                    filterAd = Parameter.V_TOGGLE_BUTTON_OFF;
                    SharedPreferenceTool.write(Parameter.K_SETTING_FILTER_ADVERTISEMENT,
                            Parameter.V_TOGGLE_BUTTON_OFF, getActivity());
                    filterAdIV.setImageResource(R.drawable.toggle_off);
                }else{
                    filterAd = Parameter.V_TOGGLE_BUTTON_ON;
                    SharedPreferenceTool.write(Parameter.K_SETTING_FILTER_ADVERTISEMENT,
                            Parameter.V_TOGGLE_BUTTON_ON, getActivity());
                    filterAdIV.setImageResource(R.drawable.toggle_on);
                }
            }
        });

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
                SettingFragment.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

}
