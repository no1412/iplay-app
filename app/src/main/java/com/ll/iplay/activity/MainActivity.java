package com.ll.iplay.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.ll.iplay.adapter.MyFragmentPagerAdapter;
import com.ll.iplay.common.Constants;
import com.ll.iplay.fragment.EnterttainmentFragment;
import com.ll.iplay.fragment.FoodFragment;
import com.ll.iplay.fragment.MyFragment;
import com.ll.iplay.refactor.FixedSpeedScroller;
import com.loc.f;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Fragment> fragmentList;

    private Button publishBtn;
    private ViewPager mainViewPager;
    private TextView foodTextView, searchTextView;
    private TextView entertainmentView;
    private TextView myTextView;
    private TextView cityLocationTextView;
    private LinearLayout foodLinearLayout;
    private LinearLayout entertainmentLinearLayout;
    private LinearLayout myLinearLayout;
    private RelativeLayout topBarRelativeLayout;

    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    private FoodFragment foodFragment;
    private EnterttainmentFragment enterttainmentFragment;
    private MyFragment myFragment;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        initLocation();
        initView();
        /*if (prefs.getString("user", null) == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }*/
    }

    /**
     * 初试化控件
     */
    private void initView() {
        publishBtn = (Button) findViewById(R.id.id_publish_btn);
        mainViewPager = (ViewPager) findViewById(R.id.id_main_viewpager);
        setViewPagerScrollSpeed();
        foodTextView = (TextView) findViewById(R.id.id_tv_food);
        searchTextView  = (TextView) findViewById(R.id.id_search_text);
        entertainmentView = (TextView) findViewById(R.id.id_tv_entertainment);
        myTextView = (TextView) findViewById(R.id.id_tv_my);
        cityLocationTextView = (TextView) findViewById(R.id.id_city_location);
        foodLinearLayout = (LinearLayout) findViewById(R.id.id_layout_food);
        entertainmentLinearLayout = (LinearLayout) findViewById(R.id.id_layout_entertainment);
        myLinearLayout = (LinearLayout) findViewById(R.id.id_layout_my);
        topBarRelativeLayout = (RelativeLayout) findViewById(R.id.id_top_bar);

        //设置缓存view 的个数（实际有3个，缓存2个+正在显示的1个）
        mainViewPager.setOffscreenPageLimit(2);
        fragmentList = new ArrayList<Fragment>();

        foodFragment = new FoodFragment();
        enterttainmentFragment = new EnterttainmentFragment();
        myFragment = new MyFragment();

        fragmentList.add(foodFragment);
        fragmentList.add(enterttainmentFragment);
        fragmentList.add(myFragment);

        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);

        /*mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position){
                    case 0 :
                        foodTextView.setTextColor(Color.parseColor("#941AE6"));
                        break;
                    case 1 :
                        entertainmentView.setTextColor(Color.parseColor("#941AE6"));
                        break;
                    case 2 :
                        myTextView.setTextColor(Color.parseColor("#941AE6"));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/

        searchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        publishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = mainViewPager.getCurrentItem();
                Intent intent = new Intent(getApplicationContext(), UploadThingsActivity.class);
                intent.putExtra("publishType", number);
                startActivity(intent);
            }
        });

        foodLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTextView();
                foodTextView.setTextColor(Color.parseColor("#941AE6"));
                topBarRelativeLayout.setVisibility(View.VISIBLE);
                mainViewPager.setCurrentItem(0);
            }
        });
        entertainmentLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTextView();
                entertainmentView.setTextColor(Color.parseColor("#941AE6"));
                topBarRelativeLayout.setVisibility(View.VISIBLE);
                mainViewPager.setCurrentItem(1);
            }
        });
        myLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTextView();
                myTextView.setTextColor(Color.parseColor("#941AE6"));
                topBarRelativeLayout.setVisibility(View.GONE);
                mainViewPager.setCurrentItem(2);
            }
        });

        mainViewPager.setAdapter(myFragmentPagerAdapter);
    }

    /**
     * 重置配色
     */
    private void resetTextView() {
        foodTextView.setTextColor(Color.parseColor("#000000"));
        entertainmentView.setTextColor(Color.parseColor("#000000"));
        myTextView.setTextColor(Color.parseColor("#000000"));

    }

    /**
     * 设置ViewPager的滑动速度
     */
    private void setViewPagerScrollSpeed( ){
        try {
            Field mScroller = null;
            mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller( mainViewPager.getContext( ) );
            mScroller.set( mainViewPager, scroller);
        }catch(NoSuchFieldException e){

        }catch (IllegalArgumentException e){

        }catch (IllegalAccessException e){

        }
    }
    /**
     * 初试化定位
     */
    public void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。
                        cityLocationTextView.setText(aMapLocation.getCity());
                        //保存当前城市编码
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
                        editor.putString(Constants.CURRENT_CITY_CODE, aMapLocation.getCityCode());
                        editor.apply();
                    }else {
                        cityLocationTextView.setText("正在定位...");
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_SHORT).show();
                        Log.e("AmapError","location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        });
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }
    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }
}
