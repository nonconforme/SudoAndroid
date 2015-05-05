package com.thinkmobiles.sudo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.models.numbers.NumberObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavilion on 14.04.2015.
 */
public class NumbersAdapter extends BaseAdapter{

    private Context mContext;
    private List<NumberObject> mListNumberObject;
    private LayoutInflater inflater;


    public NumbersAdapter(Context _context) {
        mContext = _context;
        mListNumberObject = new ArrayList<>();
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mListNumberObject.size();
    }

    @Override
    public NumberObject getItem(int _position) {
        return mListNumberObject.get(_position);
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


            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        viewHolder.tvNumber.setText(mListNumberObject.get(position).getNumber());
        


        return view;
    }

    public void reloadList(List<NumberObject> _listCountries) {
        this.mListNumberObject = _listCountries;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        public TextView tvNumber;


    }
}
