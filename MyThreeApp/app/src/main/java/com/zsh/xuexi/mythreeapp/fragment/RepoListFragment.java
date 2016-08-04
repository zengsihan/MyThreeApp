package com.zsh.xuexi.mythreeapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.zsh.xuexi.mythreeapp.R;
import com.zsh.xuexi.mythreeapp.activity.RepoInfoActivity;
import com.zsh.xuexi.mythreeapp.adapter.RepoListAdapter;
import com.zsh.xuexi.mythreeapp.commons.ActivityUtils;
import com.zsh.xuexi.mythreeapp.commons.RepoListView;
import com.zsh.xuexi.mythreeapp.db.DbHelper;
import com.zsh.xuexi.mythreeapp.db.LocalRepoDao;
import com.zsh.xuexi.mythreeapp.db.RepoConverter;
import com.zsh.xuexi.mythreeapp.entity.LocalRepo;
import com.zsh.xuexi.mythreeapp.entity.Repo;
import com.zsh.xuexi.mythreeapp.http.Language;
import com.zsh.xuexi.mythreeapp.presenter.RepoListPresenter;
import com.zsh.xuexi.mythreeapp.view.FooterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by zsh on 2016/7/27.
 * 仓库列表
 * 将显示当前语言的所有仓库，有下拉刷新，上拉加载更多的效果
 */
public class RepoListFragment extends Fragment implements RepoListView {
    private static final String KEY_LANGUAGE="key_language";

    public static RepoListFragment getInstance(Language language){
        RepoListFragment fragment=new RepoListFragment();
        Bundle args=new Bundle();
        args.putSerializable(KEY_LANGUAGE,language);
        fragment.setArguments(args);
        return fragment;
    }
    private Language getLanguage(){
        return (Language) getArguments().getSerializable(KEY_LANGUAGE);
    }

    @Bind(R.id.lvRepos) ListView listView;
    @Bind(R.id.ptrClassicFrameLayout) PtrClassicFrameLayout ptrFrameLayout;//下拉刷新的第三方控件
    @Bind(R.id.emptyView) TextView emptyView;
    @Bind(R.id.errorView) TextView errorView;

    private RepoListAdapter adapter;
    private RepoListPresenter presenter;//用来做当前页面业务逻辑及视图更新
    private FooterView footerView;//上拉加载更多的视图
    private ActivityUtils activityUtils;//工具类
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activityUtils=new ActivityUtils(this);
        presenter=new RepoListPresenter(this,getLanguage());
        adapter = new RepoListAdapter();
        listView.setAdapter(adapter);
        // 按下某个仓库后，进入此仓库详情
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Repo repo=adapter.getItem(position);
                RepoInfoActivity.open(getContext(),repo);
            }
        });
        // 长按某个仓库后，加入收藏
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //热门仓库列表上的repo
                Repo repo=adapter.getItem(position);
                LocalRepo localRepo= RepoConverter.convert(repo);
                //添加到本地仓库表中去（只认本地仓库实体LocalRepo)
                new LocalRepoDao(DbHelper.getInstance(getContext())).createOrUpdate(localRepo);
                activityUtils.showToast("收藏成功");
                return true;
            }
        });

        initPullToRefresh();//调用下拉刷新的初始方法

        initLoadMoreScroll();//初始化上拉加载更多

        //如果当前页面没有数据，开始自动刷新
        if(adapter.getCount()==0){
            ptrFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                   ptrFrameLayout.autoRefresh();//自动刷新
                }
            },200);
        }
    }

    private void initLoadMoreScroll() {
        footerView=new FooterView(getContext());
        //第三方控件，监听listview滑动最后，加载数据
        Mugen.with(listView, new MugenCallbacks() {
            @Override//listview滚动到底部，讲触发此方法
            public void onLoadMore() {
                //执行上拉加载数据的业务处理
                Log.i("ms","进入了上拉刷新");
                presenter.loadMore();
            }

            // 是否正在加载中
            // 其内部将用此方法来判断是否触发onLoadMore
            @Override
            public boolean isLoading() {
                return listView.getFooterViewsCount()>0&&footerView.isLoading();
            }

            // 是否已加载完成所有数据
            // 其内部将用此方法来判断是否触发onLoadMore
            @Override
            public boolean hasLoadedAllItems() {
                return listView.getFooterViewsCount()>0&&footerView.isComplete();
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    //初始化下拉刷新，这是第三方的使用
    private void initPullToRefresh() {
        // 使用当前对象做为key，来记录上一次的刷新时间,如果两次下拉太近，将不会触发新刷新
        ptrFrameLayout.setLastUpdateTimeRelateObject(this);
        // 关闭header所用时长
        ptrFrameLayout.setDurationToCloseHeader(1500);
        // 下拉刷新监听处理
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            // 当你"下拉时",将触发此方法
            @Override public void onRefreshBegin(PtrFrameLayout frame) {
                // 去做数据的加载，做具体的业务
                // 也就是说，你要抛开视图，到后台线程去做你的业务处理(数据刷新加载)
                presenter.refresh();//调用刷新方法，异步处理。
            }
        });
        // 以下代码（只是修改了header样式）
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.initWithString("I LIKE " + getLanguage().getName() );
        header.setPadding(0, 60, 0, 60);
        // 修改Ptr的HeaderView效果
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
        ptrFrameLayout.setBackgroundResource(R.color.colorRefresh);
    }

    // 下拉刷新视图实现----------------------------------------
    @Override
    public void showContentView() {
        ptrFrameLayout.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView(String errorMsg) {
        ptrFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyView() {
        ptrFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String msg) {
        activityUtils.showToast(msg);
    }

    //停止刷新，
    @Override
    public void stopRefresh() {
        if(ptrFrameLayout!=null)
        ptrFrameLayout.refreshComplete();//隐藏刷新的动画
    }

    // 刷新数据
    // 将后台线程更新加载到的数据，刷新显示到视图(listview)上来显示给用户看
    @Override
    public void refreshData(List<Repo> data) {
        adapter.clear();//清理适配器
        adapter.addAll(data);//添加数据
    }

    //上拉加载更多视图实现-----------------------------------------------
    @Override//显示listview的foot加载更多
    public void showLoadMoreLoading() {
        if(listView.getFooterViewsCount()==0){
            listView.addFooterView(footerView);//给listview添加foot
        }
        footerView.showLoading();//显示进度条
    }

    @Override
    public void hideLoadMoreLoading() {
        listView.removeFooterView(footerView);//移除listview的foot
    }

    @Override//显示错误信息
    public void showLoadMoreError(String erroeMsg) {
        if (listView.getFooterViewsCount()==0){
            listView.addFooterView(footerView);
        }
        footerView.showError(erroeMsg);
    }

    @Override//刷新上拉加载的数据
    public void addMoreData(List<Repo> datas) {
        adapter.addAll(datas);
    }
}
