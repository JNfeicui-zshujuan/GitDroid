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
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    FooterView footerView;

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
                        hideLoadMore();
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

    private void loadData() {
        final int size = new Random().nextInt(15);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final ArrayList<String> loadDatas = new ArrayList<String>();
                for (int i = 0; i < size; i++) {
                    loadDatas.add("我是第" + (++count) + "条数据");
                }
                asnycLoadDatad(size, loadDatas);
            }
        }).start();

        //通知Adapter数据已更新
        adapter.notifyDataSetChanged();
//        //刷新完成时,停止刷新
//        stopRefresh();
    }

    private static int count = 0;

    public void asnycLoadDatad(final int size, final ArrayList<String> datas) {
        ptrfram.post(new Runnable() {
            @Override
            public void run() {
                //模拟空数据是的视图
                if (size == 0) {
                    showEmptyView();
                } else if (size == 1) {
                    showErroView();
                } else {
                    showContentView();
                    //视图进行数据刷新
                    refreshData(datas);
                }
                //停止结束这次下拉刷新
                stopRefresh();
            }
        });

    }

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
        listView.setAdapter(adapter);
        //加载刷新数据
        ptrfram.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadData();
            }
        });
        footerView = new FooterView(getContext());
        //上拉加载更多(listview滑动到最后的位置了,就可以loadmore)
        Mugen.with(listView, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                Toast.makeText(getActivity(), "loadMore", Toast.LENGTH_SHORT).show();
                loadMore();
            }

            //是否正在加载，避免重复加载
            @Override
            public boolean isLoading() {
                return listView.getFooterViewsCount() > 0 && footerView.isLoading();
            }

            //是否所有数据都加载完
            @Override
            public boolean hasLoadedAllItems() {
                return false;
            }
        }).start();
    }

    @OnClick({R.id.emptyView, R.id.errorView})
       public void autoRefresh() {
        ptrfram.autoRefresh();
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
        adapter.addAll(datas);
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
    //
    @Override
    public void viewLoading() {

    }

    //加载错误
    @Override
    public void loadViewErro() {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showError();
    }

    //加载完成
    @Override
    public void loadMoreEnd() {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showComplete();
    }

    //添加更多数据
    @Override
    public void addMoreData(List<String> datas) {
        adapter.addAll(datas);
    }

    //隐藏加载更多
    @Override
    public void hideLoadMore() {
        listView.removeFooterView(footerView);
    }

    //正在加载更多
    @Override
    public void showLoadMoreLoading() {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showLoading();
    }

}
