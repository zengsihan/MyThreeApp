package com.zsh.xuexi.mythreeapp.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zsh.xuexi.mythreeapp.R;
import com.zsh.xuexi.mythreeapp.commons.ActivityUtils;
import com.zsh.xuexi.mythreeapp.entity.UserRepo;
import com.zsh.xuexi.mythreeapp.fragment.FavoriteFragment;
import com.zsh.xuexi.mythreeapp.fragment.GankFragment;
import com.zsh.xuexi.mythreeapp.fragment.HotRepoFragment;
import com.zsh.xuexi.mythreeapp.fragment.HotUserFragment;

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
    private HotUserFragment hotUserFragment;
    private FavoriteFragment favoriteFragment;
    private GankFragment gankFragment;

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

    @Override
    protected void onStart() {
        super.onStart();
        //没有授权的话
        if(UserRepo.isEmpty()){
            btnLogin.setText("登陆GitHub");
            return;
        }
        btnLogin.setText("切换账号");
        //设置title
        getSupportActionBar().setTitle(UserRepo.getUser().getName());
        //设置用户头像
        String photoUrl=UserRepo.getUser().getAvatar();
        ImageLoader.getInstance().displayImage(photoUrl,ivIcon);
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
        // 将默认选中项“手动”设置为false
        if (menuItem.isChecked()) {
            menuItem.setChecked(false);
        }
        // 根据选择做切换
        switch (menuItem.getItemId()){
            case R.id.github_hot_repo:// 热门仓库
                if(!hotRepoFragment.isAdded()){
                    replaceFragment(hotRepoFragment);
                }
                break;
            case R.id.github_hot_coder:// 热门开发者
                if(hotUserFragment==null){
                    hotUserFragment=new HotUserFragment();
                }
                if(!hotUserFragment.isAdded()){
                    replaceFragment(hotUserFragment);
                }
                break;
            case R.id.arsenal_my_repo:// 我的收藏
                if(favoriteFragment==null){
                    favoriteFragment=new FavoriteFragment();
                }
                if(!favoriteFragment.isAdded()){
                    replaceFragment(favoriteFragment);
                }
                break;
            case R.id.tips_daily:
                if(gankFragment==null){
                    gankFragment=new GankFragment();
                }
                if(!gankFragment.isAdded()){
                    replaceFragment(gankFragment);
                }
                break;
        }
        // 关闭drawerLayout
        drawerLayout.post(new Runnable() {
            @Override public void run() {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        // 返回true，代表将该菜单项变为checked状态
        return true;
    }
}
