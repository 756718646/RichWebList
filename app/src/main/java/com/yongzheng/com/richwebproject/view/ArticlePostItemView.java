package com.yongzheng.com.richwebproject.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 文章下面帖子
 * 用于测量item高度
 */
public class ArticlePostItemView extends LinearLayout{

    private int index;

    private final String TAG = "ArticlePostItemView";

    private ItemMeasureCallBack callBack;

    public interface ItemMeasureCallBack{
        void measureItem(int index,int h);
    }

    public ArticlePostItemView(Context context) {
        super(context);
    }

    public ArticlePostItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ArticlePostItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int h = getMeasuredHeight();
        //测量高度回调
        if (callBack!=null){
            callBack.measureItem(index,h);
        }
    }

    public void setCallBack(ItemMeasureCallBack callBack) {
        this.callBack = callBack;
    }
}
