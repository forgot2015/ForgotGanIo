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

import com.linzongfu.forgotgankio.R;
import com.linzongfu.forgotgankio.adapter.ArticleAdapter;
import com.linzongfu.forgotgankio.bean.DataByCategory;
import com.linzongfu.forgotgankio.network.Network;

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


    private static final int defautlSize = 20;
    private static final int defaultPage = 1;

    private String curCategory;
    private int curPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navView.setNavigationItemSelectedListener(this);
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
            Snackbar.make(item.getActionView(), "点击了主题", Snackbar.LENGTH_SHORT).show();
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
        rvArticle.setLayoutManager(new LinearLayoutManager(this));
        rvArticle.setAdapter(adapter);
    }

    public void loadData(String category, int page) {
        Network.getDataByCategoryApi()
                .getDataByCategory(category, defautlSize, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataByCategory>() {
                    @Override
                    public void accept(DataByCategory dataByCategory) throws Exception {
                        Log.e(TAG, dataByCategory.toString());
                        if (dataByCategory.getResults() != null) {
                            ArticleAdapter adapter = (ArticleAdapter) rvArticle.getAdapter();
                            adapter.setNewData(dataByCategory.getResults());
                        }
                    }
                });
    }

    public void prePage(String curCategory, int curPage) {

    }

    public void nextPage(String curCategory, int curPage) {
    }
}
