package com.yongzheng.com.richwebproject;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.coolindicator.sdk.CoolIndicator;
import com.yongzheng.com.richwebproject.base.BaseAdapter;
import com.yongzheng.com.richwebproject.base.BaseHolder;
import com.yongzheng.com.richwebproject.view.HeaderScrollHelper;
import com.yongzheng.com.richwebproject.view.HeaderViewPager;
import com.yongzheng.com.richwebproject.web.RichWebView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yongzheng on 2018/8/24.
 * 显示url
 */
public class RichUrlActivity extends AppCompatActivity {

    private RichWebView webView;
    private RecyclerView recyclerView;
    private HeaderViewPager scrollableLayout;//滚动控件父容器
    private CoolIndicator indicator;//进度条

    private List<String> datas = new ArrayList<>();
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rich_url);
        initView();
    }

    private void initView() {
        webView = findViewById(R.id.web_view);
        recyclerView = findViewById(R.id.recycler_view);
        scrollableLayout = findViewById(R.id.scrollableLayout);
        indicator = findViewById(R.id.indicator);
        for (int i=0;i<30;i++){
            datas.add("");
        }
        webView.loadUrl("https://github.com/756718646/RichWebList");
        adapter = new ListAdapter(datas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        //滚动绑定
        scrollableLayout.setCurrentScrollableContainer(new HeaderScrollHelper.ScrollableContainer(){
            @Override
            public View getScrollableView() {
                return recyclerView;
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                indicator.start();
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                indicator.complete();
            }
        });
    }

    class ListAdapter extends BaseAdapter<String> {

        public ListAdapter(List<String> data) {
            super(R.layout.default_item,data);
        }

        @Override
        protected void convert(BaseHolder helper, String item) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearCache(true);
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
    }
}
