package com.linzongfu.forgotgankio.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.linzongfu.forgotgankio.R;
import com.linzongfu.forgotgankio.adapter.ArticleAdapter;
import com.linzongfu.forgotgankio.bean.DataByCategory;
import com.linzongfu.forgotgankio.network.Network;
import com.linzongfu.forgotgankio.presenter.MainPresenter;
import com.linzongfu.forgotgankio.presenter.MainPresenterImpl;
import com.linzongfu.forgotgankio.util.LoadingDialog;
import com.linzongfu.forgotgankio.view.MainView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {
    public static final String TAG = "MainActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_article)
    RecyclerView rvArticle;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private LoadingDialog loadingDialog;

    private static final int defautlSize = 20;
    private static final int defaultPage = 1;

    private String curCategory;
    private int curPage = 1;



    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);

        loadingDialog = new LoadingDialog(this);
        initRecyclerView();

        presenter = new MainPresenterImpl();
        presenter.attachView(this);

        presenter.loadData("all", defaultPage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Snackbar.make(rvArticle, "点击了主题", Snackbar.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_all:
                updateTitleAndArticle("all");
                break;
            case R.id.nav_android:
                updateTitleAndArticle("Android");
                break;
            case R.id.nav_ios:
                updateTitleAndArticle("iOS");
                break;
            case R.id.nav_relax:
                updateTitleAndArticle("休息视频");
                break;
            case R.id.nav_welfare:
                updateTitleAndArticle("福利");
                break;
            case R.id.nav_expand:
                updateTitleAndArticle("拓展资源");
                break;
            case R.id.nav_front:
                updateTitleAndArticle("前端");
                break;
            case R.id.nav_recommend:
                updateTitleAndArticle("瞎推荐");
                break;
            case R.id.nav_app:
                updateTitleAndArticle("App");
                break;
            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void updateTitleAndArticle(String category) {
        updateTitle(category);
        presenter.loadData(category, defaultPage);
    }

    @Override
    public void updateTitle(String category) {
        toolbar.setTitle(category);
    }

    @Override
    public void initRecyclerView() {
        ArticleAdapter adapter = new ArticleAdapter();
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                presenter.pressArticle(position);
            }
        });
        rvArticle.setLayoutManager(new LinearLayoutManager(this));
        rvArticle.setAdapter(adapter);
    }

    @Override
    public void showLoadingDialog() {
        loadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        loadingDialog.dismiss();
    }

    @Override
    public void showData(List<DataByCategory.ResultsBean> list) {
        ArticleAdapter adapter = (ArticleAdapter) rvArticle.getAdapter();
        adapter.setNewData(list);
    }

    @Override
    public void showLoadError() {
        Toast.makeText(MainActivity.this, "加载出错，请检查网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
