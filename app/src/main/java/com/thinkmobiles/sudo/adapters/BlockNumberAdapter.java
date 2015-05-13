package com.thinkmobiles.sudo.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.activities.ProfileViewActivity;
import com.thinkmobiles.sudo.core.APIConstants;
import com.thinkmobiles.sudo.global.CircleTransform;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by omar on 19.04.15.
 */
public class BlockNumberAdapter extends BaseAdapter implements StickyListHeadersAdapter, CompoundButton.OnCheckedChangeListener {

    private List<UserModel> contacts;
    private LayoutInflater mInflater;
    private Activity mActivity;


    public BlockNumberAdapter(Activity context) {
        this.mActivity = context;
        this.contacts = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
    }


    public void reloadList(List<UserModel> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public UserModel getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public long getHeaderId(int i) {


        if (contacts.size() > 0) {
            if (contacts.get(i).getCompanion() != null && contacts.get(i).getCompanion().length() > 0) {
                return contacts.get(i).getCompanion().subSequence(0, 1).charAt(0);
            }
        } else return 0;
        return 0;

    }


    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.list_item_block_number, viewGroup, false);
            holder.ivAvatar = (ImageView) view.findViewById(R.id.ivContactsAvatar);
            holder.tvFirstName = (TextView) view.findViewById(R.id.tvContacstFirstName);
            holder.tvNumber = (TextView) view.findViewById(R.id.tvContactNumber);
            holder.checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.ivAvatar.setTag(pos);
        setAvatar(holder.ivAvatar, contacts.get(pos).getAvatar(), pos);
//        }
        holder.tvFirstName.setText(contacts.get(pos).getCompanion());

        if (contacts.get(pos).getNumbers() != null && contacts.get(pos).getNumbers().size() > 0)
            holder.tvNumber.setText(contacts.get(pos).getNumbers().get(contacts.get(pos).getNumbers().size() - 1).getNumber());

        holder.checkBox.setTag(pos);

        holder.checkBox.setOnCheckedChangeListener(this);


        return view;
    }

    @Override
    public View getHeaderView(int i, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = mInflater.inflate(R.layout.contacts_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.tvContactsHeader);

            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }


        String headerText;
        headerText = "";


        if (contacts.size() > 0)
            if (contacts.get(i).getCompanion() != null && contacts.get(i).getCompanion().length() > 0)
                headerText = "" + contacts.get(i).getCompanion().subSequence(0, 1).charAt(0);


        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int tag = (int) compoundButton.getTag();
        if (b) {
            Toast.makeText(mActivity, contacts.get(tag).getCompanion() + "was blocked", Toast.LENGTH_SHORT).show();

            for(NumberModel number : contacts.get(tag).getNumbers()){
                number.setBlocked(true);
            }


        } else {
            Toast.makeText(mActivity, contacts.get(tag).getCompanion() + "was unblocked", Toast.LENGTH_SHORT).show();

            for(NumberModel number : contacts.get(tag).getNumbers()) {
                number.setBlocked(false);
            }
        }
    }

    public List<UserModel> getResultList(){
        return contacts;
    }

    public class ViewHolder {
        ImageView ivAvatar;
        CheckBox checkBox;
        TextView tvFirstName, tvNumber;


    }

    private class HeaderViewHolder {

        TextView text;
    }


    private void setAvatar(final ImageView imageView, String imageUrl, final int pos) {
        if (imageUrl != null && !imageUrl.equalsIgnoreCase("")) {
            Picasso.with(mActivity).load(APIConstants.SERVER_URL + "/" + imageUrl).transform(new CircleTransform()).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    if ((int) imageView.getTag() != pos) {
                        Picasso.with(mActivity).load(R.drawable.ic_launcher).transform(new CircleTransform()).into(imageView);
                    }
                }

                @Override
                public void onError() {
                    Picasso.with(mActivity).load(R.drawable.ic_launcher).transform(new CircleTransform()).into(imageView);

                }
            });


        } else {
            Picasso.with(mActivity).load(R.drawable.ic_launcher).transform(new CircleTransform()).into(imageView);

        }

    }


}
