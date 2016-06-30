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

import com.feicuiedu.gitdroid.R;

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
public class RepoFragment extends Fragment {
    private static final String TAG = "RepoFragment";
    @Bind(R.id.lvRepos)
    ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> datas = new ArrayList<>();
    @Bind(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrfram;
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
                for (int i = 1; i <= size; i++) {
                    datas.add("刷新数据第" + i + "次");
                }
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
        ptrfram.refreshComplete();
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
        adapter.addAll(datas);
        listView.setAdapter(adapter);
        //加载刷新数据
        ptrfram.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadData(20);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        Log.d(TAG, "onDestroy: ..........");
    }
}
