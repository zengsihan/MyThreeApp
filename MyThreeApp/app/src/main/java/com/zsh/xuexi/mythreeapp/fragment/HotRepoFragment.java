package com.zsh.xuexi.mythreeapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zsh.xuexi.mythreeapp.R;
import com.zsh.xuexi.mythreeapp.adapter.HotRepoAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zsh on 2016/7/27.
 * 热门仓库Fragment
 * 里面放着一个ViewPager，在Adapter里面，每一个pager面都是一个Fragment
 */
public class HotRepoFragment extends Fragment {
    @Bind(R.id.tabLayout) TabLayout tabLayout;
    @Bind(R.id.viewPager) ViewPager viewPager;
    private HotRepoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_repo, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        adapter=new HotRepoAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
