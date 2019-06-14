package com.example.dandane.network;

import java.io.IOException;

import okhttp3.Response;

public interface NetworkListner {
    void responseListner(Response response, HTTPRequest httpRequest) throws IOException;
}
