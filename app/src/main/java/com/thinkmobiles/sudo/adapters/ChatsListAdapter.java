package com.thinkmobiles.sudo.adapters;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.models.chat.LastChatsModel;
import com.thinkmobiles.sudo.models.chat.MessageModel;
import com.thinkmobiles.sudo.utils.Utils;


import java.util.ArrayList;

import java.util.List;


/**
 * Created by omar on 19.04.15.
 */
public class ChatsListAdapter extends BaseAdapter {

    private List<LastChatsModel> mLastChatModel;
    private LayoutInflater mInflater;
    private Activity mActivity;
    private boolean selectionMode = false;
    private boolean[] selectionArray;
 



    public ChatsListAdapter(Activity mActivity) {
        this.mActivity = mActivity;
        this.mLastChatModel = new ArrayList<>();
        mInflater = LayoutInflater.from(mActivity);


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

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        LastChatsModel thisChat = null;
        MessageModel lastMessage;


        if (mLastChatModel != null && mLastChatModel.size() > 0) thisChat = mLastChatModel.get(position);
        if (thisChat != null) {
            lastMessage = thisChat.getLastmessage();


            if (lastMessage != null) {
                holder.tvSenderNumber.setText(thisChat.getLastmessage().getOwner().getNumber());
                holder.tvReceiverNumber.setText("To " + thisChat.getLastmessage().getCompanion().getNumber());
                holder.tvItemTimedate.setText(Utils.stringToDate(thisChat.getLastmessage().getPostedDate()));
                holder.tvMessagePreview.setText(thisChat.getLastmessage().getBody());
                holder.ivAvatar.setTag(position);
                holder.tvCompanion.setText("");
                holder.ivAvatar.setTag(position);
                setSelectionState(holder.ivAvatar, holder.container, position);
            }


        }

        return view;
    }


    private ViewHolder initViewHolder(View view) {
        ViewHolder holder = new ViewHolder();
        holder.tvSenderNumber = (TextView) view.findViewById(R.id.tvChatItemSenderNumber);
        holder.tvReceiverNumber = (TextView) view.findViewById(R.id.tvChatItemReceiverDetails);
        holder.tvMessagePreview = (TextView) view.findViewById(R.id.tvChatItemMessagePreview);
        holder.tvItemTimedate = (TextView)  view.findViewById(R.id.tvChatItemTimedate);
        holder.tvCompanion = (TextView) view.findViewById(R.id.tvChatCompanion);
        holder.container = (LinearLayout) view.findViewById(R.id.llChatItemContainer);
        holder.ivAvatar = (ImageView) view.findViewById(R.id.ivChatAvatar);


         return holder;

    }


    private class ViewHolder {
        ImageView ivAvatar;
        TextView tvSenderNumber, tvReceiverNumber, tvMessagePreview, tvItemTimedate, tvCompanion;
        LinearLayout container;

    }



    public void setSelection(boolean    selectionMode, boolean [] selectionArray){
        this.selectionMode = selectionMode;
        this.selectionArray = selectionArray;
        notifyDataSetChanged();
    }

    public void setSelectionState(ImageView imageView, LinearLayout container, int position){
        if(selectionMode == true){

            if (selectionArray[position] && (int)imageView.getTag() == position) {
                imageView.setImageResource(R.drawable.ic_ua_ukraine);
                container.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_chats_item_long_pressed));
            }
            else{
                imageView.setImageResource(R.drawable.ic_man_chat);
                container.setBackground(mActivity.getResources().getDrawable(R.drawable.bg_chats_item_default));
            }
        }
        else{
            imageView.setImageResource(R.drawable.ic_man_chat);
        }

    }
}
