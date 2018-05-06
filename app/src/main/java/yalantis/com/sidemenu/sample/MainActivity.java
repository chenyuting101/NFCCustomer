package yalantis.com.sidemenu.sample;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import chenyuting.com.nfccustomer.activity.Main2Activity;
import chenyuting.com.nfccustomer.fragment.AdvertisementFragment;
import chenyuting.com.nfccustomer.fragment.CouponFragment;
import chenyuting.com.nfccustomer.fragment.NewReceiptFragment;
import chenyuting.com.nfccustomer.fragment.ReceiptListFragment;
import chenyuting.com.nfccustomer.fragment.ReceiptReceivedFragment;
import chenyuting.com.nfccustomer.fragment.SettingFragment;
import chenyuting.com.nfccustomer.models.Coupon;
import chenyuting.com.nfccustomer.services.AdvertisementService;
import chenyuting.com.nfccustomer.tool.Commands;
import chenyuting.com.nfccustomer.tool.DataBaseTool;
import chenyuting.com.nfccustomer.tool.Parameter;
import chenyuting.com.nfccustomer.tool.SharedPreferenceTool;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.sample.fragment.ContentFragment;
import yalantis.com.sidemenu.util.ViewAnimator;


public class MainActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ContentFragment contentFragment;
    private Toolbar toolbar;
    //Fragment contentFragment;
    private ViewAnimator viewAnimator;
    private int res = R.drawable.content_music;
    private LinearLayout linearLayout;
    private String TAG = "MainActivity";
    private MsgReceiver msgReceiver;
    //let fragment can listen to touch event
    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(
            10);

    /**
     * 广播接收器
     * @author len
     *
     */
    public class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //拿到进度，更新UI
            //int progress = intent.getIntExtra("progress", 0);
            //Log.w(TAG, "receive broadcast");
            String command = intent.getStringExtra(Commands.COMMAND);
            Log.w(TAG, "receive broadcast"+command);
            switch (command){
                case Commands.COMMAND_GET_NEW_RECEIPT:
//                    MainActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {

                            ReceiptReceivedFragment contentFragment2 = new ReceiptReceivedFragment(getBaseContext());
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment2).commitAllowingStateLoss();//.commit();
                            Log.w(TAG, "on received beging to change ui");
//                        }
//                    });

                    //contentFragment2.receivedNewReceipt();
                    break;
                case Commands.COMMAND_USE_ADVERTISEMENT:
                    String useAdData = SharedPreferenceTool.read(Parameter.K_USE_ADVERTISEMENT_DATA,context);
                    try {
                        JSONObject object = new JSONObject(useAdData);
                        int requiredNum = object.getInt(Parameter.K_RULE_NUMBER);
                        String couponId = object.getString(Parameter.K_COUPON_ID);
                        DataBaseTool dataBaseTool = new DataBaseTool();
                        int coupouNum = dataBaseTool.isCouponExist(context,couponId);
                        if(coupouNum<requiredNum){
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Coupon use");
                            builder.setMessage("You can not use this advertisement!");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }else{
                            dataBaseTool.updateCouponNum(context,couponId,""+(coupouNum-requiredNum));
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Coupon use");
                            builder.setMessage("You use this advertisement successful!");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    CouponFragment couponFragment = new CouponFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, couponFragment).commitAllowingStateLoss();//.commit();
                    Log.w(TAG, "on received beging to change ui");
                    break;
                default:break;
            }
            //mProgressBar.setProgress(progress);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG, "onCreate");
        //初始化数据库
        DataBaseTool dataBaseTool = new DataBaseTool();
        dataBaseTool.initiateDataBase(this);
        dataBaseTool.deleteCoupon(this);
        //动态注册广播接收器
        msgReceiver = new MsgReceiver();
//        Log.w(TAG,"register broadcast");
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("com.example.communication.RECEIVER");
//        registerReceiver(msgReceiver, intentFilter);
        setContentView(R.layout.activity_main);
        //contentFragment = ContentFragment.newInstance(R.drawable.content_music);
        //change here=====
        contentFragment = ContentFragment.newInstance(ContentFragment.BOOK,null);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, contentFragment)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        initialSharedPreferenceContent();


        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, contentFragment, drawerLayout, this);
        Intent intent = new Intent(this, AdvertisementService.class);
        startService(intent);

    }

    @Override
    protected void onDestroy() {
        Log.w(TAG, "unregister broadcasy");
        //注销广播
        unregisterReceiver(msgReceiver);
        super.onDestroy();
    }

    @Override
    protected void onResume (){
        Log.w(TAG,"register broadcast");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.communication.RECEIVER");
        registerReceiver(msgReceiver, intentFilter);
        super.onResume();
    }
    @Override
    protected void onNewIntent (Intent intent){
        setIntent(intent);
    }

    private void createMenuList() {
        Log.d(TAG, "createMenuList");
        System.out.println("createMenuList====================");
        SlideMenuItem menuItem0 = new SlideMenuItem(ContentFragment.CLOSE, R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(ContentFragment.BUILDING, R.drawable.icn_1);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(ContentFragment.BOOK, R.drawable.icn_2);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(ContentFragment.PAINT, R.drawable.icn_3);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(ContentFragment.CASE, R.drawable.icn_4);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(ContentFragment.SHOP, R.drawable.icn_5);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(ContentFragment.PARTY, R.drawable.icn_6);
        list.add(menuItem6);
//        SlideMenuItem menuItem7 = new SlideMenuItem(ContentFragment.MOVIE, R.drawable.icn_7);
//        list.add(menuItem7);
    }


    private void setActionBar() {
        Log.d(TAG, "setActionBar");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Welcome");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.w(TAG, "onOptionsItemSelected");
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition) {
        Log.d(TAG, "replaceFragment");
        this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        //findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        //here to set new fragment
        //ContentFragment contentFragment = ContentFragment.newInstance(ContentFragment.BOOK,null);//ContentFragment.newInstance(this.res);
        //change here ============
        //ReceiptListFragment contentFragment = new ReceiptListFragment();
        //这里根据topposition来改变fragment
        Log.d(TAG, "topPosition" +topPosition);
        switch (topPosition){
            case 1:
                ReceiptReceivedFragment contentFragment = new ReceiptReceivedFragment(getBaseContext());
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
                toolbar.setTitle("New Receipt");
                break;
            case 2:CouponFragment contentFragment2 = new CouponFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment2).commit();
                toolbar.setTitle("Coupon");
                break;
            case 3:AdvertisementFragment contentFragment3 = new AdvertisementFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment3).commit();
                toolbar.setTitle("Advertisement");
                break;
            case 4:
                Log.w(TAG, "===========sholu be here");
                SettingFragment settingFragment = new SettingFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, settingFragment).commit();
                toolbar.setTitle("Setting");
                break;
            case 5:
                NewReceiptFragment newReceiptFragment = new NewReceiptFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, newReceiptFragment).commit();
                toolbar.setTitle("Receipt List");
                break;
            case 6:
                Intent intent = new Intent(this,Main2Activity.class);
                startActivity(intent);
                break;
            default:break;


        }

        return contentFragment;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        Log.d(TAG, "onSwitch slideMenuItem"+slideMenuItem.getName());
        switch (slideMenuItem.getName()) {
            case ContentFragment.CLOSE:
                return screenShotable;
            case ContentFragment.BUILDING:
                return replaceFragment(screenShotable, 1);
            case ContentFragment.BOOK:
                return replaceFragment(screenShotable, 2);
            case ContentFragment.PAINT:
                return replaceFragment(screenShotable, 3);
            case ContentFragment.CASE:
                return replaceFragment(screenShotable, 5);
            case ContentFragment.SHOP:
                return replaceFragment(screenShotable, 4);
            case ContentFragment.PARTY:
                return replaceFragment(screenShotable, 6);

            default:
                return replaceFragment(screenShotable, position);
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            listener.onTouch(ev);
        }
        return super.dispatchTouchEvent(ev);
    }





    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }

    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener);
    }

    public interface MyOnTouchListener {
        public boolean onTouch(MotionEvent ev);
    }

    public void initialSharedPreferenceContent(){
        if (SharedPreferenceTool.read(Parameter.K_SETTING_UPLOAD_DATA, this).isEmpty()) {
            SharedPreferenceTool.write(Parameter.K_SETTING_UPLOAD_DATA, Parameter.V_TOGGLE_BUTTON_ON, this);
            Log.w(TAG, Parameter.K_SETTING_UPLOAD_DATA + SharedPreferenceTool.read(Parameter.K_SETTING_UPLOAD_DATA, this));
        }
        if (SharedPreferenceTool.read(Parameter.K_SETTING_RECEIVE_ADVERTISEMENT, this).isEmpty()) {
            SharedPreferenceTool.write(Parameter.K_SETTING_RECEIVE_ADVERTISEMENT, Parameter.V_TOGGLE_BUTTON_ON, this);
            Log.w(TAG, Parameter.K_SETTING_RECEIVE_ADVERTISEMENT + SharedPreferenceTool.read(Parameter.K_SETTING_RECEIVE_ADVERTISEMENT, this));
        }
        if (SharedPreferenceTool.read(Parameter.K_SETTING_FILTER_ADVERTISEMENT, this).isEmpty()) {
            SharedPreferenceTool.write(Parameter.K_SETTING_FILTER_ADVERTISEMENT, Parameter.V_TOGGLE_BUTTON_ON, this);
            Log.w(TAG, Parameter.K_SETTING_FILTER_ADVERTISEMENT + SharedPreferenceTool.read(Parameter.K_SETTING_FILTER_ADVERTISEMENT, this));
        }
    }
}
