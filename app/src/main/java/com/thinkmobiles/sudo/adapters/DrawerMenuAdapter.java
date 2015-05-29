package com.thinkmobiles.sudo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.models.DrawerMenuItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavilion on 14.04.2015.
 */
public class DrawerMenuAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;

    private List<DrawerMenuItemModel> mListMenus;

    public DrawerMenuAdapter(Context _context) {
        mContext = _context;
        mListMenus = new ArrayList<>();
        inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mListMenus.size();
    }

    @Override
    public DrawerMenuItemModel getItem(int _position) {
        return mListMenus.get(_position);
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
            view = inflater.inflate(R.layout.drawer_menu_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = (ImageView) view.findViewById(R.id.ivIcon_Drawer);
            viewHolder.tvName = (TextView) view.findViewById(R.id.tvName_Drawer);
            viewHolder.tvParam = (TextView) view.findViewById(R.id.tvParam_Drawer);
            viewHolder.backLineDrawer = view.findViewById(R.id.blackLineDrawer);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (mListMenus.get(position).getName() == 0) {
            viewHolder.rlDrawerMenuItem = (RelativeLayout) view.findViewById(R.id.rlDrawerMenuItem);
            viewHolder.ivIcon.setVisibility(View.INVISIBLE);
            viewHolder.tvParam.setVisibility(View.INVISIBLE);
            viewHolder.tvName.setVisibility(View.INVISIBLE);
            viewHolder.backLineDrawer.setVisibility(View.VISIBLE);

        } else {
            viewHolder.ivIcon.setImageResource(mListMenus.get(position).getIcon());
            viewHolder.tvName.setText(mContext.getResources().getString(mListMenus.get(position).getName()));
            viewHolder.tvParam.setText(mListMenus.get(position).getParam());
        }

        return view;
    }

    public void reloadList(List<DrawerMenuItemModel> _listNumbers) {
        this.mListMenus = _listNumbers;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        public ImageView ivIcon;
        public TextView tvName, tvParam;
        public RelativeLayout rlDrawerMenuItem;
        public View backLineDrawer;
    }

    @Override
    public boolean isEnabled(int position) {
        if (position == 3) return false;
        else return true;
    }


 }