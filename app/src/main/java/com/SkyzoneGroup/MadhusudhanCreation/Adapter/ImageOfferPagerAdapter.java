package com.SkyzoneGroup.MadhusudhanCreation.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.SkyzoneGroup.MadhusudhanCreation.Activity.SubCatWiseSubCatActivity;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Datum;

import java.util.List;

/**
 * Created by kevalt on 4/3/2018.
 */

public class ImageOfferPagerAdapter extends PagerAdapter {

    private static LayoutInflater inflater;
    Activity activity;
    List<Datum> icons;

    public ImageOfferPagerAdapter(FragmentActivity activity, List<Datum> items) {
        this.activity = activity;
        inflater = activity.getLayoutInflater();
        this.icons = items;
    }



    @Override
    public int getCount() {
        return icons.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View imageLayout = inflater.inflate(R.layout.pager, container, false);
        assert imageLayout != null;

        final ImageView imageView;

        Log.d("DATA","MAinDATA123" + icons.get(position).getCatId());
//        Toast.makeText(activity, icons.get(position).getCatId().toString(), Toast.LENGTH_SHORT).show();
        imageView = (ImageView) imageLayout.findViewById(R.id.category_image1);
//        Picasso.with(activity).load(icons.get(position).getImage()).fit().centerCrop()
//                .placeholder(R.drawable.icon)
//                .into(imageView);
        Glide.with(activity).load(icons.get(position).getImage())
                .crossFade()
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.icon)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        imageView.setImageDrawable(resource);
                        return false;
                    }
                })
                .into(imageView);

        //  Picasso.with(activity).load(icons.get(position).getSld_image()).placeholder(R.drawable.about).error(R.drawable.about).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CATID = icons.get(position).getCatId();
                String CATNAME = icons.get(position).getTitle();

                Log.d("DATA" ,"CATID" + CATID);
                if(CATID != ""){
                  //  Toast.makeText(activity, CATID, Toast.LENGTH_LONG).show();
                    Intent intent  =new Intent(activity, SubCatWiseSubCatActivity.class);
                    intent.putExtra("CATID",CATID);
                    intent.putExtra("CATNAME",CATNAME);


                    activity.startActivity(intent);
                }

            }
        });


        container.addView(imageLayout, 0);
        return imageLayout;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
