package com.enterprises.wasp.newsfeed;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String URL;

    public NewsLoader(Context context, String url) {
        super(context);
        URL = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (URL == null) {
            return null;
        }

        List<News> news = QueryUtils.fetchNewsData(URL);
        return news;
    }
}

