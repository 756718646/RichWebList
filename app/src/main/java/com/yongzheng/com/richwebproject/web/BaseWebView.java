package com.yongzheng.com.richwebproject.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class BaseWebView extends WebView {

	TextView title;

	public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context,attrs,defStyleAttr);
	}


	private WebViewClient client = new WebViewClient() {
		/**
		 * 防止加载网页时调起系统浏览器
		 */
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	};

	@SuppressLint("SetJavaScriptEnabled")
	public BaseWebView(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
		this.setWebViewClient(client);
		// this.setWebChromeClient(chromeClient);
		// WebStorage webStorage = WebStorage.getInstance();
		initWebViewSettings();
		setClickable(true);
	}

	private void initWebViewSettings() {
		WebSettings webSetting = this.getSettings();
		webSetting.setJavaScriptEnabled(true);
		webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
		webSetting.setAllowFileAccess(true);
		webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		webSetting.setSupportZoom(true);
		webSetting.setBuiltInZoomControls(true);
		webSetting.setUseWideViewPort(true);
		webSetting.setSupportMultipleWindows(true);
		// webSetting.setLoadWithOverviewMode(true);
		webSetting.setAppCacheEnabled(true);
		// webSetting.setDatabaseEnabled(true);
		webSetting.setDomStorageEnabled(true);
		webSetting.setGeolocationEnabled(true);
		webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
		// webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
		webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
		// webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
		webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

		// this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
		// settings 的设计
	}

	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		boolean ret = super.drawChild(canvas, child, drawingTime);
//		if (BuildConfig.IS_LOG){
//			canvas.save();
//			Paint paint = new Paint();
//			paint.setColor(0x7fff0000);
//			paint.setTextSize(24.f);
//			paint.setAntiAlias(true);
//			if (getX5WebViewExtension() != null) {
//				canvas.drawText(this.getContext().getPackageName() + "-pid:"
//						+ android.os.Process.myPid(), 10, 50, paint);
//				canvas.drawText(
//						"X5  Core:" + QbSdk.getTbsVersion(this.getContext()), 10,
//						100, paint);
//			} else {
//				canvas.drawText(this.getContext().getPackageName() + "-pid:"
//						+ android.os.Process.myPid(), 10, 50, paint);
//				canvas.drawText("Sys Core", 10, 100, paint);
//			}
//			canvas.drawText(Build.MANUFACTURER, 10, 150, paint);
//			canvas.drawText(Build.MODEL, 10, 200, paint);
//			canvas.restore();
//		}
		return ret;
	}

	public BaseWebView(Context arg0) {
		super(arg0);
		setBackgroundColor(85621);
	}

	//onDraw表示显示完毕
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

}
