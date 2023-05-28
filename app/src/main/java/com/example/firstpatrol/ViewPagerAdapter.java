package com.example.firstpatrol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewPagerAdapter extends PagerAdapter {
    Context context;
    int images[]={
        R.drawable.ingy2,
        R.drawable.ingy3
    };

    int headings[]={
      R.string.heading_one,
      R.string.heading_two
    };

    int description[]={
      R.string.desc_one,
      R.string.desc_two
    };

    public ViewPagerAdapter(Context context){

        this.context=context;
    }

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(LinearLayout) object;
    }

    @NonNull
    /*@Override*/
    public Object instanciateItem(@NonNull ViewGroup container, int position){
        LayoutInflater layoutInflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_main2,container,false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView slidetitleimage = (ImageView) view.findViewById(R.id.titleImage);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView slideHeading = (TextView) view.findViewById(R.id.texttitle);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView slideDescription = (TextView) view.findViewById(R.id.textdescription);

        slidetitleimage.setImageResource(images[position]);
        slideHeading.setText(headings[position]);
        slideDescription.setText(description[position]);

        container.addView(view);

        return view;

    }
}
