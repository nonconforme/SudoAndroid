package com.thinkmobiles.sudo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.models.counties.NumberPackages;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavilion on 14.04.2015.
 */
public class NumbersAdapter extends BaseAdapter{

    private Context mContext;
    private List<NumberPackages> mListNumberPackages;
    private LayoutInflater inflater;

    public NumbersAdapter(Context _context) {
        mContext = _context;
        mListNumberPackages = new ArrayList<NumberPackages>();
        inflater = (LayoutInflater) _context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mListNumberPackages.size();
    }

    @Override
    public NumberPackages getItem(int _position) {
        return mListNumberPackages.get(_position);
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
            viewHolder.tvNumber = (TextView) view.findViewById(R.id.tvNumber_FN);
            viewHolder.tvPrice = (TextView) view.findViewById(R.id.tvCountry_FN);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        viewHolder.tvNumber.setText(mListNumberPackages.get(position).getPackageName());
        viewHolder.tvPrice.setText(mListNumberPackages.get(position).getPrice());


        return view;
    }

    public void reloadList(List<NumberPackages> _listCountries) {
        this.mListNumberPackages = _listCountries;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        public TextView tvNumber;
        public TextView tvPrice;

    }
}
