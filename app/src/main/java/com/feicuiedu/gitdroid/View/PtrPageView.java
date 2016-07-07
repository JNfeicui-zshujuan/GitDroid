package com.feicuiedu.gitdroid.View;

import com.feicuiedu.gitdroid.hotrepositor.Repo;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by l on 2016/7/2.
 */
public interface PtrPageView extends PtrView<List<Repo>>,LoadMoreView<List<Repo>>, MvpView {
}
