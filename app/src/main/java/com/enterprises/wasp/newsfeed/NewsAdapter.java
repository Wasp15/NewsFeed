package com.enterprises.wasp.newsfeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_information_layout, parent, false);
        }

        News currentStory = getItem(position);

        TextView titleView = listItemView.findViewById(R.id.title);
        String title = currentStory.getPrivateWebTitle();
        titleView.setText(title);

        TextView sectionView = listItemView.findViewById(R.id.section);
        String section = currentStory.getPrivateSectionName();
        sectionView.setText(section);

        TextView dateView = listItemView.findViewById(R.id.date);
        String date = currentStory.getPrivateWebPublicationDate();
        dateView.setText(date);

        return listItemView;
    }
}

