package com.thinkmobiles.sudo.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.activities.BaseProfileActivity;
import com.thinkmobiles.sudo.activities.ProfileEditActivity;
import com.thinkmobiles.sudo.core.APIConstants;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import com.thinkmobiles.sudo.utils.CircleTransform;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by omar on 19.04.15.
 */
public class ContactsListAdapter extends BaseAdapter implements StickyListHeadersAdapter, View.OnClickListener {

    private LayoutInflater mInflater;
    private Activity mActivity;

    private Animation mUpAnimation;
    private Animation mBottommAnimation;

    private List<UserModel> contacts;

    private int lastPosition = -1;
    private boolean doAnimate = true;


    public ContactsListAdapter(Activity activity) {
        this.mActivity = activity;
        this.contacts = new ArrayList<>();
        mInflater = LayoutInflater.from(activity);
        initAnimations();

    }

    private void initAnimations() {
        mUpAnimation = AnimationUtils.loadAnimation(mActivity, R.anim.down_from_top);
        mBottommAnimation = AnimationUtils.loadAnimation(mActivity, R.anim.up_from_bottom);

    }


    public void reloadList(List<UserModel> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
        doAnimate = false;
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
        holder.ivOptions.setTag(pos);
        holder.ivOptions.setOnClickListener(this);

        setAvatar(holder.ivAvatar, contacts.get(pos).getAvatar(), pos);
        holder.tvFirstName.setText(contacts.get(pos).getCompanion());

        if (contacts.get(pos).getNumbers() != null && contacts.get(pos).getNumbers().size() > 0)
            holder.tvNumber.setText(contacts.get(pos).getNumbers().get(contacts.get(pos).getNumbers().size() - 1).getNumber());
        if (doAnimate) {
            Animation animation = AnimationUtils.loadAnimation(mActivity, (pos > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            view.startAnimation(animation);
        }
        lastPosition = pos;
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
           String headerText = "";


        if (contacts.size() > 0)
            if (contacts.get(i).getCompanion() != null && contacts.get(i).getCompanion().length() > 0)
                headerText = "" + contacts.get(i).getCompanion().subSequence(0, 1).charAt(0);


        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public void onClick(final View view) {
        PopupMenu popupMenu = new PopupMenu(mActivity, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if(menuItem.getItemId() == R.id.contacts_popup_edit)
                    launchEditActivity(contacts.get((int) view.getTag()));
                else if(menuItem.getItemId() == R.id.contacts_popup_remove){

                    AlertDialogCallback callback = new AlertDialogCallback() {
                        @Override
                        public void confirmDeletePhoneNumber(int pos) {
                            Intent deleteIntent = new  Intent(Constants.DELETE_CONTACT);
                            deleteIntent.putExtra(Constants.POSITION, pos);
                            mActivity.sendBroadcast(deleteIntent);
                        }


                    };
                    showConfirmationDialog(callback, (int) view.getTag());

                }
                return false;
            }
        });
        popupMenu.show();
    }


    private void showConfirmationDialog(AlertDialogCallback alertDialogCallback, final int position) {
        final AlertDialogCallback callback = alertDialogCallback;

        new AlertDialog.Builder(mActivity)
                .setTitle(mActivity.getString(R.string.delete_contact)).setMessage(mActivity.getString(R.string
                .do_you_want_to_delete) + contacts.get(position).getCompanion() + " ?")
                .setPositiveButton(mActivity.getString(R.string.alert_delete_pn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.confirmDeletePhoneNumber(position);

                    }

                }).setNegativeButton(mActivity.getString(R.string.alert_delete_pn_cancel), null).show();
    }


    private interface AlertDialogCallback {
        void confirmDeletePhoneNumber(int pos);
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
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.with(mActivity).load(APIConstants.SERVER_URL + "/" + imageUrl).transform(new CircleTransform()).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    if ((int) imageView.getTag() != pos) {
                        Picasso.with(mActivity).load(R.drawable.ic_man).transform(new CircleTransform()).into(imageView);
                    }
                }
                @Override
                public void onError() {
                    Picasso.with(mActivity).load(R.drawable.ic_man).transform(new CircleTransform()).into(imageView);

                }
            });


        } else {
            Picasso.with(mActivity).load(R.drawable.ic_man).transform(new CircleTransform()).into(imageView);

        }

    }
    public void launchEditActivity(UserModel userModel) {
        Intent intent = new Intent(mActivity, ProfileEditActivity.class);
        Bundle b = new Bundle();
        if (userModel != null) {
            b.putSerializable(BaseProfileActivity.USER_MODEL, userModel);
            intent.putExtra(BaseProfileActivity.USER_MODEL, b);
        }
        mActivity.startActivity(intent);
    }


}
