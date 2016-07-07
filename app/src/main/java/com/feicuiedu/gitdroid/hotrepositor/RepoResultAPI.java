package com.feicuiedu.gitdroid.hotrepositor;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zhengshujuan on 2016/7/6.
 */
public class RepoResultAPI {
    //搜索仓库结果
    @SerializedName("total_count")
    private int totalCount;

    public List<Repo> getRepoList() {
        return repoList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    //仓库列表
    @SerializedName("items")
    private List<Repo> repoList;
    @SerializedName("incomplete_results")
    private boolean incompleteResults;

}
