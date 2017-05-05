package com.ll.iplay.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ll.iplay.activity.R;
import com.ll.iplay.adapter.FoodAdapter;
import com.ll.iplay.common.Constants;
import com.ll.iplay.gson.FoodDescribe;
import com.ll.iplay.hendler.FoodHandler;
import com.ll.iplay.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by ll on 2017/3/12.
 */

public class FoodFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;

    private List<FoodDescribe> foodDescribes = new ArrayList<FoodDescribe>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_fragment,container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        //初试化food
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFoods();
    }

    private void initFoods() {
        showProgressDialog();
        foodDescribes.clear();
        String getFoodDEscribesUrl = Constants.REQUEST_PREFIX + "food/getFoodDescribe";
        HttpUtil.sendOkHttpRequest(getFoodDEscribesUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "获取美食信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                foodDescribes = FoodHandler.handleFoodDescribesResponse(responseText);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        recyclerView.setAdapter(new FoodAdapter(getContext(), foodDescribes));
                    }
                });
            }
        });
//        Food food1 = new Food(R.drawable.pizza, "遇到一个很好吃的披萨", R.drawable.user_head, "阿萨德");
//        foodList.add(food1);
//        Food food2 = new Food(R.drawable.pizza, "遇到一个很好吃的披萨", R.drawable.user_head, "阿萨德");
//        foodList.add(food2);
//        Food food3 = new Food(R.drawable.avatar, "遇到一个很好吃的披萨", R.drawable.user_head, "阿萨德");
//        foodList.add(food3);
//        Food food4 = new Food(R.drawable.pizza, "遇到一个很好吃的披萨", R.drawable.user_head, "阿萨德");
//        foodList.add(food4);
//        Food food5 = new Food(R.drawable.pizza, "遇到一个很好吃的披萨", R.drawable.user_head, "阿萨德");
//        foodList.add(food5);
//        Food food6 = new Food(R.drawable.avatar, "遇到一个很好吃的披萨", R.drawable.user_head, "阿萨德");
//        foodList.add(food6);
    }
    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
