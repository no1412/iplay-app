package com.ll.iplay.fragment;

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
import com.ll.iplay.adapter.EntertainmentAdapter;
import com.ll.iplay.common.Constants;
import com.ll.iplay.gson.EntertainmentDescribe;
import com.ll.iplay.handler.EntertainmentHandler;
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

public class EnterttainmentFragment extends Fragment {

    private RecyclerView recyclerView;

    private List<EntertainmentDescribe> entertainmentDescribes = new ArrayList<EntertainmentDescribe>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.entertainment_fragment,container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //初试化enterttainment
        initEnterttainment();
        return view;
    }

    private void initEnterttainment() {
        String getFoodDEscribesUrl = Constants.REQUEST_PREFIX + "entertainment/getEntertainmentDescribe";
        HttpUtil.sendOkHttpRequest(getFoodDEscribesUrl, new Callback() {
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
                        recyclerView.setAdapter(new EntertainmentAdapter(getContext(), entertainmentDescribes));
                    }
                });
            }
        });
    }
}
