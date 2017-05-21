package com.example.android.newshub;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ghena on 20/05/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private static final String AUTHOR ="Author: ";
    private static final String DATE = "Published on: ";


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView section;
        public TextView date;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            section = (TextView) itemView.findViewById(R.id.news_type);
            date = (TextView)itemView.findViewById(R.id.date);
        }
    }

    private List<News> mNews;
    private Context mContext;
    // Pass in the news array into the constructor
    public NewsAdapter(Context context, List<News> news) {
        mNews = news;
        mContext = context;
    }
    // Easy access to the context object in the recyclerView
    private Context getContext() {
        return mContext;
    }
    // Inflating a layout from XML and returning the holder
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View newsView = inflater.inflate(R.layout.sample_row, parent, false);


        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(newsView);
        return viewHolder;
    }
    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        News news = mNews.get(position);

        // Set item views based on our views and data model
        TextView title = viewHolder.title;
        title.setText(news.getTitle());
        TextView section = viewHolder.section;
        section.setText(AUTHOR + news.getSection());
        TextView date = viewHolder.date;
        date.setText(DATE + news.getDate());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mNews.size();
    }

}
