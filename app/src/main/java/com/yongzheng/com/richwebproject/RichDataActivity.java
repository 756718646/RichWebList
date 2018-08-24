package com.yongzheng.com.richwebproject;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.coolindicator.sdk.CoolIndicator;
import com.yongzheng.com.richwebproject.base.BaseAdapter;
import com.yongzheng.com.richwebproject.base.BaseHolder;
import com.yongzheng.com.richwebproject.view.HeaderScrollHelper;
import com.yongzheng.com.richwebproject.view.HeaderViewPager;
import com.yongzheng.com.richwebproject.web.JavaScriptLog;
import com.yongzheng.com.richwebproject.web.RichWebView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yongzheng on 2018/8/24.
 * 显示html标签内容
 */
public class RichDataActivity extends AppCompatActivity {

    private RichWebView webView;
    private RecyclerView recyclerView;
    private HeaderViewPager scrollableLayout;//滚动控件父容器
    private CoolIndicator indicator;//进度条

    private List<String> datas = new ArrayList<>();
    private ListAdapter adapter;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rich_data);
        this.context = this;
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
        //设置点击图片
        webView.addJavascriptInterface(new JavaScriptLog(this, new JavaScriptLog.ClickImageCallBack() {
            @Override
            public void clickImage(String src) {
                Toast.makeText(context,"点击:"+src,Toast.LENGTH_SHORT).show();
            }
        }), "control");
        //设置html内容
        webView.setShow(getHtmlData());
        //设置图片加载失败回调
        webView.setLoadImgError();
        //添加点击图片脚本事件
        webView.setImageClickListener();
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
    }

    class ListAdapter extends BaseAdapter<String> {

        public ListAdapter(List<String> data) {
            super(R.layout.default_item,data);
        }

        @Override
        protected void convert(BaseHolder helper, String item) {
            helper.setText(R.id.text,"我是item "+helper.getAdapterPosition());
        }
    }


    private String getHtmlData() {
        StringBuffer sb = new StringBuffer();
        try {
            InputStream is = getAssets().open("data.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String temp = "";
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb.toString();
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
