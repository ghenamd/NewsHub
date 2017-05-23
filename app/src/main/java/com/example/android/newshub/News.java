package com.example.android.newshub;

/**
 * Created by Ghena on 18/05/2017.
 */

public class News {
    private String mTitle;
    private String mSection;
    private String mDate;
    private String mWebUrl;

    public News(String title, String section, String date, String webUrl) {
        mTitle = title;
        mSection = section;
        mDate = date;
        mWebUrl = webUrl;
    }

    public String getTitle() {
        return mTitle;
    }
    public String getSection() {
        return mSection;
    }
    public String getDate() {
        return mDate;
    }
    public String getWebUrl(){return mWebUrl;}
}
