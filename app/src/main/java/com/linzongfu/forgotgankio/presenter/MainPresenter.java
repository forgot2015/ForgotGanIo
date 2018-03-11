package com.linzongfu.forgotgankio.presenter;

import com.linzongfu.forgotgankio.view.MainView;

/**
 * Created by forgot on 2018/3/11.
 * Email: forgot2015@gmail.com
 */

public interface MainPresenter extends BaseMvpPresenter<MainView> {
    /**
     * 加载数据
     */
    void loadData(String category, int page);

    /**
     * 加载上一页数据
     */
    void loadPrePage(String curCategory, int curPage);

    /**
     * 加载下一页数据
     */
    void loadNextPage(String curCategory, int curPage);

    void pressArticle(int position);
}
