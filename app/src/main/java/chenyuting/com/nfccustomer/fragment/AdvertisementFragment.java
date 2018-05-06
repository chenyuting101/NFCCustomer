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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import chenyuting.com.nfccustomer.adapter.AdvertisementListBaseAdapter;
import chenyuting.com.nfccustomer.models.Coupon;
import chenyuting.com.nfccustomer.models.Item;
import chenyuting.com.nfccustomer.tool.DataBaseTool;
import chenyuting.com.nfccustomer.tool.Parameter;
import chenyuting.com.nfccustomer.tool.SharedPreferenceTool;
import chenyuting.com.nfccustomer.tool.UseNetTool;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.sample.R;

/**
 * Created by chenyuting on 11/27/17.
 */

public class AdvertisementFragment extends Fragment implements ScreenShotable {
    private String TAG = "AdvertisementFragment";
    private View view;
    private View containerView;
    private Bitmap bitmap;
    private TextView idTV, descriptionTV,ruleTV,validDateTV;
    private ImageView detailIV, backIV;
    private ListView advertisementLV;
    private LinearLayout advertisementDetailLL;
    private SharedPreferenceTool sharedPreferenceTool;
    private Context context;
    private AdvertisementListBaseAdapter advertisementListBaseAdapter;

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
        View view = inflater.inflate(R.layout.fragment_advertisement_list, container, false);
        sharedPreferenceTool = new SharedPreferenceTool();
        UseNetTool useNetTool = new UseNetTool();
        final String data = useNetTool.getAdvertisementDataFromServer();

        descriptionTV = (TextView) view.findViewById(R.id.fragment_advertisement_detail_description);
        idTV = (TextView) view.findViewById(R.id.ragment_advertisement_detail_advertise_id);
        ruleTV = (TextView) view.findViewById(R.id.ragment_advertisement_detail_rule);
        validDateTV = (TextView) view.findViewById(R.id.ragment_advertisement_detail_valid_date);
        detailIV = (ImageView) view.findViewById(R.id.fragment_advertisement_detail_iv);
        advertisementDetailLL = (LinearLayout) view.findViewById(R.id.fragment_advertisement_detail_ll);
        advertisementLV = (ListView) view.findViewById(R.id.fragment_advertisement_lv);
        backIV = (ImageView) view.findViewById(R.id.fragment_advertisement_detail_back_iv);
        try {
            Log.w(TAG, "data"+data);
            final JSONArray dataJson = new JSONArray(data.trim());

            advertisementListBaseAdapter = new AdvertisementListBaseAdapter(this.getActivity());
            advertisementListBaseAdapter.setAdvertiseList(dataJson);
            advertisementLV.setAdapter(advertisementListBaseAdapter);
            backIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    advertisementLV.setVisibility(View.VISIBLE);
                    advertisementDetailLL.setVisibility(View.GONE);
                }
            });
            advertisementLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    advertisementDetailLL.setVisibility(View.VISIBLE);
                    advertisementLV.setVisibility(View.GONE);
                    try {

                        String validdate = "From "+ dataJson.getJSONObject(position)
                                .getString(Parameter.K_RULE_VALID_DATE_FROM)+" To "
                                +dataJson.getJSONObject(position).getString(Parameter.K_RULE_VALID_DATE_TO);
                        validDateTV.setText(validdate);
                        String rule = "You should collect "+dataJson.getJSONObject(position).getString(Parameter.K_RULE_NUMBER)
                                +" stamp: "+dataJson.getJSONObject(position).getString(Parameter.K_COUPON_ID);
                                //+" to change"+dataJson.getJSONObject(position).getString(Parameter.K_RULE_EXCHANGE_PRODUCT_ID);
                        ruleTV.setText(rule);
                        idTV.setText(dataJson.getJSONObject(position).getString(Parameter.K_ADVERTISEMENT_ID));
                        descriptionTV.setText(dataJson.getJSONObject(position).getString(Parameter.K_DESCRIPTION));
                        Picasso.with(getActivity()).load(dataJson.getJSONObject(position)
                                .getString(Parameter.K_SERVER_PICTURE_URL)).placeholder(R.drawable.ic_launcher)
                                .into(detailIV);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


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
                AdvertisementFragment.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

}
