package com.feicuiedu.gitdroid.favorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.common.ActivityUtils;
import com.feicuiedu.gitdroid.favorite.model.LocalRepoDao;
import com.feicuiedu.gitdroid.favorite.model.RepoGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhengshujuan on 2016/7/8.
 */
public class FavoriteFrament extends Fragment implements PopupMenu.OnMenuItemClickListener {
    @Bind(R.id.tvGroupType)
    TextView tvGroupType;
    @Bind(R.id.listView)
    ListView listView;

    private Menu menu;
    private RepoGroupDao repoGroupDao;//仓库类别dao
    private LocalRepoDao localRepoDao;//本地仓库dao
    private ActivityUtils utils;
    private LocalRepoAdapter adapter;
    private static final String TAG = "FavoriteFrament";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repoGroupDao = new RepoGroupDao(DBHelp.getInstance(getContext()));
        localRepoDao=new LocalRepoDao(DBHelp.getInstance(getContext())) ;
        //DBHelp的单利模式
        utils = new ActivityUtils(this);
    }
    private LocalRepo currentLocalRepo;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.listView){
            //得到menu的位置
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo= (AdapterView.AdapterContextMenuInfo) menuInfo;
            int position=adapterContextMenuInfo.position;
            //得到当前操作的本地仓库
            currentLocalRepo=adapter.getItem(position);
            MenuInflater menuInflater=getActivity().getMenuInflater();
            menuInflater.inflate(R.menu.menu_context_favorite,menu);
            SubMenu subMenu=menu.findItem(R.id.sub_menu_move).getSubMenu();
            //从本地数据库拿出所有分类
            List<RepoGroup> localGroups=repoGroupDao.queryForAll();
            for (RepoGroup repoGroup:localGroups){
                subMenu.add(R.id.menu_group_move,repoGroup.getId(),Menu.NONE,repoGroup.getName());
            }
        }
    }
    @Override public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete) {
            localRepoDao.delete(currentLocalRepo);
            resetData();
            return true;
        }
        int groupId = item.getGroupId();
        // 在移动至里 (将当前操作的仓库的 类别进行了更改, reset)
        if (groupId == R.id.menu_group_move) {
            if (id == R.id.repo_group_no) { // 未分类
                currentLocalRepo.setRepoGroup(null);
            } else {
                currentLocalRepo.setRepoGroup(repoGroupDao.queryForId(id));
            }
            localRepoDao.createOrUpdata(currentLocalRepo);
            resetData();
            return true;
        }
        return super.onContextItemSelected(item);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        adapter = new LocalRepoAdapter();
        Log.d(TAG, "onViewCreated: "+adapter);
        listView.setAdapter(adapter);
        adapter.setDatas(localRepoDao.queryForAll());
        //进入时默认显示本地仓库
        registerForContextMenu(listView);
    }
    @OnClick(R.id.btnFilter)
    public void showPopMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        //默认项
        popupMenu.inflate(R.menu.menu_popup_repo_groups);
        //拿到menu对象(上面默认只有全部和为分类)
        menu = popupMenu.getMenu();
        //拿到本地数据库内的所有类别数据
        List<RepoGroup> repoGroups = repoGroupDao.queryForAll();
        for (RepoGroup repoGroup : repoGroups) {
            menu.add(Menu.NONE, repoGroup.getId(), Menu.NONE, repoGroup.getName());
        }
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(this);
    }
    private int repoGroupID;
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        tvGroupType.setText(item.getTitle());
        repoGroupID = item.getItemId();
        resetData();
        return true;
    }

    private void resetData() {
        // ListView控件 ,上面的数据要重置更新刷新
        switch (repoGroupID) {
            case R.id.repo_group_all:
                adapter.setDatas(localRepoDao.queryForAll());
                break;
            case R.id.repo_group_no:
                adapter.setDatas(localRepoDao.queryForNoGroup());
                break;
            default:
                adapter.setDatas(localRepoDao.queryForGroupId(repoGroupID));
                break;
        }
    }
}
