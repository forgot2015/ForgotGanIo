package com.linzongfu.forgotgankio.presenter;

/**
 * Created by forgot on 2018/3/11.
 * Email: forgot2015@gmail.com
 */

public interface BaseMvpPresenter<V> {
    void attachView(V view);

    void detachView();

}
