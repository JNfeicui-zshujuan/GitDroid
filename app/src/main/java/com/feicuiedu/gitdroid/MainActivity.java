package com.feicuiedu.gitdroid;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.feicuiedu.gitdroid.common.ActivityUtils;
import com.feicuiedu.gitdroid.favorite.FavoriteFrament;
import com.feicuiedu.gitdroid.reopList.HotRepoFragment;
import com.feicuiedu.gitdroid.httpclient.CurrentUser;
import com.feicuiedu.gitdroid.login.LoginActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.navigationView)
    NavigationView navigationView;
    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private HotRepoFragment hotRepoFragment;
    private FavoriteFrament favoriteFrament;
    private MenuItem menuItem;
    private ImageView ivIcon;
    private ActivityUtils utils;
    private Button btnLogin;
    private String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //当内容改变时调用该方法
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        utils=new ActivityUtils(this);
        ivIcon=(ImageView) ButterKnife.findById(navigationView.getHeaderView(0),R.id.ivIcon);
        //登录
        btnLogin=(Button) ButterKnife.findById(navigationView.getHeaderView(0),R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utils.startActivity(LoginActivity.class);
            }
        });
        //设置监听
        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        // 设置Toolbar左上角切换侧滑菜单的按钮
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        // 默认第一个menu项为选中(最热门)
        menuItem = navigationView.getMenu().findItem(R.id.github_hot_repo);
        menuItem.setChecked(true);
        hotRepoFragment= new HotRepoFragment();
        replaceFragment(hotRepoFragment);

    }
    //替换不同的fragment
    private void replaceFragment(Fragment fragment) {
        //获取系统自带的fragment管理器
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        //替换布局
        transaction.replace(R.id.container, fragment);
        //提交事务
        transaction.commit();
    }

    //视图可见时,设置侧滑菜单中的登录按钮的状态
    @Override
    protected void onStart() {
        super.onStart();
        //还没有授权登录
        if (CurrentUser.isEmpty()){
            btnLogin.setText(R.string.login_github);
            return;
        }
        //已经授权登录
        btnLogin.setText("切换账号");
        getSupportActionBar().setTitle(CurrentUser.getUser().getName());
        //设置用户头像
        String photoUrl=CurrentUser.getUser().getAvatar();
        ImageLoader.getInstance().displayImage(photoUrl,ivIcon);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //true将菜单变为checked状态
        switch (item.getItemId()) {
            case R.id.github_hot_repo:
              if (!hotRepoFragment.isAdded())
                   replaceFragment(hotRepoFragment);

                break;
            case R.id.tips_daily:
                Toast.makeText(MainActivity.this, "tips_daily", Toast.LENGTH_SHORT).show();
                break;
            case R.id.arsenal_my_repo:
               if (favoriteFrament==null) favoriteFrament=new FavoriteFrament();
                if (!favoriteFrament.isAdded()){
                    replaceFragment(favoriteFrament);
                }
                break;
            case R.id.tips_share:
                //按下分享时,关闭左侧侧滑菜单
                Toast.makeText(MainActivity.this, "share", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {

        //如果NavigationView是开的-->关闭
        //是关的-->退出Activity
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            //否则直接执行onBackPressed方法
            super.onBackPressed();
        }
    }
}
