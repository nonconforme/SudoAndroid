package com.thinkmobiles.sudo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;

import java.util.List;

/**
 * Created by omar on 23.04.15.
 */
public class ProfileViewNumbersAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;

    private List<NumberModel> mListNumbers;

    public ProfileViewNumbersAdapter(Context _context, List<NumberModel> _list) {
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
    public NumberModel getItem(int position) {
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
            view = inflater.inflate(R.layout.list_item_phone_number, parent, false);
            viewHolder = new ViewHolder();
            viewHolder. tvPhoneNumber = (TextView) view.findViewById(R.id.tvPhoneNumber_AVC);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder. tvPhoneNumber.setText(mListNumbers.get(position).getNumber());
        return view;
    }

    static class ViewHolder {
        public TextView tvPhoneNumber;

    }

}