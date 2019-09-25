package com.SkyzoneGroup.MadhusudhanCreation.Adapter;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.SkyzoneGroup.MadhusudhanCreation.Activity.ProductInfoActivity;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.MultipleImage;

import java.util.List;


public class MultipleImage_Adapter extends RecyclerView.Adapter<View_Holder> {

    ProductInfoActivity context;
    List<MultipleImage> dataSet;

    public MultipleImage_Adapter(ProductInfoActivity productInfoActivity, List<MultipleImage> multipleImages) {
        this.dataSet = multipleImages;
       this.context = productInfoActivity;

    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.multipleimage_card_view, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {

        Glide.with(context).load(dataSet.get(position).getImage())
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
                        holder.ivProductIcon.setImageDrawable(resource);
                        return false;
                    }
                })
                .into(holder.ivProductIcon);
//        Picasso.with(context).load(dataSet.get(position).getImage()).fit().centerCrop()
//                .placeholder(R.drawable.icon)
//                .into(holder.ivProductIcon);

        fadeAnimation(  holder.cardView, false);

        holder.ivProductIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.SetMainImage(dataSet.get(position).getImage());

            }
        });

    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

    private void fadeAnimation(final View v, boolean isFadeOut){
        ObjectAnimator fadeOut = isFadeOut? ObjectAnimator.ofFloat(v, "alpha",  1f, 0f) :
                ObjectAnimator.ofFloat(v, "alpha",  0f, 1f);
        fadeOut.setDuration(500);
        fadeOut.start();
    }
}
