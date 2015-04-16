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
import com.thinkmobiles.sudo.models.Numbers;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by Pavilion on 14.04.2015.
 */
public class NumbersAdapter extends BaseAdapter{

    private Context mContext;
    private List<Numbers> mListNumbers;
    private LayoutInflater inflater;

    public NumbersAdapter(Context _context, List<Numbers> _list){
        mContext = _context;
        mListNumbers = _list;
        inflater = (LayoutInflater) _context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mListNumbers.size();
    }

    @Override
    public Numbers getItem(int position) {
        return mListNumbers.get(position);
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
            viewHolder.tvCredit     = (TextView) view.findViewById(R.id.tvCredit_FN);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Picasso.with(mContext)
                .load(R.drawable.ic_launcher)
                .resize(100, 100)
                .transform(new CropCircleTransformation())
                .into(viewHolder.ivCountry);

        viewHolder.tvCountry.setText(mListNumbers.get(position).getCountry());
        viewHolder.tvCredit.setText(mListNumbers.get(position).getCredits());

        return view;
    }

    static class ViewHolder {
        public ImageView ivCountry;
        public TextView tvCountry;
        public TextView tvCredit;
    }
}
