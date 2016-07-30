package com.zsh.xuexi.mythreeapp.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.zsh.xuexi.mythreeapp.R;
import com.zsh.xuexi.mythreeapp.commons.ActivityUtils;
import com.zsh.xuexi.mythreeapp.fragment.HotRepoFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zsh on 2016/7/26.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.navigationView) NavigationView navigationView;// 侧滑菜单视图
    @Bind(R.id.drawerLayout) DrawerLayout drawerLayout;// 抽屉(包含内容+侧滑菜单)

    private HotRepoFragment hotRepoFragment;//热门仓库fragment

    private Button btnLogin;
    private ImageView ivIcon;

    private ActivityUtils activityUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils=new ActivityUtils(this);
        // 设置当前视图(也就是说，更改了当前视图内容,将导至onContentChanged方法触发)
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        //actionbar处理
        setSupportActionBar(toolbar);
        //设置navigationview的监听器
        navigationView.setNavigationItemSelectedListener(this);
        //构建抽屉的监听
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        toggle.syncState();//根据drawerlayout同步器当前状态
        //设置抽屉监听
        drawerLayout.addDrawerListener(toggle);

        btnLogin=ButterKnife.findById(navigationView.getHeaderView(0),R.id.btnLogin);
        ivIcon=ButterKnife.findById(navigationView.getHeaderView(0),R.id.ivIcon);
        btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                activityUtils.startActivity(LoginActivity.class);
                finish();
            }
        });

        //默认显示热门仓库的fragment
        hotRepoFragment=new HotRepoFragment();
        replaceFragment(hotRepoFragment);
    }
    //替换fragment的方法
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.commit();
    }

    //侧滑菜单监听器
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.github_hot_repo:// 热门仓库
                break;
        }
        // 返回true，代表将该菜单项变为checked状态
        return true;
    }
}
