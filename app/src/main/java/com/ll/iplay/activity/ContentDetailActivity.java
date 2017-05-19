package com.ll.iplay.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ll.iplay.common.Constants;

public class ContentDetailActivity extends AppCompatActivity {

    private WebView contentWebView;
    private String url = Constants.REQUEST_PREFIX + "content/seeContentDetail?appKey=" + Constants.APP_KEY;
    private String contentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_detail);
        contentId = getIntent().getStringExtra("contentId");
        initView();
    }

    private void initView() {
        contentWebView = (WebView) findViewById(R.id.id_content_web_view);
        contentWebView.getSettings().setUseWideViewPort(true); // 设置加载进来的页面自适应手机屏幕（可缩放）
        contentWebView.getSettings().setLoadWithOverviewMode(true);
        contentWebView.setWebViewClient(new WebViewClient());
        contentWebView.getSettings().setJavaScriptEnabled(true);
        contentWebView.loadUrl(url + "&contentId=" + contentId);
    }
}
