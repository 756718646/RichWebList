package com.yongzheng.com.richwebproject;

import android.app.ActionBar;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.coolindicator.sdk.CoolIndicator;
import com.yongzheng.com.richwebproject.base.BaseAdapter;
import com.yongzheng.com.richwebproject.base.BaseHolder;
import com.yongzheng.com.richwebproject.view.ArticlePostItemView;
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
 * 空列表头部限制情况，主要使用setTopOffset 限制滑动范围
 * 说明:流程 1，估算列表高度，2，setTopOffset 设置头部滑动的范围
 */
public class EmptyListActivity extends AppCompatActivity {

    private RichWebView webView;
    private RecyclerView recyclerView;
    private HeaderViewPager scrollableLayout;//滚动控件父容器
    private CoolIndicator indicator;//进度条

    private List<String> datas = new ArrayList<>();
    private ListAdapter adapter;

    private Context context;

    private int viewPortWidth;//屏幕宽度
    private int viewPortHeight;
    private View topHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_list);
        this.context = this;
        int[] screenSize = getScreenSize(context.getResources());
        viewPortWidth = screenSize[0];
        viewPortHeight = screenSize[1];
        initView();
    }

    private void initView() {
        topHead = findViewById(R.id.topHead);
        webView = findViewById(R.id.web_view);
        recyclerView = findViewById(R.id.recycler_view);
        scrollableLayout = findViewById(R.id.scrollableLayout);
        indicator = findViewById(R.id.indicator);
        //这里是测试数据，目前为了看效果设置1，也可以设置0看效果
        for (int i=0;i<1;i++){
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
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                showTopLimitDo();
            }
        },500);
    }

    class ListAdapter extends BaseAdapter<String> {

        public ListAdapter(List<String> data) {
            super(R.layout.default_item,data);
        }


        @Override
        protected void convert(BaseHolder helper, String item) {
            ArticlePostItemView main = (ArticlePostItemView) helper.itemView;
            main.setIndex(helper.getAdapterPosition());
            main.setCallBack(measureCallBack);

            helper.setText(R.id.text,"我是item "+helper.getAdapterPosition());
        }
    }

    /**
     * 获取屏幕分辨率
     */
    public static int[] getScreenSize(Resources resources) {
        int width = resources.getDisplayMetrics().widthPixels;
        int height = resources.getDisplayMetrics().heightPixels;
        int[] result = new int[2];
        result[0] = width;
        result[1] = height;
        return result;
    }

    /**
     * 测量item高度
     */
    ArticlePostItemView.ItemMeasureCallBack measureCallBack = new ArticlePostItemView.ItemMeasureCallBack() {
        @Override
        public void measureItem(int index, int h) {
            if (index>20){
                //仅记录,这里的20只是测试，可以根据业务进行对应调整
                return;
            }
            itemHeights.put(index,h);
            getItemMaxHeight();
        }
    };

    /**
     * 测量前20个item的总高度
     * @return
     */
    public int getItemMaxHeight(){
        int max = 0;
        for (int i=0;i<itemHeights.size();i++){
            Integer itemH = itemHeights.get(i);
            if (itemH!=null){
                max = itemH + max;
            }
        }
        return max;
    }

    //记录列表前20个高度，为了估算列表高度
    private SparseArray<Integer> itemHeights = new SparseArray();

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

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 估算列表的高度，这里是测量每一个item的高度(测量回调，然后保存)
     * @return
     */
    private int mearsureRecycleHeight(){
        int max = getItemMaxHeight();
        return max;
    }

    /**
     * 防止底部空白过得，头部限制滑动距离
     */
    private void showTopLimitDo() {
        int topHeight = scrollableLayout.getMeasuredHeight();
        int th = topHead.getHeight();
        //估算列表是否足够长度
        int countHeight = mearsureRecycleHeight();
        int topAndButton = dip2px(context,100);
        //如果头部过少，又没有评论情况(实际头部+估算的内容数据)
        if (th+countHeight + topAndButton<viewPortHeight){
            scrollableLayout.setTopOffset(0);
            scrollableLayout.setCanScroll(false);
            //禁止滑动
            return;
        }
        //可以滑动
        scrollableLayout.setCanScroll(true);
        if (countHeight >= viewPortHeight) {
            scrollableLayout.setTopOffset(0);
        }else if (countHeight >= topHeight){
            scrollableLayout.setTopOffset(0);
        }else {
            //减去其他空间的高度(状态栏+虚拟键盘等)，这里就不多做处理，默认10
            int otherHeight = dip2px(context,10);
            int tem = viewPortHeight - countHeight - otherHeight;
            if (tem<0)tem = 0;
            //下面设置一个比例，底部空白比例
            scrollableLayout.setTopOffset(tem);
        }
    }

}
