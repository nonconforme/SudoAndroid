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
import com.squareup.picasso.Picasso;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.core.APIConstants;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import com.thinkmobiles.sudo.models.chat.MessageModelOld;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by omar on 19.04.15.
 */
public class ChatListAdapter extends BaseAdapter {

    private List<MessageModelOld> listMessages;
    private LayoutInflater mInflater;
    private Context context;
    private UserModel receiver;


    public ChatListAdapter(Context context) {
        this.context = context;
        listMessages = new ArrayList<>();
        mInflater = LayoutInflater.from(context);

    }

    public void reloadContent(List<MessageModelOld> listMessages, UserModel receiver) {
        this.listMessages = listMessages;
        this.receiver = receiver;
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
    public View getView(int postion, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();

            if (isIncoming(postion)) view = mInflater.inflate(R.layout.list_item_chat_incoming, viewGroup, false);
            else view = mInflater.inflate(R.layout.list_item_chat_outgoing, viewGroup, false);

            holder.ivAvatar = (ImageView) view.findViewById(R.id.ivAvatar);
            holder.tvMessage = (TextView) view.findViewById(R.id.tvChatText);
            holder.tvTimedate = (TextView) view.findViewById(R.id.tvTimeDate);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        setAvatar(holder.ivAvatar, postion);
        setMessage(holder.tvMessage, postion);
        setTimeDate(holder.tvTimedate, postion);


        return view;
    }

    private class ViewHolder {
        ImageView ivAvatar;
        TextView tvTimedate, tvMessage;

    }

    private boolean isIncoming(int position) {

        if (listMessages.get(position).getSender().equals(receiver)) return false;
        return true;

    }

    private void setAvatar(ImageView iv, int position) {
        String imageUrl = listMessages.get(position).getSender().getAvatar();
        if (imageUrl != null && !imageUrl.equalsIgnoreCase("")) {
            int dimen = (int) context.getResources().getDimension(R.dimen.schats_avatar_size);
            Picasso.with(context).load(APIConstants.SERVER_URL + "/" + imageUrl).resize(dimen, dimen).into(iv);


        } else {
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
            iv.setImageBitmap(bm);
        }

    }

    private void setMessage(TextView tv, int position) {
        tv.setText(listMessages.get(position).getMessageText());

    }

    private void setTimeDate(TextView tv, int position) {
        long timestamp = listMessages.get(position).getTimeStamp();

        SimpleDateFormat formatter = new SimpleDateFormat(context.getString(R.string.date_format));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        tv.setText(formatter.format(calendar.getTime()));
    }

}
