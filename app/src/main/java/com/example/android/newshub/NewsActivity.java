package com.example.android.newshub;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ghena on 20/05/2017.
 */

public class NewsActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    private NewsAdapter mAdapter;
    private static final String GUARDIAN_API = "https://content.guardianapis.com/search?q=";
    private ProgressBar progressBar;
    private TextView noInternetConnection;
    private TextView noData;
    private RecyclerView recycler;
    private String newUrl;
    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("News List");
        setContentView(R.layout.recyler_view);
        //Gets the Intent from the MainActivity
        String topic = getIntent().getStringExtra("TOPIC");
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        noData = (TextView) findViewById(R.id.no_data);
        noInternetConnection = (TextView) findViewById(R.id.no_internet_connection);
        //Create the new requested URL
        String endOfUrl = "&orderBy=newest&api-key=test";
        newUrl = GUARDIAN_API + topic.toLowerCase() + endOfUrl;
        Log.v("New url:", newUrl);

        //Check if there is InternetConnection
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        recycler = (RecyclerView) findViewById(R.id.recycler);
        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        recycler.setAdapter(mAdapter);
        //Setting an OnItemClickListener step-7
        mAdapter.SetOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                News currentNews = mAdapter.getPosition(position);//We use the method getPosition created in the NewsAdapter
                //We use an intent to open the news website
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(currentNews.getWebUrl()));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        recycler.setLayoutManager(new LinearLayoutManager(this));
        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();
        // If there is internet connection we initialise the Loader
        if (isConnected) {
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        }
        // If there is no internet connection we set the text on the screen as no internet connection.
        else {
            progressBar.setVisibility(View.GONE);
            noInternetConnection.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

        return new NewsLoader(NewsActivity.this, newUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {
        progressBar.setVisibility(View.GONE);
        // Clear the adapter of previous news data
        int size = mAdapter.getItemCount();
        mAdapter.clearAdapter();

        // If there is a valid list of {@link news}, then add them to the adapter's
        // data set. This will trigger the recyclerView to update.
        if (newsList != null && !newsList.isEmpty()) {
            mAdapter.addAll(newsList);
        } else {
            // If there is no data from the server we set the view as no data available
            noData.setText(R.string.no_data_available);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clearAdapter();
    }
}
