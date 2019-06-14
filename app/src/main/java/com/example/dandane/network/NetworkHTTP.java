package com.example.dandane.network;

import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkHTTP {
    private static OkHttpClient okHttpClient;
    private static String baseUrl = "http://54.180.57.73:3000";

    private static void responseListners(Response response, HTTPRequest httpRequest) throws IOException {
        for (NetworkListner networkListner: NetworkSingleton.getInstance().networkListner) {
            networkListner.responseListner(response, httpRequest);
        }
    }

    private static void GET(String url, Callback callback) {
        HttpUrl.Builder httpUrlBuilder = HttpUrl.parse(url).newBuilder();
        Request request = new Request.Builder().url(httpUrlBuilder.build().toString()).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
    private static void POST(String url, RequestBody requestBody, Callback callback) {
        Request request = new Request.Builder().url(url).post(requestBody).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void init(Context context) {
        okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        // Before Request
                        Request originalRequest = chain.request();
                        if (NetworkSingleton.getInstance().connectSid != null) {
                            originalRequest = chain.request().newBuilder()
                                    .addHeader("Cookie", NetworkSingleton.getInstance().connectSid)
                                    .build();
                        }
                        // After Request
                        Response response = chain.proceed(originalRequest);
                        Log.e("URL", originalRequest.url().toString());
                        if (originalRequest.url().toString().equals(baseUrl + "/signin")) {
                            if (response.header("set-cookie") != null) {
                                NetworkSingleton.getInstance().connectSid = response.header("set-cookie");
                            }
                        }
                        return response;
                    }
                }).build();
    }

    public static void requestGETStatus() {
        GET(baseUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                responseListners(response, HTTPRequest.GETStatus);
            }
        });
    }
    public static void requestPOSTSignIn(String id, String pw) {
        RequestBody requestBody = new FormBody.Builder().add("id", id).add("pw", pw).build();
        POST(baseUrl + "/signin", requestBody, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                responseListners(response, HTTPRequest.POSTSignIn);
            }
        });
    }
    public static void requestPOSTSignUp(String id, String pw) {
        RequestBody requestBody = new FormBody.Builder().add("id", id).add("pw", pw).build();
        POST(baseUrl + "/signup", requestBody, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                responseListners(response, HTTPRequest.POSTSignUp);
            }
        });
    }
}

