package com.example.dandane.item;

import java.util.ArrayList;

public class VocaItem {
    private ArrayList<WordItem> voca;
    private String title;
    private String desc;

    public ArrayList<WordItem> getVoca() {
        return voca;
    }
    public void setVoca(ArrayList<WordItem> voca) {
        this.voca = voca;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public VocaItem(String title, String desc) {
        this.voca = new ArrayList<>();
        this.title = title;
        this.desc = desc;
    }
}
