package com.example.android.newshub;

/**
 * Created by Ghena on 18/05/2017.
 */

public class News {
    private String mTitle;
    private String mSection;
    private String mDate;

    public News(String title, String section, String date) {
        mTitle = title;
        mSection = section;
        mDate = date;
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
}
