package com.SkyzoneGroup.MadhusudhanCreation.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.SkyzoneGroup.MadhusudhanCreation.R;



//The adapters View Holder
public class View_Holder extends RecyclerView.ViewHolder {



    LinearLayout layout,btVproshare;
    ImageView ivMenu;
    TextView tvStyle,tvSize;
    ImageView ivIcon,product_wish,productSimilar;
    ImageButton ibDelete;
    Button btVproduct;
    TextView tvName, tvDiscount, tvspecial_price, tvprice;
    ImageView ivImage, ivImage2;
    TextView tvaddressdisplay, tvmobile, tvskucode, tvQuantity, tvMRP, tvcat_name,catalog_type;
    CheckBox radio_button, RadioButton;
    View view;
    RelativeLayout rlSize;
    // order text viwe

    TextView tvOrderId, tvDate, tvStates, tvItem;
    RecyclerView my_List_view;
    LinearLayout ll_main, cv;
    CardView cardView;
    WebView tvsDetail;
    ImageView ivRight, ivLeft;
    RadioButton radioButton;
    ImageView ivProductIcon;
    public View_Holder(View itemView) {
        super(itemView);
        view = itemView;
        productSimilar=(ImageView)itemView.findViewById(R.id.productSimilar);
        tvName = (TextView) itemView.findViewById(R.id.name);
        tvskucode = (TextView) itemView.findViewById(R.id.tvskucode);
        tvStyle = (TextView) itemView.findViewById(R.id.tvStyle);
        tvSize = (TextView) itemView.findViewById(R.id.tvSize);
        ibDelete = (ImageButton) itemView.findViewById(R.id.ibDelete);
        rlSize= (RelativeLayout) itemView.findViewById(R.id.rlSize);
        product_wish=(ImageView)itemView.findViewById(R.id.productWish);
        catalog_type=(TextView)itemView.findViewById(R.id.catalog_type);
        tvaddressdisplay = (TextView) itemView.findViewById(R.id.address_display_text);
        tvmobile = (TextView) itemView.findViewById(R.id.mobile);
        // my_List_view=(RecyclerView)itemView.findViewById(R.id.pro_RecyclerView);
//        radio_button =(CheckBox)itemView.findViewById(R.id.radio_button);
        radioButton = (RadioButton) itemView.findViewById(R.id.rbutton);
        ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
        cardView = (CardView) itemView.findViewById(R.id.cardview);
        ivProductIcon=(ImageView)itemView.findViewById(R.id.ivProductIcon);
        //     ll_main =(LinearLayout)itemView.findViewById(R.id.ll_main);
        ivImage2 = (ImageView) itemView.findViewById(R.id.ivImage2);
        btVproduct = (Button) itemView.findViewById(R.id.btVproduct);
        btVproshare = (LinearLayout) itemView.findViewById(R.id.btVproshare);
        tvDiscount = (TextView) itemView.findViewById(R.id.tvdiscount);
        tvMRP = (TextView) itemView.findViewById(R.id.tvMRP);
        tvspecial_price = (TextView) itemView.findViewById(R.id.tvspecial_price);
        tvprice = (TextView) itemView.findViewById(R.id.tvprice);
        tvQuantity = (TextView) itemView.findViewById(R.id.tvQuantity);
        ivLeft = (ImageView) itemView.findViewById(R.id.ivLeft);
        ivRight = (ImageView) itemView.findViewById(R.id.ivRight);

        tvcat_name = (TextView) itemView.findViewById(R.id.tvcat_name);

        tvOrderId = (TextView) itemView.findViewById(R.id.tvOrderId);
        tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        tvStates = (TextView) itemView.findViewById(R.id.tvStates);
        tvItem = (TextView) itemView.findViewById(R.id.tvItem);

    }


}
