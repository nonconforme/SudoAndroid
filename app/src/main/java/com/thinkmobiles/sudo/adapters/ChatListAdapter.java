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
import com.thinkmobiles.sudo.models.chat.ChatModel;
import com.thinkmobiles.sudo.models.chat.MessageModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by omar on 19.04.15.
 */
public class ChatListAdapter extends BaseAdapter {

    private List<ChatModel> chats;
    private LayoutInflater mInflater;
    private Context context;




    public ChatListAdapter(Context context) {
        this.context = context;
        this.chats = new ArrayList<>();
        mInflater = LayoutInflater.from(context);


    }


    public void reloadList(List<ChatModel> chats) {
        this.chats = chats;
       /* notifyDataSetChanged();*/
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.chat_item, viewGroup, false);


            holder.tvSenderName = (TextView) view.findViewById(R.id.tvChatItemSenderName);
            holder.tvSenderNumber = (TextView) view.findViewById(R.id.tvChatItemSenderNumber);
            holder.tvReceiverDetails = (TextView) view.findViewById(R.id.tvChatItemReceiverDetails);
            holder.tvMessagePreview = (TextView) view.findViewById(R.id.tvChatItemMessagePreview);
            holder.tvItemTimedate = (TextView) view.findViewById(R.id.tvChatItemTimedate);
            holder.tvViewDetails = (TextView) view.findViewById(R.id.tvChatItemViewDetails);


            holder.ivAvatar = (ImageView) view.findViewById(R.id.ivChatAvatar);
            holder.ivReply = (ImageView) view.findViewById(R.id.ivChatItemReply);
            holder.ivOptions = (ImageView) view.findViewById(R.id.ivChatItemOptions);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ChatModel thisChat = chats.get(i);
        List<MessageModel> thisChatList = thisChat.getListMessages();
        MessageModel lastMessage = null;
        if (thisChatList != null && thisChatList.size() > 0){
            lastMessage = thisChatList.get(thisChatList.size() - 1);
            thisChatList = null;
        }


        holder.tvSenderName.setText(thisChat.getSender().getCompanion());
        holder.tvSenderNumber.setText(thisChat.getSenderNumber());
        holder.tvReceiverDetails.setText(thisChat.getReceiver().getCompanion() + " " + thisChat.getReceiverNumber());

        if (lastMessage != null) {
            holder.tvMessagePreview.setText(lastMessage.getMessageText());

            long timeStamp = lastMessage.getTimeStamp();
            if (timeStamp != 0)
                holder.tvItemTimedate.setText(getDate(timeStamp));
        }
        setAvatar(holder.ivAvatar, thisChat.getReceiver().getAvatar());

        if(!lastMessage.isTrueIfMessageWasRecieved())
            holder.ivReply.setVisibility(View.INVISIBLE);

        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()){
                    case R.id.ivChatItemOptions:
                        Toast.makeText(context, "Options was clicked", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.tvChatItemViewDetails:
                        Toast.makeText(context, "View Details was clicked", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        };

        holder.ivOptions.setOnClickListener(myOnClickListener);
        holder.tvViewDetails.setOnClickListener(myOnClickListener);

        return view;
    }


    private class ViewHolder {
        ImageView ivAvatar, ivReply, ivOptions;
        TextView tvSenderName, tvSenderNumber, tvReceiverDetails,
                tvMessagePreview, tvItemTimedate, tvViewDetails;


    }

    private class HeaderViewHolder {

        TextView text;
    }


    private void setAvatar(ImageView imageView, String imageUrl) {
        if (imageUrl != null && !imageUrl.equalsIgnoreCase("")) {
            int dimen = (int) context.getResources().getDimension(R.dimen.sc_avatar_size);
            Picasso.with(context)
                    .load(imageUrl)
                    .resize(dimen, dimen)
                    .into(imageView);


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
