package com.zsh.xuexi.mythreeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zsh.xuexi.mythreeapp.R;
import com.zsh.xuexi.mythreeapp.entity.Repo;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zsh on 2016/8/1.
 */
public class RepoListAdapter extends BaseAdapter {
    private final ArrayList<Repo> datas;

    public RepoListAdapter() {
        datas = new ArrayList<Repo>();
    }

    public void addAll(Collection<Repo> repos) {
        datas.addAll(repos);
        notifyDataSetChanged();
    }

    public void clear() {
        datas.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Repo getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_repo, parent, false);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder vh= (ViewHolder) view.getTag();
        Repo repo=getItem(position);//当前item选的“数据”
        vh.tvRepoName.setText(repo.getFullName());
        vh.tvRepoInfo.setText(repo.getDescription());
        vh.tvRepoStars.setText(repo.getStartCount()+"");
        ImageLoader.getInstance().displayImage(repo.getOwner().getAvatar(),vh.ivIcon);
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.ivIcon) ImageView ivIcon;
        @Bind(R.id.tvRepoName) TextView tvRepoName;
        @Bind(R.id.tvRepoInfo) TextView tvRepoInfo;
        @Bind(R.id.tvRepoStars) TextView tvRepoStars;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
