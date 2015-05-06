package com.thinkmobiles.sudo.adapters;

import android.app.Activity;
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
import com.thinkmobiles.sudo.activities.ChatActivity;
import com.thinkmobiles.sudo.models.chat.ChatModel;
import com.thinkmobiles.sudo.models.chat.MessageModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by omar on 19.04.15.
 */
public class ChatsListAdapter extends BaseAdapter {

    private List<ChatModel> chats;
    private LayoutInflater mInflater;
    private Activity activity;
    private CustomOnClickListener mItemOptionListeners;


    public ChatsListAdapter(Activity activity) {
        this.activity = activity;
        this.chats = new ArrayList<>();
        mInflater = LayoutInflater.from(activity);
        mItemOptionListeners = new CustomOnClickListener();

    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    public void reloadList(List<ChatModel> chats) {
        this.chats = chats;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return chats.size();
    }

    @Override
    public Object getItem(int i) {
        return chats.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.list_item_chat, viewGroup, false);
            holder = initViewHolder(view);
            holder.ivOptions.setOnClickListener(mItemOptionListeners);
            holder.tvViewDetails.setOnClickListener(mItemOptionListeners);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        List<MessageModel> thisChatList;
        ChatModel thisChat = null;
        MessageModel lastMessage = null;
        if (chats != null && chats.size() > 0) thisChat = chats.get(position);
        if (thisChat != null) {
            thisChatList = thisChat.getListMessages();
            if (thisChatList != null && thisChatList.size() > 0) lastMessage = thisChatList.get(0);

            holder.tvSenderName.setText(thisChat.getSender().getCompanion());
/*
            holder.tvSenderNumber.setText(thisChat.getSenderNumber().getNumber());

            holder.tvReceiverDetails.setText(thisChat.getReceiver().getCompanion() + " " + thisChat.getReceiverNumber().getNumber());*/

            if (lastMessage != null) {
                holder.tvMessagePreview.setText(lastMessage.getMessageText());
                long timeStamp = lastMessage.getTimeStamp();
                if (timeStamp != 0) holder.tvItemTimedate.setText(getDate(timeStamp));
            }

            setAvatar(holder.ivAvatar, thisChat.getReceiver().getAvatar());
            if (lastMessage.getSender() != thisChat.getSender()) holder.ivReply.setVisibility(View.INVISIBLE);
        }

        holder.ivOptions.setTag(position);
        holder.tvViewDetails.setTag(position);


        return view;
    }


    private ViewHolder initViewHolder(View view) {
        ViewHolder holder = new ViewHolder();
        holder.tvSenderName = (TextView) view.findViewById(R.id.tvChatItemSenderName);
        holder.tvSenderNumber = (TextView) view.findViewById(R.id.tvChatItemSenderNumber);
        holder.tvReceiverDetails = (TextView) view.findViewById(R.id.tvChatItemReceiverDetails);
        holder.tvMessagePreview = (TextView) view.findViewById(R.id.tvChatItemMessagePreview);
        holder.tvItemTimedate = (TextView) view.findViewById(R.id.tvChatItemTimedate);
        holder.tvViewDetails = (TextView) view.findViewById(R.id.tvChatItemViewDetails);
        holder.ivAvatar = (ImageView) view.findViewById(R.id.ivChatAvatar);
        holder.ivReply = (ImageView) view.findViewById(R.id.ivChatItemReply);
        holder.ivOptions = (ImageView) view.findViewById(R.id.ivChatItemOptions);
        return holder;

    }

    private void setAvatar(ImageView imageView, String imageUrl) {
        if (imageUrl != null && !imageUrl.equalsIgnoreCase("")) {
            int dimen = (int) activity.getResources().getDimension(R.dimen.sc_avatar_size);
            Picasso.with(activity).load(imageUrl).resize(dimen, dimen).into(imageView);


        } else {
            Bitmap bm = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_launcher);
            imageView.setImageBitmap(bm);
        }

    }

    private String getDate(long milliSeconds) {

        SimpleDateFormat formatter = new SimpleDateFormat(activity.getString(R.string.date_format));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    private void startChatActivity(ChatModel chatModel) {
        ChatActivity.launch(activity, chatModel);
    }

    private class CustomOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();

            switch (view.getId()) {
                case R.id.ivChatItemOptions:

                    break;
                case R.id.tvChatItemViewDetails:

                    startChatActivity(chats.get(position));


                    break;
            }

        }
    }

    private class ViewHolder {
        ImageView ivAvatar, ivReply, ivOptions;
        TextView tvSenderName, tvSenderNumber, tvReceiverDetails, tvMessagePreview, tvItemTimedate, tvViewDetails;

    }


}
