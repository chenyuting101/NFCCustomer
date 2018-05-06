package chenyuting.com.nfccustomer.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yalantis.com.sidemenu.sample.R;


/**
 * Created by chenyuting on 11/10/17.
 */

public class DiscountCouponFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_coupon_discount, container, false);
        return rootView;
    }
}
