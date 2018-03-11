package com.linzongfu.forgotgankio.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.linzongfu.forgotgankio.R;
import com.linzongfu.forgotgankio.util.LoadingDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.wv_article)
    WebView wvArticle;

    String mUrl;
    String mTitle;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        loadingDialog = new LoadingDialog(this);

        //声明WebSettings子类
        WebSettings webSettings = wvArticle.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小


        wvArticle.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作
                loadingDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //设定加载结束的操作
                loadingDialog.dismiss();
            }
        });


        Intent intent = getIntent();
        if (intent != null) {
            mUrl = intent.getStringExtra("url");
            mTitle = intent.getStringExtra("title");
            toolbar.setTitle(mTitle);
            wvArticle.loadUrl(mUrl);
        }

    }


    public static void actionStart(Context context, String url,String title) {
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //重写左上角返回键功能
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        if (wvArticle != null) {
            wvArticle.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            wvArticle.clearHistory();

            ((ViewGroup) wvArticle.getParent()).removeView(wvArticle);
            wvArticle.destroy();
            wvArticle = null;
        }
        super.onDestroy();
    }
}
