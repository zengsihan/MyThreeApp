package com.zsh.xuexi.mythreeapp.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.zsh.xuexi.mythreeapp.R;
import com.zsh.xuexi.mythreeapp.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zsh on 2016/7/26.
 */
public class Test extends BaseActivity {

    @Bind(R.id.test_btn_1)
    Button testBtn1;
    @Bind(R.id.test_btn_2)
    Button testBtn2;
    @Bind(R.id.test_btn_3)
    Button testBtn3;
    @Bind(R.id.test_btn_4)
    Button testBtn4;
    @Bind(R.id.test_iv)
    ImageView testIv;

    @Override
    public void setLaout() {
        setContentView(R.layout.test_activity);
    }

    @Override
    public void getData() {

    }

    @Override
    public void getView() {

    }

    @Override
    public void setView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.test_btn_1, R.id.test_btn_2, R.id.test_btn_3, R.id.test_btn_4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test_btn_1:
                Toast.makeText(Test.this, "1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.test_btn_2:
                break;
            case R.id.test_btn_3:
                break;
            case R.id.test_btn_4:
                break;
        }
    }

    @OnClick(R.id.test_iv)
    public void onClick() {
    }
}
