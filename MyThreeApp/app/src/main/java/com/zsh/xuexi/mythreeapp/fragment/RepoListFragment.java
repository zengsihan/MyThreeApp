package com.zsh.xuexi.mythreeapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zsh.xuexi.mythreeapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zsh on 2016/7/27.
 */
public class RepoListFragment extends Fragment {
    @Bind(R.id.lvRepos) ListView listView;
    private ArrayAdapter<String> adapter;

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
//        ButterKnife.bind(this, view);
        String[] datas={"1","2","3","4","5","6"};
        adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,datas);
        listView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
