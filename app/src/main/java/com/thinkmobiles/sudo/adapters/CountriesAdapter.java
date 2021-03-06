package com.thinkmobiles.sudo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.models.counties.CountryModel;

import java.util.ArrayList;
import java.util.List;

import static com.thinkmobiles.sudo.utils.CountryHelper.setCountryByIso;

/**
 * Created by Pavilion on 14.04.2015.
 */
public class CountriesAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;

    private List<CountryModel> mListCountries;


    public CountriesAdapter(Context _context) {
        mContext = _context;
        mListCountries = new ArrayList<>();
        inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            view = inflater.inflate(R.layout.list_item_country, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivCountry = (ImageView) view.findViewById(R.id.ivCountry_FN);
            viewHolder.tvCountry = (TextView) view.findViewById(R.id.tvCountry_FN);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        setCountryByIso(mContext, viewHolder.ivCountry, mListCountries.get(position).getCountryIso());
        viewHolder.tvCountry.setText(mListCountries.get(position).getName());
        return view;
    }

    public void reloadList(List<CountryModel> _listCountries) {

        List<CountryModel> tempList = new ArrayList<>();
        int i = 0;
        for (CountryModel country : _listCountries) {
            if (App.getCurrentLocationISO()!= null
                    && country.getCountryIso().equalsIgnoreCase(App
                    .getCurrentLocationISO()) ||


                    (
                           App
                            .getCurrentLocationISO()!= null &&
            country.getCountryIso().equalsIgnoreCase(Constants.UNITED_KINGDOM_ISO)
                            &&
                            App.getCurrentLocationISO().equalsIgnoreCase(Constants.REAL_UNITED_KINGDOM_ISO))
                           ) {

                tempList.add(country);
                _listCountries.remove(i);
                i++;
            }
        }
        tempList.addAll(_listCountries);
        this.mListCountries = tempList;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        public ImageView ivCountry;
        public TextView tvCountry;

    }
}
