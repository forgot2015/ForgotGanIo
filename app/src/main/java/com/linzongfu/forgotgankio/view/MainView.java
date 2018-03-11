package com.linzongfu.forgotgankio.view;

import com.linzongfu.forgotgankio.bean.DataByCategory;

import java.util.List;

/**
 * Created by forgot on 2018/3/11.
 * Email: forgot2015@gmail.com
 */

public interface MainView extends BaseMvpView {
    void updateTitle(String category);
    void initRecyclerView();
    void showLoadingDialog();
    void hideLoadingDialog();
    void showData(List<DataByCategory.ResultsBean> list);
    void showLoadError();
}
