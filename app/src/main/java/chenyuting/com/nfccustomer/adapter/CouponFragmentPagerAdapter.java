package chenyuting.com.nfccustomer.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by chenyuting on 11/10/17.
 */

public class CouponFragmentPagerAdapter extends FragmentPagerAdapter {

    //private String [] title = {"Stamp","Discount","Cash"};
    private String [] title = {"Coupon","Use Coupon Record"};

    private List<Fragment> fragmentList;
    public CouponFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
    @Override
    public int getCount() {
        return fragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
