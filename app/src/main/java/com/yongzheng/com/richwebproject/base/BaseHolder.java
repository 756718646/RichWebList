package com.yongzheng.com.richwebproject.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * 简单holder封装
 */
public class BaseHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> views;
    private final SparseArray<Object> tags;//存放临时tag
    private final Context context;
    private View convertView;

    public BaseHolder(Context context, View itemView) {
        super(itemView);
        views = new SparseArray<>();
        tags = new SparseArray<>();
        this.context = context;
        this.convertView = itemView;
    }

    public  <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 获取tag
     * @param key
     * @param <T>
     * @return
     */
    public  <T extends Object> T getTag(int key) {
        Object tag = tags.get(key);
        if (tag==null)return null;
        return (T)tag;
    }

    /**
     * 设置tag
     * @param key
     * @param obj
     */
    public void putTag(int key,Object obj){
        tags.put(key,obj);
    }

    public BaseHolder setText(int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);
        return this;
    }
}
