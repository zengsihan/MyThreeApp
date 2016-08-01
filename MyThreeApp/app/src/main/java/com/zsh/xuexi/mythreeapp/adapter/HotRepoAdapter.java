package com.zsh.xuexi.mythreeapp.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zsh.xuexi.mythreeapp.fragment.RepoListFragment;
import com.zsh.xuexi.mythreeapp.http.Language;

import java.util.List;

/**
 * Created by zsh on 2016/7/27.
 */
public class HotRepoAdapter extends FragmentPagerAdapter {
    private List<Language> languages;

    public HotRepoAdapter(FragmentManager fm, Context cont) {
        super(fm);
        languages=Language.getDefaultLanguages(cont);
    }

    @Override
    public Fragment getItem(int position) {
        return RepoListFragment.getInstance(languages.get(position));
    }

    @Override
    public int getCount() {
        return languages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return languages.get(position).getName();
    }
}
