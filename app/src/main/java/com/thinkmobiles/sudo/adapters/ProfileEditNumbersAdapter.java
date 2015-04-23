package com.thinkmobiles.sudo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;

import java.util.List;

/**
 * Created by omar on 23.04.15.
 */
public class ProfileEditNumbersAdapter extends BaseAdapter {

    private Context mContext;
    private List<NumberModel> mListNumbers;
    private LayoutInflater inflater;

    public ProfileEditNumbersAdapter(Context _context, List<NumberModel> _list) {
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
            view = inflater.inflate(R.layout.phone_number_item_edit, parent, false);

            viewHolder = new ViewHolder();
            viewHolder. etPhoneNumber = (TextView) view.findViewById(R.id.etPhoneNumber_AVC);
            viewHolder.ivDeleteNumber = (ImageView) view.findViewById(R.id.ivRemoveNumber_AVC);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        viewHolder. etPhoneNumber.setText(mListNumbers.get(position).getNumber());
        final int pos = position;
        viewHolder.ivDeleteNumber .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListNumbers.remove(pos);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    static class ViewHolder {
        public TextView etPhoneNumber;
        public ImageView ivDeleteNumber;

    }

}