package com.example.dandane.network;

import com.example.dandane.item.VocaItem;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NetworkSingleton {
    private static final NetworkSingleton ourInstance = new NetworkSingleton();

    public static NetworkSingleton getInstance() {
        return ourInstance;
    }

    private NetworkSingleton() { }

    public ArrayList<NetworkListner> networkListner = new ArrayList<>();
    public String connectSid;

    public ArrayList<VocaItem> vocaItems = new ArrayList<>();
}
