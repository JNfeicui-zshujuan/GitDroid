package com.feicuiedu.gitdroid.favorite;

import com.feicuiedu.gitdroid.favorite.model.RepoGroup;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by zhengshujuan on 2016/7/8.
 * RepoGroup的相关操作
 */
public class RepoGroupDao {

    private  Dao<RepoGroup, Long> dao;

    public RepoGroupDao(DBHelp dbHelp){
        try {
            dao = dbHelp.getDao(RepoGroup.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //查询所有的数据仓库
    public List<RepoGroup> queryForAll(){
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
        throw new RuntimeException(e);
        }
    }
    public RepoGroup queryForId(long id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //添加和更新数据
    public void createOrUpdate(RepoGroup repoGroup){
        try {
            dao.createOrUpdate(repoGroup);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //添加和更新数据
    public void createOrUpdate(List<RepoGroup> repoGroups){
        for (RepoGroup repoGroup:repoGroups){
            createOrUpdate(repoGroup);
        }
    }
}
