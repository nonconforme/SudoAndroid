package com.thinkmobiles.sudo.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import com.thinkmobiles.sudo.models.chat.MessageModel;
import com.thinkmobiles.sudo.models.chat.MessageModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by omar on 19.04.15.
 */
public class ChatListAdapter extends BaseAdapter {

    private List<MessageModel> listMessages;
    private LayoutInflater mInflater;
    private Context context;
    private UserModel thisUser;


    public ChatListAdapter(Context context, UserModel thisUser) {

        this.context = context;
        this.thisUser = thisUser;
        this.listMessages = new ArrayList<>();
        mInflater = LayoutInflater.from(context);


    }


    public void reloadList(List<MessageModel> listMessages) {
        this.listMessages = listMessages;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return listMessages.size();
    }

    @Override
    public Object getItem(int i) {
        return listMessages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(null, viewGroup, false);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        return view;
    }

    private class ViewHolder {
        ImageView ivAvatar, ivReply, ivOptions;
        TextView tvSenderName, tvSenderNumber, tvReceiverDetails, tvMessagePreview, tvItemTimedate, tvViewDetails;

    }

    private class HeaderViewHolder {

        TextView text;
    }


    private void setAvatar(ImageView imageView, String imageUrl) {
        if (imageUrl != null && !imageUrl.equalsIgnoreCase("")) {
            int dimen = (int) context.getResources().getDimension(R.dimen.sc_avatar_size);
            Picasso.with(context).load(imageUrl).resize(dimen, dimen).into(imageView);


        } else {
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
            imageView.setImageBitmap(bm);
        }

    }

    private String getDate(long milliSeconds) {

        SimpleDateFormat formatter = new SimpleDateFormat("mm-dd-HH:mm");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis((int) milliSeconds);
        return formatter.format(calendar.getTime());
    }

}
