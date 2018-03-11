package com.linzongfu.forgotgankio.activity;

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

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.linzongfu.forgotgankio.R;
import com.linzongfu.forgotgankio.adapter.ArticleAdapter;
import com.linzongfu.forgotgankio.bean.DataByCategory;
import com.linzongfu.forgotgankio.network.Network;
import com.linzongfu.forgotgankio.util.LoadingDialog;

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
        implements NavigationView.OnNavigationItemSelectedListener {
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

    private List<DataByCategory.ResultsBean> resultsBeans;

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

        resultsBeans = new ArrayList<>();
        loadingDialog = new LoadingDialog(this);
        initRecyclerView();

        loadData("all", defaultPage);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Snackbar.make(rvArticle, "点击了主题", Snackbar.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_all:
                loadData("all", defaultPage);
                break;
            case R.id.nav_android:
                loadData("Android", defaultPage);
                break;
            case R.id.nav_ios:
                loadData("iOS", defaultPage);
                break;
            case R.id.nav_relax:
                loadData("休息视频", defaultPage);
                break;
            case R.id.nav_welfare:
                loadData("福利", defaultPage);
                break;
            case R.id.nav_expand:
                loadData("拓展资源", defaultPage);
                break;
            case R.id.nav_front:
                loadData("前端", defaultPage);
                break;
            case R.id.nav_recommend:
                loadData("瞎推荐", defaultPage);
                break;
            case R.id.nav_app:
                loadData("App", defaultPage);
                break;
            default:
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void initRecyclerView() {
        ArticleAdapter adapter = new ArticleAdapter();
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                DataByCategory.ResultsBean bean = resultsBeans.get(position);
                ArticleActivity.actionStart(MainActivity.this, bean.getUrl(), bean.getDesc());
            }
        });
        rvArticle.setLayoutManager(new LinearLayoutManager(this));
        rvArticle.setAdapter(adapter);
    }

    public void loadData(String category, int page) {
        toolbar.setTitle(category);
        loadingDialog.show();
        Network.getDataByCategoryApi()
                .getDataByCategory(category, defautlSize, page)
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataByCategory>() {
                    @Override
                    public void accept(DataByCategory dataByCategory) throws Exception {
                        loadingDialog.dismiss();
                        resultsBeans = dataByCategory.getResults();
                        if (resultsBeans != null) {
                            ArticleAdapter adapter = (ArticleAdapter) rvArticle.getAdapter();
                            adapter.setNewData(resultsBeans);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loadingDialog.dismiss();
                        ToastUtils.showShort("加载出错，请检查网络");
                    }
                });
    }

    public void prePage(String curCategory, int curPage) {

    }

    public void nextPage(String curCategory, int curPage) {
    }
}
