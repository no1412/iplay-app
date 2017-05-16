package com.ll.iplay.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ll.iplay.activity.R;

/**
 * Created by ll on 2017/3/12.
 */

public class MyFragment extends Fragment {

    private TextView userInforSetting;

    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_center,container, false);
        userInforSetting = (TextView) view.findViewById(R.id.id_user_infor_settings);

        setListener();
        return view;
    }

    private void setListener() {
    }


}
