package com.example.dawn.manage.util;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Administrator on 2015-9-10.
 */
public class HttpHelper {
    private static AsyncHttpClient httpClient;
    public static void dopost(String url,RequestParams rp,JsonHttpResponseHandler jr){
        if(httpClient==null){
            httpClient= new AsyncHttpClient();
        }
        httpClient.post(url,rp,jr);
    }
    public static void doget(){

    }
}
