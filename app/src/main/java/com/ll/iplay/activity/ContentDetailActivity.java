package com.ll.iplay.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.ll.iplay.common.Constants;

public class ContentDetailActivity extends AppCompatActivity {

    private WebView contentWebView;
    private ImageView backImage;

    private String url = Constants.REQUEST_PREFIX + "content/seeContentDetail?appKey=" + Constants.APP_KEY;
    private String contentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_detail);
        contentId = getIntent().getStringExtra("contentId");
        initView();
        setListener();
    }

    private void initView() {
        backImage = (ImageView) findViewById(R.id.id_back_image);

        contentWebView = (WebView) findViewById(R.id.id_content_web_view);
        contentWebView.getSettings().setUseWideViewPort(true); // 设置加载进来的页面自适应手机屏幕（可缩放）
        contentWebView.getSettings().setLoadWithOverviewMode(true);
        contentWebView.setWebViewClient(new WebViewClient());
        contentWebView.getSettings().setJavaScriptEnabled(true);
        contentWebView.loadUrl(url + "&contentId=" + contentId);
    }

    private void setListener() {
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentDetailActivity.this.finish();
            }
        });
    }
}
