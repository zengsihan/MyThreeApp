package com.zsh.xuexi.mythreeapp.presenter;

import android.os.AsyncTask;

import com.zsh.xuexi.mythreeapp.fragment.RepoListFragment;

import java.util.ArrayList;

/**
 * Created by zsh on 2016/7/27.
 */
public class RepoListPresenter {
    private RepoListFragment repoListFragment;

    public RepoListPresenter(RepoListFragment repoListFragment) {
        this.repoListFragment = repoListFragment;
    }
    public void refresh(){
        new RefreshTask().execute();
    }

    class RefreshTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<String> datas=new ArrayList<String>();
            for(int i=0;i<20;i++){
                datas.add("ceshi"+i);
            }
            repoListFragment.stopRefresh();
            repoListFragment.refreshData(datas);
            repoListFragment.showContentView();
        }
    }
}
