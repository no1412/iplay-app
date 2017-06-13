package com.ll.iplay.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.ll.iplay.adapter.ContentAdapter;
import com.ll.iplay.common.Constants;
import com.ll.iplay.gson.ContentDescribe;
import com.ll.iplay.handler.ContentHandler;
import com.ll.iplay.util.HttpUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/***
 * created by ll
 */
public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;

    private ImageView backImageView;

    private List<ContentDescribe> contentDescribes;

    private ContentAdapter contentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initData();
        setListener();
    }

    private void initData() {
        searchView = (SearchView) findViewById(R.id.searchv_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        backImageView = (ImageView) findViewById(R.id.id_back_image);

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query !=null && !query.equals("")) {
                    //Toast.makeText(getApplicationContext(), "测试点击提交", Toast.LENGTH_SHORT).show();
                    searchMsg(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText !=null && !newText.equals("")) {
                    //Toast.makeText(getApplicationContext(), newText, Toast.LENGTH_SHORT).show();
                    searchMsg(newText);
                }
                return false;
            }
        });
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.this.finish();
            }
        });
    }
    public void searchMsg(String searchText) {

        String url = Constants.REQUEST_PREFIX + "content/searchContentDescribe";
        Map<String, String> params = new HashMap<String, String>();
        params.put("searchText", searchText);
        HttpUtil.sendOkHttpRequestByPost(url, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "搜索信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                if (contentDescribes != null) {
                    contentDescribes.clear();
                }
                contentDescribes = ContentHandler.handleContentDescribesResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (contentAdapter == null) {
                            contentAdapter = new ContentAdapter(getApplicationContext(), contentDescribes);
                            recyclerView.setAdapter(contentAdapter);
                        }
                        contentAdapter.setContentDescribes(contentDescribes);
                        contentAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

}
