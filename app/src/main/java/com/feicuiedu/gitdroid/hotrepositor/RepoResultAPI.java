package com.feicuiedu.gitdroid.hotrepositor;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zhengshujuan on 2016/7/6.
 * //搜索总仓库结果
 */
public class RepoResultAPI {
    //仓库的总数量
    @SerializedName("total_count")
    private int totalCount;

    public int getTotalCount() {
        return totalCount;
    }

    //仓库列表,具体指一个仓库
    @SerializedName("items")
    private List<Repo> repoList;
    //是否完成搜索结果
    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    //具体到每个仓库的结果
    public List<Repo> getRepoList() {
        return repoList;
    }

}
