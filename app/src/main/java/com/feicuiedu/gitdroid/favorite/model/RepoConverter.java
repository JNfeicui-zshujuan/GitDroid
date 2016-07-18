package com.feicuiedu.gitdroid.favorite.model;

import android.support.annotation.NonNull;

import com.feicuiedu.gitdroid.favorite.LocalRepo;
import com.feicuiedu.gitdroid.hotrepositor.Repo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengshujuan on 2016/7/8.
 */
public class RepoConverter  {
private RepoConverter(){}
  public static @NonNull
    LocalRepo convert(@NonNull Repo repo){
      LocalRepo localRepo=new LocalRepo();
      localRepo.setAvatatr(repo.getOwner().getAvatar());
    localRepo.setDescription(repo.getDescription());
    localRepo.setForkCount(repo.getForksCount());
    localRepo.setFullName(repo.getFullName());
    localRepo.setId(repo.getId());
    localRepo.setName(repo.getName());
    localRepo.setStarCount(repo.getStargazerCount());
    return localRepo;
  }
public static @NonNull
  List<LocalRepo> convertAll(List<Repo> repos){
  ArrayList<LocalRepo> localRepos=new ArrayList<>();
   for (Repo repo:repos){
    localRepos.add(convert(repo));
  }

  return localRepos;
}




}

