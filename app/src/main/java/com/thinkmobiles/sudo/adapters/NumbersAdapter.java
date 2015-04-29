package com.thinkmobiles.sudo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thinkmobiles.sudo.R;


import java.util.ArrayList;
import java.util.List;

import com.thinkmobiles.sudo.models.counties.CountryModel;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by Pavilion on 14.04.2015.
 */
public class NumbersAdapter extends BaseAdapter{

    private Context mContext;
    private List<CountryModel> mListCountries;
    private LayoutInflater inflater;

    public NumbersAdapter(Context _context) {
        mContext = _context;
        mListCountries = new ArrayList<CountryModel>();
        inflater = (LayoutInflater) _context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mListCountries.size();
    }

    @Override
    public CountryModel getItem(int _position) {
        return mListCountries.get(_position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_number, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.ivCountry    = (ImageView) view.findViewById(R.id.ivCountry_FN);
            viewHolder.tvCountry    = (TextView) view.findViewById(R.id.tvCountry_FN);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Picasso.with(mContext)
                .load(R.drawable.ic_launcher)
                .resize(100, 100)
                .transform(new CropCircleTransformation())
                .into(viewHolder.ivCountry);

        viewHolder.tvCountry.setText(mListCountries.get(position).getName());


        return view;
    }

    public void reloadList(List<CountryModel> _listCountries) {
        this.mListCountries = _listCountries;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        public ImageView ivCountry;
        public TextView tvCountry;

    }
}
