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
import com.thinkmobiles.sudo.models.Credits;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import java.util.List;

/**
 * Created by Pavilion on 16.04.2015.
 */
public class RechargeCreditsAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;

    private List<Credits> mListCredits;

    public RechargeCreditsAdapter(Context _context, List<Credits> _list){
        mContext = _context;
        mListCredits = _list;
        mInflater = (LayoutInflater) _context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mListCredits.size();
    }

    @Override
    public Credits getItem(int position) {
        return mListCredits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View _view = convertView;
        ViewHolder _viewHolder;
        if (_view == null) {
            _view = mInflater.inflate(R.layout.list_item_credit, parent, false);
            _viewHolder = new ViewHolder();
            _viewHolder.ivCredit     = (ImageView) _view.findViewById(R.id.ivImage_FRC);
            _viewHolder.tvCredit     = (TextView) _view.findViewById(R.id.tvCredit_FRC);
            _viewHolder.tvBuy        = (TextView) _view.findViewById(R.id.tvBuy_FRC);
            _view.setTag(_viewHolder);
        } else {
            _viewHolder = (ViewHolder) _view.getTag();
        }

        loadAvatar(_viewHolder.ivCredit);
        _viewHolder.tvCredit.setText(mListCredits.get(position).getCredits());
        _viewHolder.tvBuy.setText(mListCredits.get(position).getBuy());

        return _view;
    }

    private void loadAvatar(ImageView imageView) {
        Picasso.with(mContext)
                .load(R.drawable.ic_credits)
                .transform(new CropCircleTransformation())
                .into(imageView);
    }

    static class ViewHolder {
        public ImageView ivCredit;
        public TextView tvCredit;
        public TextView tvBuy;
    }
}
