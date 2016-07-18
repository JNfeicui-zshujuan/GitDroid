package com.feicuiedu.gitdroid.favorite.model;

import com.feicuiedu.gitdroid.favorite.DBHelp;
import com.feicuiedu.gitdroid.favorite.LocalRepo;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by zhengshujuan on 2016/7/8.
 * 对本地仓库的操作
 */
public class LocalRepoDao {
    private Dao<LocalRepo,Long> dao;
    //构造参数传入数据
    public LocalRepoDao(DBHelp deHelp){
        try {
            dao=deHelp.getDao(LocalRepo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//添加或更新本地仓库
    public void createOrUpdata(LocalRepo localRepo){
        try {
            dao.createOrUpdate(localRepo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createOrUpdata(List<LocalRepo> localRepos){
        for (LocalRepo localRepo:localRepos){
            createOrUpdata(localRepo);
        }
    }
    //查询本地所有仓库(全部的,未分类的,图像处理的....)
public List<LocalRepo> queryForAll(){
    try {
        return dao.queryForAll();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
    //查询本地未分类仓库(全部的,未分类的,图像处理的)
    public List<LocalRepo> queryForNoGroup(){
        try {
          return   dao.queryBuilder().where().isNull(LocalRepo.COLUMN_GROUP_ID).query();
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
    }
    //查询本地指定类别仓库
    public List<LocalRepo> queryForGroupId(int groupId){
        try {
            return dao.queryForEq(LocalRepo.COLUMN_GROUP_ID,groupId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void delete(LocalRepo localRepo){
        try {
            dao.delete(localRepo);
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }
    }
}
