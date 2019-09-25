package com.SkyzoneGroup.MadhusudhanCreation.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.SkyzoneGroup.MadhusudhanCreation.Fragment.MainFragment;
import com.SkyzoneGroup.MadhusudhanCreation.Model.SectionDataModel;
import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Datum;

import java.util.ArrayList;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private ArrayList<SectionDataModel> dataList;
    private Activity _context;
    Subcatlist subcatlist;

    public interface Subcatlist {
        public void passcatid(String SUB_Cat, String catid);
    }

    public void Regter(Subcatlist subcatlist) {
        this.subcatlist = subcatlist;

    }

    public ExpandableListAdapter(FragmentActivity activity, ArrayList<SectionDataModel> allSampleData) {
        this.dataList = allSampleData;
        this._context = activity;
    }


    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        ArrayList<Datum> subCat = dataList.get(groupPosition).getAllItemsInSection();

        return subCat.get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        final ArrayList<Datum> subCat = dataList.get(groupPosition).getAllItemsInSection();

        final String childText = subCat.get(childPosition).getCatName();
        final String SUB_Cat = subCat.get(childPosition).getCatId();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);


        txtListChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(">>> ", " SUB CAT: " + SUB_Cat);
                MainFragment.expListView.setVisibility(View.GONE);
                MainFragment.ll_mainview.setVisibility(View.VISIBLE);
             //   MainFragment.alertDialog1.dismiss();
                if (subcatlist != null) {
                    subcatlist.passcatid(SUB_Cat, childText);
                }
           //     MainFragment.alertDialog1.cancel();
                //   _context.mDrawerLayout.closeDrawer(Gravity.START);
//                Bundle args = new Bundle();
//                args.putString("category_id", subCat.get(childPosition).getCatId());
//                MainFragment fragobj = new MainFragment();
//                fragobj.setArguments(args);
////                MainFragment.SUBCat(subCat.get(childPosition).getCatId());
////                Intent intent = new Intent(_context, ProductActivity.class);
////                intent.putExtra("SUB_CATID", SUB_Cat);
////                intent.putExtra("SUB_CATNAME", childText);
////                _context.startActivity(intent);
////                _context.overridePendingTransition(R.anim.open_next, R.anim.close_next);

            }
        });

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        Log.d(" >>> ", "getChildrenCount: " + groupPosition);
        ArrayList<Datum> subCat = dataList.get(groupPosition).getAllItemsInSection();

        return subCat.size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return dataList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return dataList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
//        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }


        LinearLayout Linearparent = (LinearLayout) convertView.findViewById(R.id.parent);


        ImageView arrow = (ImageView) convertView.findViewById(R.id.arrow);

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);

        lblListHeader.setTypeface(null, Typeface.NORMAL);

        if (isExpanded) {

            lblListHeader.setTextColor(_context.getResources().getColor(R.color.listview_group_text_color_after));


            // arrow.setImageResource(R.drawable.ic_down);
            //   arrow.setBackgroundResource(R.drawable.ic_up_arrow);

            arrow.setImageResource(R.drawable.ic_up_arrow);
            arrow.setColorFilter(_context.getResources().getColor(R.color.grey_8f));
            Linearparent.setBackgroundColor(_context.getResources().getColor(R.color.listview_group_parent_color_after));

        } else {
            lblListHeader.setTextColor(_context.getResources().getColor(R.color.listview_group_text_color_before));

            //  arrow.setImageResource(R.drawable.ic_up_arrow);
            //   arrow.setBackgroundResource(R.drawable.ic_down);
            arrow.setImageResource(R.drawable.ic_down);
            arrow.setColorFilter(_context.getResources().getColor(R.color.listview_group_arrow_image_color_before));
            Linearparent.setBackgroundColor(_context.getResources().getColor(R.color.listview_group_parent_color_before));
        }
        // lblListHeader.setText(headerTitle);
        lblListHeader.setText(dataList.get(groupPosition).getHeaderTitle());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}