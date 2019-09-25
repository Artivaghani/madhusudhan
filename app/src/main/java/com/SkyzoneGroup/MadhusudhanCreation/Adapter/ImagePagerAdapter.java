package com.SkyzoneGroup.MadhusudhanCreation.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.SkyzoneGroup.MadhusudhanCreation.Activity.WelcomeActivity;
import com.SkyzoneGroup.MadhusudhanCreation.R;

/**
 * Created by kevalt on 3/6/2018.
 */

public class ImagePagerAdapter extends PagerAdapter {
    private LayoutInflater inflater;
    Activity activity;
    Context context;
    int[] Adbanner;

    public ImagePagerAdapter(FragmentActivity activity, int[] banner) {
        this.context = activity;
        this.inflater = activity.getLayoutInflater();
        this.Adbanner = banner;

    }

    public ImagePagerAdapter(WelcomeActivity welcomeActivity, int[] vPimage) {
        this.context = welcomeActivity;
        this.inflater = welcomeActivity.getLayoutInflater();
        this.Adbanner = vPimage;
    }

    @Override
    public int getCount() {
        return Adbanner.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View imageLayout = inflater.inflate(R.layout.layout_creative, container, false);

        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.creative);
        imageView.setImageResource(Adbanner[position]);

        container.addView(imageLayout);

        return imageLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
