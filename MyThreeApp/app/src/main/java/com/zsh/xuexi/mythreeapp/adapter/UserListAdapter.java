package com.zsh.xuexi.mythreeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zsh.xuexi.mythreeapp.R;
import com.zsh.xuexi.mythreeapp.entity.User;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zsh on 2016/8/2.
 */
public class UserListAdapter extends BaseAdapter {
    private final ArrayList<User> datas;

    public UserListAdapter() {
        datas = new ArrayList<User>();
    }

    public void addAll(Collection<User> users) {
        datas.addAll(users);
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
    public User getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_user, parent, false);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder vh= (ViewHolder) view.getTag();
        User user=getItem(position);
        vh.tvLoginName.setText(user.getLogin());
        ImageLoader.getInstance().displayImage(user.getAvatar(), vh.ivIcon);
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.ivIcon) ImageView ivIcon;
        @Bind(R.id.tvLoginName) TextView tvLoginName;
        @Bind(R.id.imageView) ImageView imageView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
