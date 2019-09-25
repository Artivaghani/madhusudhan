package com.SkyzoneGroup.MadhusudhanCreation.Activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.SkyzoneGroup.MadhusudhanCreation.R;
import com.SkyzoneGroup.MadhusudhanCreation.Rest.Option;

import java.util.List;

/**
 * Created by kevalt on 4/10/2018.
 */

public class SpinnerCustomAdapter extends BaseAdapter {
    ProductInfoActivity context;
    List<Option> dataset;
    LayoutInflater inflter;

    public SpinnerCustomAdapter(ProductInfoActivity productInfoActivity, List<Option> users) {

        this.context = productInfoActivity;
        this.dataset = users;
        Log.d("DATA","DATASIZE" + dataset.size());
        inflter = (LayoutInflater.from(productInfoActivity));

    }


    @Override
    public int getCount() {
        return dataset.size();

    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_rows, null);
        Log.d("DATA","DATANAME" + dataset.get(i).getOptionLabel());
        TextView names = (TextView) view.findViewById(R.id.company);
        names.setText(dataset.get(i).getOptionLabel());
        return view;
    }
}
