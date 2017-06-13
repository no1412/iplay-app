package com.ll.iplay.common;

import com.ll.iplay.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by ll on 2017/5/5.
 */

public class Constants {

    public static String REQUEST_PREFIX = "http://192.168.191.1:8080/iplay/";

    public static final String APP_KEY = "199504110000";

    public static final String USER = "user";
    public static final String CURRENT_CITY_CODE = "currentCityCode";

    public static final String SUCCESS = "1";
    public static final String FAILURE = "0";


    public static final String SEVER_EXCEPTION = "服务器异常!";
    public static final String FORM_WRONG_MSG = "请按要求填写!";
}
