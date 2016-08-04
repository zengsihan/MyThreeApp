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
import com.zsh.xuexi.mythreeapp.entity.LocalRepo;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zsh on 2016/8/4.
 */
public class FavoriteAdapter extends BaseAdapter {
    private final ArrayList<LocalRepo> datas;

    public FavoriteAdapter() {
        datas = new ArrayList<LocalRepo>();
    }

    public void setData(Collection<LocalRepo> repos) {
        datas.clear();
        datas.addAll(repos);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public LocalRepo getItem(int position) {
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
        LocalRepo localRepo=getItem(position);
        vh.tvRepoName.setText(localRepo.getFullName());
        vh.tvRepoInfo.setText(localRepo.getDescription());
        vh.tvRepoStars.setText(String.format("starts: %d",localRepo.getStartCount()));
        ImageLoader.getInstance().displayImage(localRepo.getAvatar(),vh.ivIcon);

        return view;
    }

    static class ViewHolder {
        @Bind(R.id.ivIcon) ImageView ivIcon;
        @Bind(R.id.tvRepoName) TextView tvRepoName;
        @Bind(R.id.tvRepoInfo) TextView tvRepoInfo;
        @Bind(R.id.tvRepoStars) TextView tvRepoStars;
        @Bind(R.id.layoutTexts) LinearLayout layoutTexts;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
