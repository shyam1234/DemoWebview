package com.malviya.demowebview.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.malviya.demowebview.R;
import com.malviya.demowebview.constants.Constant;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class MainActivity extends AppCompatActivity {


    private WebView mWebviewMain;
    private CircularProgressBar mCircularProgressBar;
    private TextView mTextViewLoading;
    private Runnable mRunnable;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initView();
    }

    private void init() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                mCircularProgressBar.setVisibility(View.GONE);
                mTextViewLoading.setVisibility(View.GONE);
                mHandler.removeCallbacks(mRunnable);
            }
        };
    }

    private void initView() {
        mWebviewMain = (WebView) findViewById(R.id.webview_main);
        mCircularProgressBar = (CircularProgressBar) findViewById(R.id.circularProgressbar);
        mCircularProgressBar.setColor(ContextCompat.getColor(this, R.color.colorAccent));
        mCircularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        mCircularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.progressBarWidth));
        mCircularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.backgroundProgressBarWidth));
        mTextViewLoading = (TextView) findViewById(R.id.loading);
        mCircularProgressBar.setVisibility(View.GONE);
        mTextViewLoading.setVisibility(View.GONE);
        loadWebApp();
    }

    private void loadWebApp() {
        mWebviewMain.setWebChromeClient(new MyWebViewClient());
        WebSettings webSettings = mWebviewMain.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebviewMain.loadUrl(Constant.URL);
    }

    private class MyWebViewClient extends WebChromeClient {


        public MyWebViewClient() {
            onLoadResource(null, null);
        }


        //If you will not use this method url links are opeen in new brower not in webview
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }


        //Show loader on url load
        public void onLoadResource(WebView view, String url) {
            mCircularProgressBar.setVisibility(View.VISIBLE);
            mTextViewLoading.setVisibility(View.VISIBLE);
        }


        public void onProgressChanged(WebView view, int progress) {

            //progressDialog.setProgress((progress));
            int animationDuration = 2500; // 2500ms = 2,5s
            mCircularProgressBar.setProgressWithAnimation(progress, animationDuration); // Default duration = 1500ms
            mTextViewLoading.setText("" + progress + " %");
            if (progress == 100) {
                mCircularProgressBar.setProgress(progress);
                mHandler.postDelayed(mRunnable, animationDuration);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebviewMain.canGoBack()) {
            mWebviewMain.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
}
