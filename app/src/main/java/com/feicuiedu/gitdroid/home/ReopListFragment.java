package com.feicuiedu.gitdroid.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.View.PtrPageView;
import com.feicuiedu.gitdroid.hotrepositor.Language;
import com.feicuiedu.gitdroid.hotrepositor.Repo;
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
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by zhengshujuan on 2016/7/1.
 * mvp库的使用: 添加mosby依赖:
 * compile 'com.hannesdorfmann.mosby:mvp:2.0.1'
 * 1 view继承 mvpview (定义的接口,)
 * 2 presenter继承mvpPresenter (mvpnullObjectbasepresenter)<view></>
 * 3 fragement 继承 mvpfragment <view,presenter></>
 */
public class ReopListFragment extends MvpFragment<PtrPageView, ReopListPresenter> implements PtrPageView {

  //加载列表
    @Bind(R.id.lvRepos)
    ListView listView;
    //下拉刷新de
    @Bind(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrFragment;
    //加载空白视图
    @Bind(R.id.emptyView)
    TextView emptyView;
    //加载错误视图
    @Bind(R.id.errorView)
    TextView errorView;
    //"加载刷新错误"
   // @Bind(R.string.refresh_error) String refresherror;
    private LanguageRepoAdapter adapter;
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

    private Language getLanguage() {
        return (Language) getArguments().getSerializable(KEY_);
    }

    //重写Mosby库父类MvpFragment的方法,返回当前视图使用的Presenter对象
    @Override
    public ReopListPresenter createPresenter() {
        return new ReopListPresenter(getLanguage());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapter = new LanguageRepoAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Repo repo = adapter.getItem(position);

            }
        });
        //初始下拉刷新
        initPullToRefresh();
        //初始上拉加载
        initLoadMoreScroll();
        if (adapter.getCount() == 0) {
            ptrFragment.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ptrFragment.autoRefresh();
                }
            }, 200);
        }
    }

    //初始化下拉刷新
    private void initPullToRefresh() {
        //使用本对象作为key,记录上一次刷新时间,如果两次下拉时间太近,则不会触发刷新方法
        ptrFragment.setLastUpdateTimeRelateObject(this);
        ptrFragment.setBackgroundResource(R.color.colorRefresh);
        //关闭header多耗时长
        ptrFragment.setDurationToCloseHeader(1500);
        //Header效果
        StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0, 60, 0, 60);
        header.initWithString("H H H");
        ptrFragment.setHeaderView(header);
        ptrFragment.addPtrUIHandler(header);
        //下拉刷新处理
        ptrFragment.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //执行下拉刷新数据业务
                getPresenter().refresh();
            }
        });
    }

    //初始化上拉加载
    public void initLoadMoreScroll() {
        footerView = new FooterView(getContext());
        //加载失败时,用户点击FooterView再次触发加载
        footerView.setErrorClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.LoadMore();
            }
        });
        //使用一个微型库Mugen来处理滚动监听
        Mugen.with(listView, new MugenCallbacks() {
            //listVIew滚动到底部.触发加载更多
            @Override
            public void onLoadMore() {
                //执行上拉加载数据业务
                getPresenter().LoadMore();
            }

            //是否正在加载,避免重复加载
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
    public void addMoreData(List<Repo> datas) {
        if (adapter==null)return;
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
    public void showErroView(String string) {
        ptrFragment.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
     //   errorView.setText(String.format(refresherror,string));
    }


    @Override
    public void showEmptyView() {
        ptrFragment.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void refreshData(List<Repo> datas) {
        adapter.clear();
        if (datas == null) {
            return;
        }
        adapter.addAll(datas);
    }

    @Override
    public void stopRefresh() {
        ptrFragment.refreshComplete();
    }

    @Override
    public void showMessage(String string) {

    }

    //获取ReopListFragment的实例,也就是viewpager的每一个item
    public static ReopListFragment getInctance(Language language) {
        ReopListFragment rf = new ReopListFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_, language);
        rf.setArguments(args);
        return rf;
    }


}
