package com.zsh.xuexi.mythreeapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.zsh.xuexi.mythreeapp.R;
import com.zsh.xuexi.mythreeapp.adapter.FavoriteAdapter;
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
    private FavoriteAdapter adapter;
    private int currentRepoGroupId;//当前仓库类别
    private LocalRepo currentLocalRepo;//当前操作的仓库（上下文菜单）

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
        adapter=new FavoriteAdapter();
        listView.setAdapter(adapter);
        //默认显示全部仓库
        setData(R.id.repo_group_all);
        //注册上下文菜单
        registerForContextMenu(listView);
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
        tvGroupType.setText(item.getTitle().toString());
        //保存当前选择的类别
        currentRepoGroupId=item.getItemId();
        setData(currentRepoGroupId);
        return true;
    }
    private void setData(int groupId){
        switch (groupId){
            case R.id.repo_group_all:
                adapter.setData(localRepoDao.queryForAll());
                break;
            case R.id.repo_group_no:
                adapter.setData(localRepoDao.queryForNoGroup());
                break;
            default:
                adapter.setData(localRepoDao.queryForGroupId(groupId));
                break;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId()==R.id.listView){
            // 得到当前ListView的ContextMenu，选中时选择的位置
            AdapterView.AdapterContextMenuInfo adapterMenuInfo= (AdapterView.AdapterContextMenuInfo) menuInfo;
            int position=adapterMenuInfo.position;
            currentLocalRepo=adapter.getItem(position);

            // 添加ContextMenu的内容
            MenuInflater menuInflater=getActivity().getMenuInflater();
            menuInflater.inflate(R.menu.menu_context_favorite,menu);
            //拿到子菜单，添加内容
            SubMenu subMenu=menu.findItem(R.id.sub_menu_move).getSubMenu();
            List<RepoGroup> repoGroups=repoGroupDao.queryForAll();
            //到添加到menu_group_move这个组上
            for (RepoGroup repoGroup:repoGroups){
                subMenu.add(R.id.menu_group_move,repoGroup.getId(),Menu.NONE,repoGroup.getName());
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id=item.getItemId();
        //删除
        if(id==R.id.delete){
            //删除当前选择的
            localRepoDao.delete(currentLocalRepo);
            //重置当前选择的分类下的本地仓库列表
            setData(currentRepoGroupId);
            return true;
        }
        //移动至
        int groupId=item.getGroupId();
        if(groupId==R.id.menu_group_move){
            //未分类
            if(id==R.id.repo_group_no){
                //将当前选择的本地仓库类别重置为null（无类别）
                currentLocalRepo.setRepoGroup(null);
            }else{//其他分类 id=1,2,3,4,5,6
                // 得到“其它分类”的类别对象,将当前选择的本地仓库类别重置为当前类别
                currentLocalRepo.setRepoGroup(repoGroupDao.queryForID(id));
            }
            //更新数据库数据
            localRepoDao.createOrUpdate(currentLocalRepo);
            setData(currentRepoGroupId);
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
