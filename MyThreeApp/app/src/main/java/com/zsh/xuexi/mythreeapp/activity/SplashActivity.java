package com.zsh.xuexi.mythreeapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zsh.xuexi.mythreeapp.R;
import com.zsh.xuexi.mythreeapp.commons.ActivityUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zsh on 2016/7/26.
 * 首页面,第一次启动时进入的页面
 */
public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.btnLogin) Button btnLogin;
    @Bind(R.id.btnEnter) Button btnEnter;
    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        activityUtils = new ActivityUtils(this);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btnLogin, R.id.btnEnter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                activityUtils.startActivity(LoginActivity.class);
                finish();
                break;
            case R.id.btnEnter:
                activityUtils.startActivity(MainActivity.class);
                finish();
                break;
        }
    }
}
