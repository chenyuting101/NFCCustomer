package chenyuting.com.nfccustomer.fragment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import chenyuting.com.nfccustomer.adapter.CouponFragmentPagerAdapter;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.sample.R;

/**
 * Created by chenyuting on 11/10/17.
 */

public class CouponFragment extends Fragment implements ScreenShotable {
    private String[] mTitle = new String[4];
    private String[] mData = new String[4];

//    {
//        for(int i=0;i<4;i++) {
//            mTitle[i] = "title" + i;
//            mData[i] = "data" + i;
//        }
//    }
{
    for (int i = 0; i < 2; i++) {
        mTitle[i] = "title" + i;
        mData[i] = "data" + i;
    }
}
    private TabLayout mTabLayout ;
    private ViewPager mViewPager ;
    private CouponFragmentPagerAdapter mAdapter;
    private StampCouponFragment stampCouponFragment;
    private DiscountCouponFragment discountCouponFragment;
    private CashCouponFragment cashCouponFragment;
//    private RuleCouponFragment ruleCouponFragment;
private UseCouponRecordFragment useCouponRecordFragment;
    ArrayList<Fragment> flist;
    private FragmentManager fragmentManager;
    private View containerView;
    private String TAG = "CouponFragment";
    private Bitmap bitmap;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.w(TAG, "onViewCreated");
        this.containerView = view.findViewById(R.id.container);


    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_coupon,container, false);//null);
        mTabLayout = (TabLayout)view.findViewById(R.id.fragment_coupon_tl);
        mViewPager = (ViewPager)view.findViewById(R.id.fragment_coupon_viewpager);
        //FragmentManager fragmentManager = getActivity().getfR
        fragmentManager = getChildFragmentManager();//getActivity().getSupportFragmentManager();
        initFragment();
        flist=new ArrayList<Fragment>();
        flist.add(stampCouponFragment);
//        flist.add(discountCouponFragment);
//        flist.add(cashCouponFragment);
        flist.add(useCouponRecordFragment);
//        flist.add(ruleCouponFragment);

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        final TabLayout.TabLayoutOnPageChangeListener listener =
                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout);
        mViewPager.addOnPageChangeListener(listener);
        mAdapter= new CouponFragmentPagerAdapter(fragmentManager,flist);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setTabsFromPagerAdapter(mAdapter);
        return view;

    }

    private void initFragment() {
        cashCouponFragment = new CashCouponFragment();
        discountCouponFragment = new DiscountCouponFragment();
        stampCouponFragment = new StampCouponFragment();
        useCouponRecordFragment = new UseCouponRecordFragment();
//        ruleCouponFragment = new RuleCouponFragment();
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
                CouponFragment.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }
}