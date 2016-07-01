package com.feicuiedu.gitdroid.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.feicuiedu.gitdroid.LoadMoreView;
import com.feicuiedu.gitdroid.PtrView;
import com.feicuiedu.gitdroid.R;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by zhengshujuan on 2016/6/30.
 */
public class RepoFragment extends Fragment implements PtrView<List<String>>, LoadMoreView<List<String>> {
    private static final String TAG = "RepoFragment";
    @Bind(R.id.lvRepos)
    ListView listView;
    @Bind(R.id.emptyView)
    TextView emptyView;
    @Bind(R.id.errorView)
    TextView errorView;
    private ArrayAdapter<String> adapter;
    private List<String> datas = new ArrayList<>();
    @Bind(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrfram;
    FooterView footerView = new FooterView(getContext());

    //上拉加载更多视图层的业务逻辑
    private void loadMore() {
        //显示加载中.....
        showLoadMoreLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                final ArrayList<String> loadDatas = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    loadDatas.add("我是loadMore的第" + i + "条数据");
                }
                ptrfram.post(new Runnable() {
                    @Override
                    public void run() {
                        //将加载到的数据添加到视图上
                        addMoreData(loadDatas);
                        //隐藏加载中
                    }
                });
            }
        }).start();
    }

    //给TabLayout里添加数据
    //获取fragment的实例对象
    public static RepoFragment getInstance(String language) {
        RepoFragment repoFragment = new RepoFragment();
        Bundle args = new Bundle();
        args.putSerializable("key_language", language);
        repoFragment.setArguments(args);
        return repoFragment;
    }

    private void loadData(final int size) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                datas.clear();


            }
        }).start();
        ptrfram.post(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                adapter.addAll(datas);
            }

        });
        //通知Adapter数据已更新
        adapter.notifyDataSetChanged();
        //刷新完成时,停止刷新
        stopRefresh();
    }

    public void asnycLoadDatad() {

    }
    //上拉加载更多视图层实现


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < 10; i++) {
            datas.add("杨殿文乱叨叨" + i + "次");

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_repo_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        //加载系统的listview的布局
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);
        adapter.addAll(datas);
        listView.setAdapter(adapter);
        //加载刷新数据
        ptrfram.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadData(11);
            }
        });
        //上拉加载更多(listview滑动到最后的位置了,就可以loadmore)
        Mugen.with(listView, new MugenCallbacks() {
            @Override
            public void onLoadMore() {


            }

            @Override
            public boolean isLoading() {
                return false;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        Log.d(TAG, "onDestroy: ..........");
    }

    @Override
    public void showContentView() {

        ptrfram.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);


    }

    @Override
    public void showErroView() {
        ptrfram.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);

    }

    @Override
    public void showEmptyView() {
        ptrfram.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);

    }

    @Override
    public void refreshData(List<String> strings) {
        for (int i = 1; i <= 20; i++) {
            datas.add("刷新数据第" + i + "次");
        }
    }

    @Override
    public void stopRefresh() {
        ptrfram.refreshComplete();
    }

    @Override
    public void showMessage(String string) {
        Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();

    }

    //上拉加载数据视图层实现
    @Override
    public void viewLoading() {

    }

    @Override
    public void loadViewErro(String string) {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showError();
    }

    @Override
    public void loadMoreEnd() {

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

}
