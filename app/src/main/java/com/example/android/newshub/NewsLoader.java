package com.example.android.newshub;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Ghena on 21/05/2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    //Query URL
    private String mUrl;
    //Constructs a new NewsLoader
    public NewsLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl==null){
        return null;
        }
        List<News> news = Utils.fetchNewsData(mUrl);
        return news;
    }
}
