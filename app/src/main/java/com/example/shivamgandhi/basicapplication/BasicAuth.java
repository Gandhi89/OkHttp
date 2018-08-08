package com.example.shivamgandhi.basicapplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicAuth implements Interceptor {

    private String credentials;

    public BasicAuth(String auth) {
        this.credentials = auth;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials).header("Content-Type","application/json").header("Accept-Language","en_US").build();
        return chain.proceed(authenticatedRequest);
    }
}
