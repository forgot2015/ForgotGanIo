package com.linzongfu.forgotgankio.network;

/**
 * Created by forgot on 2018/3/11.
 * Email: forgot2015@gmail.com
 */

public class UrlConfig {
    /**
     * 一般网络请求的ip
     */
    public static final String HOST_URL = "http://gank.io/";

    /**
     * http://gank.io/api/data/数据类型/请求个数/第几页
     */
    public static final String GET_DATA_BY_CATEGORY = "/api/data/{category}/{pageSize}/{pageIndex}";

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     */
    public static final String GET_DATA_BY_DAY = "/api/day/{year}/{month}/{day}";
}
