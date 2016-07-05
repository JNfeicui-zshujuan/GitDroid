package com.feicuiedu.gitdroid;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.feicuiedu.gitdroid.home.HotRepoFragment;

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
private MenuItem menuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //当内容改变是调用该方法
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
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
//        hotRepoFragment = new HotRepoFragment();
//        repleceFragment(hotRepoFragment);
        //替换不同的fragment
        //创建fragment的对象
        HotRepoFragment htrf = new HotRepoFragment();
        //获取系统自带的fragment管理器
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        //替换布局
        transaction.replace(R.id.container, htrf);
        //提交事务
        transaction.commit();
    }

//    public void repleceFragment(Fragment fragment) {
//
//    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //true将菜单变为checked状态
        switch (item.getItemId()) {
            case R.id.github_hot_repo:
                Toast.makeText(MainActivity.this, "github-hot-repo", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tips_daily:
                Toast.makeText(MainActivity.this, "tips_daily", Toast.LENGTH_SHORT).show();
                break;
            case R.id.arsenal_my_repo:
                Toast.makeText(MainActivity.this, "arsenal_my_repo", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tips_share:
                //按下分享时,关闭左侧侧滑菜单
                Toast.makeText(MainActivity.this, "share", Toast.LENGTH_SHORT).show();
                break;

        }
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
