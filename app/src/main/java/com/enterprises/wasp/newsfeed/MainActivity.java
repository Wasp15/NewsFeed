package com.enterprises.wasp.newsfeed;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<News>>,
        SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String GUARDIAN_URL = "https://content.guardianapis.com/search";
    private static final int NEWS_LOADER_ID = 1;
    private ProgressBar progressBar;
    private TextView emptyView;
    private NewsAdapter adapter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.indeterminateBar);

        ListView newsListView = findViewById(R.id.list);

        ConnectivityManager connectivityManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        adapter = new NewsAdapter(this, new ArrayList<News>());

        newsListView.setAdapter(adapter);

        // Obtain a reference to the SharedPreferences file for this app
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        // And register to be notified of preference changes
        // So we know when the user has adjusted the query settings
        prefs.registerOnSharedPreferenceChangeListener(this);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News currentStory = adapter.getItem(i);

                Uri newsUri = Uri.parse(currentStory.getPrivateUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
            }
        });

        emptyView = findViewById(R.id.empty_view);
        newsListView.setEmptyView(emptyView);

        if (isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            progressBar.setVisibility(View.GONE);
            emptyView.setText(R.string.internet_connection_err);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (key.equals(getString(R.string.settings_start_time_key)) ||
                key.equals(getString(R.string.settings_time_key)) ||
                key.equals(getString(R.string.settings_section_key))) {
            // Clear the ListView as a new query will be kicked off
            adapter.clear();

            // Hide the empty state text view as the loading indicator will be displayed
            emptyView.setVisibility(View.GONE);

            // Show the loading indicator while new data is being fetched
            View loadingIndicator = findViewById(R.id.indeterminateBar);
            loadingIndicator.setVisibility(View.VISIBLE);

            // Restart the loader to re-query the USGS as the query settings have been updated
            getLoaderManager().restartLoader(NEWS_LOADER_ID, null, this);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String defaultSection = sharedPrefs.getString(
                getString(R.string.settings_section_key),
                getString(R.string.settings_section_default)
        );

        String defaultStartTime = sharedPrefs.getString(
                getString(R.string.settings_start_time_key),
                getString(R.string.settings_start_time_default));

        String defaultEndTime = sharedPrefs.getString(
                getString(R.string.settings_time_key),
                getString(R.string.settings_time_default));

        Uri baseUri = Uri.parse(GUARDIAN_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("section", defaultSection);
        uriBuilder.appendQueryParameter("page-size", "150");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("from-date", defaultStartTime);
        uriBuilder.appendQueryParameter("to-date", defaultEndTime);
        uriBuilder.appendQueryParameter("api-key", "b0958911-b1a1-42d3-bb91-62aa34a31843");

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.indeterminateBar);
        loadingIndicator.setVisibility(View.GONE);

        //adapter.clear();

        if (news != null && !news.isEmpty()) {
            adapter.addAll(news);
        }
        emptyView.setText(R.string.stories_err);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        adapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

