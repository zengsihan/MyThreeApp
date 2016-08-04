package com.zsh.xuexi.mythreeapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.zsh.xuexi.mythreeapp.R;
import com.zsh.xuexi.mythreeapp.commons.ActivityUtils;
import com.zsh.xuexi.mythreeapp.db.DbHelper;
import com.zsh.xuexi.mythreeapp.db.LocalRepoDao;
import com.zsh.xuexi.mythreeapp.entity.LocalRepo;
import com.zsh.xuexi.mythreeapp.entity.RepoGroup;
import com.zsh.xuexi.mythreeapp.db.RepoGroupDao;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zsh on 2016/8/3.
 * 本地收藏页面Fragment
 */
public class FavoriteFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {


    @Bind(R.id.tvGroupType) TextView tvGroupType;// 用来显示当前类别的文本
    @Bind(R.id.btnFilter) ImageButton btnFilter;// 用来切换类别的按钮
    @Bind(R.id.listView) ListView listView;

    private ActivityUtils activityUtils;
    private RepoGroupDao repoGroupDao;//仓库类别dao（数据的增删改查）
    private LocalRepoDao localRepoDao;//本地仓库

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils=new ActivityUtils(this);
        repoGroupDao=new RepoGroupDao(DbHelper.getInstance(getContext()));
        localRepoDao=new LocalRepoDao(DbHelper.getInstance(getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activityUtils.showToast("size = " + localRepoDao.queryForAll().size());

    }

    //按下按钮弹出类别菜单
    @OnClick(R.id.btnFilter)
    public void showPopupMenu(View view){
        PopupMenu popupMenu=new PopupMenu(getContext(),view);
        popupMenu.setOnMenuItemClickListener(this);
        //menu项
        popupMenu.inflate(R.menu.menu_popup_repo_groups);
        //拿到menu
        Menu menu=popupMenu.getMenu();
        //拿到数据
        List<RepoGroup> repoGroups=repoGroupDao.queryForAll();
        Log.i("ms","list="+repoGroups.toString());
        for (RepoGroup repoGroup:repoGroups){
            menu.add(Menu.NONE,repoGroup.getId(),Menu.NONE,repoGroup.getName());
        }
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        String title=item.getTitle().toString();
        tvGroupType.setText(title);
        setData(item.getItemId());
        return true;
    }
    private void setData(int groupId){
        switch (groupId){
            case R.id.repo_group_all:
                break;
            case R.id.repo_group_no:
                break;
            default:
                break;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
