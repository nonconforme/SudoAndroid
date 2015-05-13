package com.thinkmobiles.sudo.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.activities.ProfileViewActivity;
import com.thinkmobiles.sudo.core.APIConstants;
import com.thinkmobiles.sudo.global.CircleTransform;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by omar on 19.04.15.
 */
public class BlockNumberAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private List<UserModel> contacts;
    private LayoutInflater mInflater;
    private Activity mActivity;

    private static final int SUCCESS = 10;

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
            view = mInflater.inflate(R.layout.list_item_contact, viewGroup, false);
            holder.ivAvatar = (ImageView) view.findViewById(R.id.ivContactsAvatar);
            holder.tvFirstName = (TextView) view.findViewById(R.id.tvContacstFirstName);
            holder.tvNumber = (TextView) view.findViewById(R.id.tvContactNumber);
            holder.ivOptions = (ImageView) view.findViewById(R.id.ivChatItemOptions);
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

        final ImageView transitionView = holder.ivAvatar;

        holder.ivOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });


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


    public class ViewHolder {
        ImageView ivAvatar, ivOptions;
        TextView tvFirstName, tvNumber;

        public ImageView getAvatarIV() {
            return ivAvatar;
        }

    }

    private class HeaderViewHolder {

        TextView text;
    }


    private void setAvatar(final ImageView imageView, String imageUrl, final int pos) {
        if (imageUrl != null && !imageUrl.equalsIgnoreCase("")) {
            Picasso.with(mActivity).load(APIConstants.SERVER_URL + "/" + imageUrl).transform(new CircleTransform()).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                  if ( (int)imageView.getTag() != pos){
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

    private void startProfileViewActivity(UserModel userModel, View view) {

        Log.d("start profile activity", "starting");

        ProfileViewActivity.launch(mActivity, view, userModel);

    }


}
