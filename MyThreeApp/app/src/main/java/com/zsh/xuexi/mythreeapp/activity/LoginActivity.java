package com.zsh.xuexi.mythreeapp.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zsh.xuexi.mythreeapp.R;
import com.zsh.xuexi.mythreeapp.commons.ActivityUtils;
import com.zsh.xuexi.mythreeapp.commons.LoginView;
import com.zsh.xuexi.mythreeapp.http.GitHubApi;
import com.zsh.xuexi.mythreeapp.presenter.LoginPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by zsh on 2016/7/29.
 * 授权登陆
 * 1. GitHub会有一个授权url （用webview）
 * 2. 同意授权后，将重定向到另一个URL,带出临时授权码code
 * 3. 用code去授权,得到token
 * 4. 使用token就能访问用户接口,得到用户数据
 */
public class LoginActivity extends AppCompatActivity implements LoginView {
    @Bind(R.id.webView) WebView webView;
    @Bind(R.id.gifImageView) GifImageView gifImageView;//动态图片
    @Bind(R.id.toolbar) Toolbar toolbar;

    private ActivityUtils activityUtils;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        activityUtils=new ActivityUtils(this);
        //设置toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loginPresenter = new LoginPresenter(this);
        initWebView();
    }

    private void initWebView() {
        //删除所有的cookie，主要为了清除以前的登陆记录
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        //授权登陆url
        webView.loadUrl(GitHubApi.AUTH_URL);
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);

        //主要为了监听进度
        webView.setWebChromeClient(webChromeClient);

        //监听webview（url会刷新）
        webView.setWebViewClient(webViewClient);
    }

    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress >= 100) {//进度完成
                gifImageView.setVisibility(View.GONE);
            }
        }
    };

    private WebViewClient webViewClient = new WebViewClient() {
        // 每当webview"刷新"时,此方法将触发
        // (密码输错了时！输对了时！等等情况web页面都会刷新变化的)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //检测是不是我们的call_back
            Uri uri = Uri.parse(url);
            if (GitHubApi.CALL_BACK.equals(uri.getScheme())) {
                //获取code
                String code = uri.getQueryParameter("code");
                //用code做登陆业务工作
                loginPresenter.login(code);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };


    @Override
    public void showProgress() {
        gifImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void resetWeb() {
        initWebView();
    }

    @Override
    public void navigateToMain() {
        activityUtils.startActivity(MainActivity.class);
        finish();
    }
}
