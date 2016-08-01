package com.zsh.xuexi.mythreeapp.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zsh on 2016/8/1.
 */
public class RepoResult {
    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    @SerializedName("items")
    private List<Repo> repoList;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Repo> getRepoList() {
        return repoList;
    }

    public void setRepoList(List<Repo> repoList) {
        this.repoList = repoList;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }
}
