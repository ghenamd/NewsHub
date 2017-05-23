package com.example.android.newshub;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Ghena on 20/05/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private static final String NEWS_TYPE ="Section: ";
    private static final String DATE = "Published on: ";
    private OnItemClickListener mItemClickListener;// OnItemClickListener for RecyclerView-step 1
    //We create a method to split the string that comes from the JSON under the SectionId key
    private String splitSection(String input){
        if (input.contains("is")){
           String[] out = input.split("is");
            String part = out[0];
            //We capitalize the first letter of the string
            String part1 = part.substring(0,1).toUpperCase() + part.substring(1);
            String part2 = out[1];
            String finalString = part1 + " is " + part2;
            return finalString;
        } else if (input.contains("and")){
            String[] out = input.split("and");
            String part = out[0];
            //We capitalize the first letter of the string
            String part1 = part.substring(0,1).toUpperCase() + part.substring(1);
            String part2 = out[1];
            String finalString = part1 +" and " + part2;
            return finalString;
        } else if (input == null){
            return input;
        }else
            //We capitalize and return the first letter of the string
        return input.substring(0,1).toUpperCase() + input.substring(1);
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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
            itemView.setOnClickListener(this);// OnItemClickListener for RecyclerView - step 2
        }
        // OnItemClickListener for RecyclerView- step 3
        @Override
        public void onClick(View v){
            if (mItemClickListener != null){
                mItemClickListener.onItemClick(v,getPosition());
            }
        }
    }
    // OnItemClickListener for RecyclerView- step4
    public interface OnItemClickListener{
         void onItemClick(View view,int position);
    }
    // OnItemClickListener for RecyclerView- step5
    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
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
    //We create a method to find the position of the news in the RecyclerView
    // OnItemClickListener for RecyclerView-step6
    public News getPosition(int position){
        return mNews.get(position);
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
        News currentNews = mNews.get(position);

        // Set item views based on our views and data model
        TextView title = viewHolder.title;
        title.setText(currentNews.getTitle().toUpperCase());
        TextView section = viewHolder.section;
        section.setText(NEWS_TYPE + splitSection(currentNews.getSection()));

        //We format the date that comes from JSON
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'", Locale.UK);
        Date formattedDate = null;
        try {
            formattedDate = simpleDateFormat.parse(currentNews.getDate());
            Log.v("The date is",formattedDate.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TextView date = viewHolder.date;
        //Get the formattedDate "Date Object" toString() and set it into the TextView
        date.setText(DATE + formattedDate.toString());
    }
    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mNews.size();
    }
   // We create a method to clear the data set from the adapter
    public void clearAdapter(){
        int size =mNews.size();
        mNews.clear();
        notifyItemRangeRemoved(0, size);
    }
    //We create a method  to add new data to the adapter
    public void addAll(List<News> newz){
        mNews.addAll(newz);
        notifyDataSetChanged();
    }
}
