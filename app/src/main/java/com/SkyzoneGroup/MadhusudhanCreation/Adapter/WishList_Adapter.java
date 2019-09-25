package com.SkyzoneGroup.MadhusudhanCreation.Adapter;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Paint;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.SkyzoneGroup.MadhusudhanCreation.Activity.ProductInfoActivity;
import com.SkyzoneGroup.MadhusudhanCreation.Fragment.Dailog_SimilarFragment;
import com.SkyzoneGroup.MadhusudhanCreation.Fragment.Dailog_WishListFragment;
import com.SkyzoneGroup.MadhusudhanCreation.Model.Subcatdatamodel;
import com.SkyzoneGroup.MadhusudhanCreation.R;

import java.util.List;

public class WishList_Adapter extends RecyclerView.Adapter<View_Holder> {

    Dailog_WishListFragment context;
    List<Subcatdatamodel> dataSet;
    String USER_ID;

    private static final StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();

    public WishList_Adapter(List<Subcatdatamodel> data, Dailog_WishListFragment context, String USER_ID) {
        this.dataSet = data;
        this.context = context;
        this.USER_ID = USER_ID;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_cat_subpro_item, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final View_Holder holder, final int position) {

        holder.tvName.setText(dataSet.get(position).getProductName());
        holder.tvprice.setText("Starting at : ₹ " + String.valueOf(dataSet.get(position).getSpecialPrice()));
//        holder.tvStyle.setText(dataSet.get(position).getCatTitle());
        holder.tvStyle.setVisibility(View.GONE);
        holder.tvspecial_price.setPaintFlags( holder.tvspecial_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvspecial_price.setText(String.valueOf(dataSet.get(position).getPrice() + ""));
        int dis = dataSet.get(position).getDiscount();
        if (dis == 0) {
            holder.tvDiscount.setVisibility(View.GONE);
        } else {
            holder.tvDiscount.setText("(" + dataSet.get(position).getDiscount() + "%OFF)");
        }

//        Picasso.with(activity).load(dataSet.get(position).getCatImage()).fit().centerCrop()
//                .placeholder(R.drawable.icon)
//                .into(holder.ivImage);
//        Glide.with(activity).load(dataSet.get(position).getCatImage()).placeholder(R.drawable.icon)
//                .into(holder.ivImage);

        if (!(((Subcatdatamodel) this.dataSet.get(position)).getProductImage() == null || ((Subcatdatamodel) this.dataSet.get(position)).getProductImage().isEmpty())) {
            Glide.with(context).load(dataSet.get(position).getProductImage())
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
                            holder.ivImage.setImageDrawable(resource);
                            return false;
                        }
                    })
                    .into(holder.ivImage);

        }

/*
        if (dataSet.get(position).getIsWishlist()==0)
        {
            holder.product_wish.setImageResource(R.mipmap.ic_favorite_border_black_24dp);
        }
        else
        {
            holder.product_wish.setImageResource(R.mipmap.ic_favorite_black_24dp);
        }
*/

        fadeAnimation(  holder.cardView, false);

        holder.btVproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getActivity(), ProductInfoActivity.class);
                intent.putExtra("PRODUCTID",dataSet.get(position).getProductId());
                context.startActivity(intent);
                context.getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

//        holder.productSimilar.setVisibility(View.GONE);



        holder.productSimilar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialogFragment bottomSheetDialogFragment = new Dailog_SimilarFragment(dataSet.get(position).getProductId());
                bottomSheetDialogFragment.show(context.getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });


        holder.product_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.product_wish.getTag()=="UNFAV")
                {
                    Animation rotation = AnimationUtils.loadAnimation(context.getActivity(), R.anim.button_rotate);
                    holder.product_wish.startAnimation(rotation);

                    context.AddWishList(USER_ID,dataSet.get(position).getProductId());
                    holder.product_wish.setImageResource(R.drawable.ic_favorite_black_24dp);
                    holder.product_wish.setTag("FAV");
                }
                else
                {
                    Animation rotation = AnimationUtils.loadAnimation(context.getActivity(), R.anim.button_rotate1);
                    holder.product_wish.startAnimation(rotation);

                    context.RemoveWish(USER_ID, dataSet.get(position).getProductId());
                    holder.product_wish.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    holder.product_wish.setTag("UNFAV");
                }
            }
        });
        holder.btVproshare.setVisibility(View.GONE);

    }


    private void fadeAnimation(final View v, boolean isFadeOut){
        ObjectAnimator fadeOut = isFadeOut? ObjectAnimator.ofFloat(v, "alpha",  1f, 0f) :
                ObjectAnimator.ofFloat(v, "alpha",  0f, 1f);
        fadeOut.setDuration(500);
        fadeOut.start();
    }
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }


}

