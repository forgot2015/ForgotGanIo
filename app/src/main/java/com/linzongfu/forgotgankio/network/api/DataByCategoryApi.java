package com.linzongfu.forgotgankio.network.api;

import com.linzongfu.forgotgankio.bean.DataByCategory;
import com.linzongfu.forgotgankio.network.UrlConfig;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by forgot on 2018/3/11.
 * Email: forgot2015@gmail.com
 */

public interface DataByCategoryApi {
    /**
     * @param category category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * @param pageSize
     * @param pageIndex
     * @return
     */
    @GET(UrlConfig.GET_DATA_BY_CATEGORY)
    Observable<DataByCategory> getDataByCategory(@Path("category") String category,
                                                 @Path("pageSize") int pageSize,
                                                 @Path("pageIndex") int pageIndex);
}
