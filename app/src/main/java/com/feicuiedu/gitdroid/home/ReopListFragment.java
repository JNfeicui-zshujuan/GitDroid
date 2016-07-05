package com.feicuiedu.gitdroid.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.feicuiedu.gitdroid.View.PtrPageView;
import com.feicuiedu.gitdroid.R;
import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by zhengshujuan on 2016/7/1.
 * mvp库的使用: 添加mosby依赖:
 *     compile 'com.hannesdorfmann.mosby:mvp:2.0.1'
 *  1 view继承 mvpview (定义的接口,)
 *  2 presenter继承mvpPresenter (mvpnullObjectbasepresenter)<view></>
 *  3 fragement 继承 mvpfragment <view,presenter></>
 *
 */
public class ReopListFragment extends MvpFragment<PtrPageView, ReopListPresenter> implements PtrPageView {
    @Bind(R.id.lvRepos)
    ListView listView;
    @Bind(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrFragment;
    @Bind(R.id.emptyView)
    TextView emptyView;
    @Bind(R.id.errorView)
    TextView errorView;
    private ArrayAdapter<String> adapter;
    private static final String KEY_ = "key_language";
    private FooterView footerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_list, container, false);

    }

    @Override
    public ReopListPresenter createPresenter() {
        return new ReopListPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        //下拉刷新
        ptrFragment.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getPresenter().loadData();
            }
        });
        //上拉加载更多(listview滑动到最后位置,就可以loadMore)
        footerView = new FooterView(getContext());
        Mugen.with(listView, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                Toast.makeText(getContext(), "loadMore", Toast.LENGTH_SHORT).show();
                getPresenter().loadMore();
            }

            @Override
            public boolean isLoading() {
                return listView.getFooterViewsCount() > 0 && footerView.isLoading();
            }

            //是否所有数据都已加载
            @Override
            public boolean hasLoadedAllItems() {
                return listView.getFooterViewsCount() > 0 && footerView.isComplete();
            }
        }).start();
    }

    @OnClick({R.id.emptyView, R.id.errorView})
    public void autoRefresh() {
        ptrFragment.autoRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void loadViewErro() {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showError();
    }

    @Override
    public void loadMoreEnd() {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showComplete();
    }

    @Override
    public void addMoreData(List<String> datas) {
        adapter.addAll(datas);
    }

    @Override
    public void hideLoadMore() {
        listView.removeFooterView(footerView);
    }

    @Override
    public void showLoadMoreLoading() {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showLoading();
    }

    //下拉刷新数据
    @Override
    public void showContentView() {
        ptrFragment.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void showErroView() {
        ptrFragment.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmptyView() {
        ptrFragment.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void refreshData(List<String> strings) {
        adapter.clear();
        adapter.addAll(strings);
    }

    @Override
    public void stopRefresh() {
        ptrFragment.refreshComplete();
    }

    @Override
    public void showMessage(String string) {

    }
    //获取ReopListFragment的实例,也就是viewpager的每一个item
    public static ReopListFragment getInctance(String language) {
        ReopListFragment rf = new ReopListFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_, language);
        rf.setArguments(args);
        return rf;
    }


}
