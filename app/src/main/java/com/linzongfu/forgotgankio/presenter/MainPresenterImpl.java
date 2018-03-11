package com.linzongfu.forgotgankio.presenter;

import com.linzongfu.forgotgankio.activity.ArticleActivity;
import com.linzongfu.forgotgankio.activity.MainActivity;
import com.linzongfu.forgotgankio.bean.DataByCategory;
import com.linzongfu.forgotgankio.network.Network;
import com.linzongfu.forgotgankio.view.MainView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by forgot on 2018/3/11.
 * Email: forgot2015@gmail.com
 */

public class MainPresenterImpl implements MainPresenter {
    private CompositeDisposable compositeDisposable;

    private MainView view;

    private static final int DEFAULT_SIZE = 20;
    private static final int DEFAULT_PAGE = 1;

    private List<DataByCategory.ResultsBean> resultsBeans;

    public MainPresenterImpl() {
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(MainView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
        compositeDisposable.clear();
    }

    @Override
    public void loadData(String category, int page) {
        view.showLoadingDialog();
        compositeDisposable.add(Network.getDataByCategoryApi()
                .getDataByCategory(category, DEFAULT_SIZE, page)
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DataByCategory>() {
                    @Override
                    public void accept(DataByCategory dataByCategory) throws Exception {
                        view.hideLoadingDialog();
                        if (dataByCategory.getResults() != null) {
                            resultsBeans = dataByCategory.getResults();
                            view.showData(dataByCategory.getResults());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.hideLoadingDialog();
                        view.showLoadError();
                    }
                })
        );
    }

    @Override
    public void loadPrePage(String curCategory, int curPage) {

    }

    @Override
    public void loadNextPage(String curCategory, int curPage) {

    }

    @Override
    public void pressArticle(int position) {
        DataByCategory.ResultsBean bean = resultsBeans.get(position);
        ArticleActivity.actionStart(view.getContext(), bean.getUrl(), bean.getDesc());
    }
}
