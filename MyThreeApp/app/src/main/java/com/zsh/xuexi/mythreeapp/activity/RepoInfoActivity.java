package com.zsh.xuexi.mythreeapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zsh.xuexi.mythreeapp.R;
import com.zsh.xuexi.mythreeapp.commons.ActivityUtils;
import com.zsh.xuexi.mythreeapp.commons.RepoInfoView;
import com.zsh.xuexi.mythreeapp.entity.Repo;
import com.zsh.xuexi.mythreeapp.presenter.RepoInfoPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zsh on 2016/8/1.
 */
public class RepoInfoActivity extends AppCompatActivity implements RepoInfoView {
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.ivIcon) ImageView ivIcon;
    @Bind(R.id.tvRepoName) TextView tvRepoName;
    @Bind(R.id.tvRepoStars) TextView tvRepoStars;
    @Bind(R.id.layoutIntroduction) RelativeLayout layoutIntroduction;
    @Bind(R.id.tvRepoInfo) TextView tvRepoInfo;
    @Bind(R.id.webView) WebView webView;// 用来展示readme的
    @Bind(R.id.progressBar) ProgressBar progressBar;// loading

    private ActivityUtils activityUtils;
    private RepoInfoPresenter presenter;

    private Repo repo;//当前仓库
    private static final String KEY_REPO ="key_repo";

    public static  void open(Context context,@Nullable  Repo repo){
        Intent intent=new Intent(context,RepoInfoActivity.class);
        intent.putExtra(KEY_REPO,repo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
        setContentView(R.layout.activity_repo_info);

        presenter = new RepoInfoPresenter(this);
        presenter.getReadme(repo);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //获取到Repo
        repo= (Repo) getIntent().getSerializableExtra(KEY_REPO);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //使用仓库名称作为title
        getSupportActionBar().setTitle(repo.getName());
        //设置仓库信息
        tvRepoName.setText(repo.getFullName());
        tvRepoInfo.setText(repo.getDescription());
        tvRepoStars.setText(String.format("start:%d   fork:%d",repo.getStartCount(),repo.getForkCount()));
        ImageLoader.getInstance().displayImage(repo.getOwner().getAvatar(),ivIcon);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgrss() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void setData(String htmlContent) {
        Log.i("ms","htmlContent="+htmlContent);
        webView.loadData(htmlContent,"text/html","UTF-8");
    }
}
