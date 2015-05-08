package com.thinkmobiles.sudo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.models.chat.MessageModel;
import com.thinkmobiles.sudo.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by omar on 19.04.15.
 */
public class ChatListAdapter extends BaseAdapter {

    private List<MessageModel> mListMessages;
    private LayoutInflater mInflater;
    private Context context;
    private   String mOwnerNumber;


    public ChatListAdapter(Context context) {
        this.context = context;
        mListMessages = new ArrayList<>();
        mInflater = LayoutInflater.from(context);

    }

    public void reloadContent(List<MessageModel> mListMessages, String mOwnerNumber) {
        this.mListMessages = mListMessages;
        this.mOwnerNumber = mOwnerNumber;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mListMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return mListMessages.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int position = getCount() - i - 1;
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();

            if (isIncomingMessage(position))
                view = mInflater.inflate(R.layout.list_item_chat_incoming, viewGroup, false);
            else view = mInflater.inflate(R.layout.list_item_chat_outgoing, viewGroup, false);

            holder.ivAvatar = (ImageView) view.findViewById(R.id.ivAvatar);
            holder.tvMessage = (TextView) view.findViewById(R.id.tvChatText);
            holder.tvTimedate = (TextView) view.findViewById(R.id.tvTimeDate);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        setAvatar(holder.ivAvatar, position);
        setMessage(holder.tvMessage, position);
        setTimeDate(holder.tvTimedate, position);


        return view;
    }

    private class ViewHolder {
        ImageView ivAvatar;
        TextView tvTimedate, tvMessage;

    }

    private boolean isIncomingMessage(int position) {


        if (mListMessages.get(position).getOwner().getNumber().equals(mOwnerNumber)) return true;

        return false;

    }

    private void setAvatar(ImageView iv, int position) {
        iv.setTag(position);
        Utils.setAvatar(context, iv, mListMessages.get(position).getOwner().getAvatar(), position);


    }

    private void setMessage(TextView tv, int position) {
        tv.setText(mListMessages.get(position).getBody());

    }

    private void setTimeDate(TextView tv, int position) {
        String timeDate = Utils.stringToDate(mListMessages.get(position).getPostedDate());
        tv.setText(timeDate);
    }

    public void addNewMessage(MessageModel newMessage) {

        mListMessages.add(0,newMessage);

        notifyDataSetChanged();
    }

}
