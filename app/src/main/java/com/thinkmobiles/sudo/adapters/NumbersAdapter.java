package com.thinkmobiles.sudo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;
import com.thinkmobiles.sudo.models.numbers.NumberObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavilion on 14.04.2015.
 */
public class NumbersAdapter extends BaseAdapter{

    private Context mContext;
    private List<NumberModel> mListMyNumbers;
    private List<NumberObject> mListAvailableNumbers;
    private LayoutInflater inflater;


    public NumbersAdapter(Context _context) {
        mContext = _context;
        mListAvailableNumbers = new ArrayList<>();
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mListAvailableNumbers.size();
    }

    @Override
    public NumberObject getItem(int _position) {
        return mListAvailableNumbers.get(_position);
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

        String  availableNumber = mListAvailableNumbers.get(position).getNumber();

        for(NumberModel myNumber : mListMyNumbers ){
            if(myNumber.getNumber().equalsIgnoreCase("+"+availableNumber)){
                viewHolder.tvNumber.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));

            }
        }
        viewHolder.tvNumber.setText(availableNumber);

        


        return view;
    }

    public void reloadList(List<NumberObject> _listAvailableNumbers, List<NumberModel> _listMyNumbers) {
        this.mListAvailableNumbers = _listAvailableNumbers;
        this.mListMyNumbers = _listMyNumbers;

        notifyDataSetChanged();
    }

    static class ViewHolder {
        public TextView tvNumber;


    }
}
