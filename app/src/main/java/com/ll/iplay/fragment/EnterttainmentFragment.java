package com.ll.iplay.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ll.iplay.activity.R;
import com.ll.iplay.adapter.EntertainmentAdapter;
import com.ll.iplay.common.Constants;
import com.ll.iplay.gson.EntertainmentDescribe;
import com.ll.iplay.handler.EntertainmentHandler;
import com.ll.iplay.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by ll on 2017/3/12.
 */

public class EnterttainmentFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    public static EnterttainmentFragment enterttainmentFragment;

    private EntertainmentAdapter entertainmentAdapter;

    private List<EntertainmentDescribe> entertainmentDescribes = new ArrayList<EntertainmentDescribe>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        enterttainmentFragment = this;
        View view =inflater.inflate(R.layout.entertainment_fragment,container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swip_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //初试化enterttainment
        initEnterttainment();
        setListener();
        return view;
    }

    private void setListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshEntertainment();
            }
        });
    }

    private void initEnterttainment() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String cityCode = sharedPreferences.getString(Constants.CURRENT_CITY_CODE, "");
        String url = Constants.REQUEST_PREFIX + "content/getContentDescribe";
        Map<String, String> params = new HashMap<String, String>();
        params.put("cityCode", cityCode);
        params.put("typeId", "2");
        HttpUtil.sendOkHttpRequestByPost(url, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "获取娱乐信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                entertainmentDescribes = EntertainmentHandler.handleEntertainmentDescribesResponse(responseText);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        entertainmentAdapter = new EntertainmentAdapter(getContext(), entertainmentDescribes);
                        recyclerView.setAdapter(entertainmentAdapter);
                    }
                });
            }
        });
    }

    public void refreshEntertainment() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String cityCode = sharedPreferences.getString(Constants.CURRENT_CITY_CODE, "");
        String url = Constants.REQUEST_PREFIX + "content/getContentDescribe";
        Map<String, String> params = new HashMap<String, String>();
        params.put("cityCode", cityCode);
        params.put("typeId", "2");
        HttpUtil.sendOkHttpRequestByPost(url, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "刷新娱乐信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                entertainmentDescribes = EntertainmentHandler.handleEntertainmentDescribesResponse(responseText);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //需要将网络请求的数据重新set到adapter里面。
                        entertainmentAdapter.setEntertainmentDescribes(entertainmentDescribes);
                        entertainmentAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "刷新娱乐信息完成", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
