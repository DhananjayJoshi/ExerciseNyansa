package com.test.nyansa;

public class URLCount {
    private int count;
    private String url;
    URLCount(){

    }
    URLCount(int count, String url){
        this.count = count;
        this.url = url;
    }

    public void increamentCountBy(int number){
        this.count = this.count + number;
    }

    public int getCount(){
        return this.count;
    }

    public String getUrl(){
        return this.url;
    }
}
