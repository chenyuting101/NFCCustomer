package chenyuting.com.nfccustomer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import java.util.ArrayList;

import chenyuting.com.nfccustomer.adapter.UseCouponRecordListBaseAdapter;
import chenyuting.com.nfccustomer.models.UseCouponRecord;
import chenyuting.com.nfccustomer.tool.DataBaseTool;
import yalantis.com.sidemenu.sample.R;

/**
 * Created by chenyuting on 11/10/17.
 */

public class UseCouponRecordFragment extends Fragment {
    private ListView useCouponRecordLV;
    private UseCouponRecordListBaseAdapter useCouponRecordListBaseAdapter;
    private ArrayList<UseCouponRecord> useCouponRecords;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_use_coupon_record, container, false);
        DataBaseTool dataBaseTool = new DataBaseTool();
        useCouponRecords = dataBaseTool.getAllUseCouponRecord(this.getActivity());

        useCouponRecordLV = (ListView) rootView.findViewById(R.id.fragment_use_coupon_record_lv);

        useCouponRecordListBaseAdapter = new UseCouponRecordListBaseAdapter(this.getActivity());
        useCouponRecordListBaseAdapter.setUseCouponRecords(useCouponRecords);
        useCouponRecordLV.setAdapter(useCouponRecordListBaseAdapter);

        return rootView;
    }

}
