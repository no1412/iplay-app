package com.ll.iplay.util;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {

    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    public static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");

    /**
     * get方式提交请求
     * @param address
     * @param callback
     */
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * post方式提交请求
     * @param address
     * @param callback
     */
    public static void sendOkHttpRequestByPost(String address, Map<String, String> params, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : params.keySet()) {
            builder.add(key, params.get(key));
        }
        Request request = new Request.Builder().url(address).post(builder.build()).build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * post方式提交图片
     * @param address
     * @param mediaType
     * @param file
     * @param callback
     */
    public static void sendOkHttpRequestFileByPost(String address, MediaType mediaType, File file, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(),
                        RequestBody.create(mediaType, file))
                .build();

        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
