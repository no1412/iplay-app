package com.ll.iplay.activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ll.iplay.adapter.FoodAdapter;
import com.ll.iplay.fragment.EnterttainmentFragment;
import com.ll.iplay.fragment.FoodFragment;
import com.ll.iplay.fragment.MyFragment;
import com.ll.iplay.model.Food;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Fragment> fragmentList;

    private ViewPager mainViewPager;
    private TextView foodTextView;
    private TextView entertainmentView;
    private TextView myTextView;
    private LinearLayout foodLinearLayout;
    private LinearLayout entertainmentLinearLayout;
    private LinearLayout myLinearLayout;
    private RelativeLayout topBarRelativeLayout;

    private PagerAdapter pagerAdapter;

    private FoodFragment foodFragment;
    private EnterttainmentFragment enterttainmentFragment;
    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mainViewPager = (ViewPager) findViewById(R.id.id_main_viewpager);
        foodTextView = (TextView) findViewById(R.id.id_tv_food);
        entertainmentView = (TextView) findViewById(R.id.id_tv_entertainment);
        myTextView = (TextView) findViewById(R.id.id_tv_my);
        foodLinearLayout = (LinearLayout) findViewById(R.id.id_layout_food);
        entertainmentLinearLayout = (LinearLayout) findViewById(R.id.id_layout_entertainment);
        myLinearLayout = (LinearLayout) findViewById(R.id.id_layout_my);
        topBarRelativeLayout = (RelativeLayout) findViewById(R.id.id_top_bar);

        fragmentList = new ArrayList<Fragment>();

        foodFragment = new FoodFragment();
        enterttainmentFragment = new EnterttainmentFragment();
        myFragment = new MyFragment();

        fragmentList.add(foodFragment);
        fragmentList.add(enterttainmentFragment);
        fragmentList.add(myFragment);

        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        };

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

        mainViewPager.setAdapter(pagerAdapter);
    }

    private void resetTextView() {
        foodTextView.setTextColor(Color.parseColor("#000000"));
        entertainmentView.setTextColor(Color.parseColor("#000000"));
        myTextView.setTextColor(Color.parseColor("#000000"));

    }

}
