package com.thinkmobiles.sudo.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.activities.ChatActivity;
import com.thinkmobiles.sudo.core.APIConstants;
import com.thinkmobiles.sudo.global.CircleTransform;
import com.thinkmobiles.sudo.models.chat.LastChatsModel;
import com.thinkmobiles.sudo.models.chat.MessageModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by omar on 19.04.15.
 */
public class ChatsListAdapter extends BaseAdapter {

    private List<LastChatsModel> mLastChatModel;
    private LayoutInflater mInflater;
    private Activity mActivity;
    private CustomOnClickListener mItemOptionListeners;


    public ChatsListAdapter(Activity mActivity) {
        this.mActivity = mActivity;
        this.mLastChatModel = new ArrayList<>();
        mInflater = LayoutInflater.from(mActivity);
        mItemOptionListeners = new CustomOnClickListener();

    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    public void reloadList(List<LastChatsModel> chats) {
        this.mLastChatModel = chats;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mLastChatModel.size();
    }

    @Override
    public Object getItem(int i) {
        return mLastChatModel.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.list_item_chats, viewGroup, false);
            holder = initViewHolder(view);
            holder.ivOptions.setOnClickListener(mItemOptionListeners);
            holder.tvViewDetails.setOnClickListener(mItemOptionListeners);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        LastChatsModel thisChat = null;
        MessageModel lastMessage = null;


        if (mLastChatModel != null && mLastChatModel.size() > 0) thisChat = mLastChatModel.get(position);
        if (thisChat != null) {
            lastMessage = thisChat.getLastmessage();


            if (lastMessage != null) {
                holder.tvSenderNumber.setText(thisChat.getLastmessage().getOwner().getNumber());
                holder.tvReceiverNumber.setText("To " + thisChat.getLastmessage().getCompanion().getNumber
                        ());
                holder.tvItemTimedate.setText(thisChat.getLastmessage().getPostedDate());
                holder.tvMessagePreview.setText(thisChat.getLastmessage().getBody());
                holder.ivAvatar.setTag(position);
                setAvatar(holder.ivAvatar, thisChat.getLastmessage().getOwner().getAvatar(), position);

            }


        }

        holder.ivOptions.setTag(position);
        holder.tvViewDetails.setTag(position);


        return view;
    }


    private ViewHolder initViewHolder(View view) {
        ViewHolder holder = new ViewHolder();
         holder.tvSenderNumber = (TextView) view.findViewById(R.id.tvChatItemSenderNumber);
        holder.tvReceiverNumber = (TextView) view.findViewById(R.id.tvChatItemReceiverDetails);
        holder.tvMessagePreview = (TextView) view.findViewById(R.id.tvChatItemMessagePreview);
        holder.tvItemTimedate = (TextView) view.findViewById(R.id.tvChatItemTimedate);
        holder.tvViewDetails = (TextView) view.findViewById(R.id.tvChatItemViewDetails);
        holder.ivAvatar = (ImageView) view.findViewById(R.id.ivChatAvatar);
        holder.ivReply = (ImageView) view.findViewById(R.id.ivChatItemReply);
        holder.ivOptions = (ImageView) view.findViewById(R.id.ivChatItemOptions);
        return holder;

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


    private void startChatActivity(LastChatsModel chatModel) {
        ChatActivity.launch(mActivity, chatModel.getLastmessage().getOwner().getNumber(),  chatModel.getLastmessage().getCompanion().getNumber());
    }

    private class CustomOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();

            switch (view.getId()) {
                case R.id.ivChatItemOptions:

                    break;
                case R.id.tvChatItemViewDetails:

                    startChatActivity(mLastChatModel.get(position));


                    break;
            }

        }
    }

    private class ViewHolder {
        ImageView ivAvatar, ivReply, ivOptions;
        TextView   tvSenderNumber, tvReceiverNumber, tvMessagePreview, tvItemTimedate, tvViewDetails;

    }


}
