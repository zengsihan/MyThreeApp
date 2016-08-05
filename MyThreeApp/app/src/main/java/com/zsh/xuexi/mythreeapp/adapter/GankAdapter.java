package com.zsh.xuexi.mythreeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zsh.xuexi.mythreeapp.R;
import com.zsh.xuexi.mythreeapp.entity.GankItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zsh on 2016/8/5.
 */
public class GankAdapter extends BaseAdapter {
    private final ArrayList<GankItem> datas;

    public GankAdapter() {
        datas = new ArrayList<GankItem>();
    }

    public void setDatas(List<GankItem> gankItems) {
        datas.clear();
        datas.addAll(gankItems);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public GankItem getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_gank, parent, false);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder vh= (ViewHolder) view.getTag();
        GankItem gankItem=getItem(position);
        vh.gankItem.setText(gankItem.getDesc());
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.gank_item) TextView gankItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
