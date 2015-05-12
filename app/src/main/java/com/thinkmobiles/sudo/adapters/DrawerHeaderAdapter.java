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
import com.thinkmobiles.sudo.models.addressbook.NumberModel;
import com.thinkmobiles.sudo.models.counties.CountryModel;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavilion on 14.04.2015.
 */
public class DrawerHeaderAdapter extends BaseAdapter {

    private boolean isCountryHidden = false;
    private Context mContext;
    private List<NumberModel> mListNumbers;
    private LayoutInflater inflater;

    public DrawerHeaderAdapter(Context _context) {
        mContext = _context;
        mListNumbers = new ArrayList<>();
        inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mListNumbers.size();
    }

    @Override
    public NumberModel getItem(int _position) {
        return mListNumbers.get(_position);
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
            view = inflater.inflate(R.layout.drawer_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.ivCountry = (ImageView) view.findViewById(R.id.ivCountry_Drawer);
            viewHolder.tvNumber = (TextView) view.findViewById(R.id.tvNumber_Drawer);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if(isCountryHidden)
            viewHolder.ivCountry.setVisibility(View.INVISIBLE);
        else
            viewHolder.ivCountry.setVisibility(View.VISIBLE);

        Picasso.with(mContext).load(R.drawable.ic_launcher).resize(100, 100).transform(new CropCircleTransformation()).into(viewHolder.ivCountry);

        viewHolder.tvNumber.setText(mListNumbers.get(position).getNumber());


        return view;
    }

    public void reloadList(List<NumberModel> _listNumbers) {
        this.mListNumbers = _listNumbers;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        public ImageView ivCountry;
        public TextView tvNumber;

    }

    public void hideCountry(boolean ishidden){
     isCountryHidden = ishidden;
        notifyDataSetChanged();
    }
}
