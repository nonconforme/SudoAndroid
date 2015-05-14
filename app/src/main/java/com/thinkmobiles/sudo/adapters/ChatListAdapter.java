package com.thinkmobiles.sudo.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    private int lastAnimatedPosition = -1;

    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;

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
            if (isIncomingMessage(position))
                view = mInflater.inflate(R.layout.list_item_chat_incoming, viewGroup, false);
            else view = mInflater.inflate(R.layout.list_item_chat_outgoing, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.setData(position);
        runEnterAnimation(view, i);

        return view;
    }

    private class ViewHolder {
        ImageView ivAvatar;
        TextView tvTimedate, tvMessage;

        public ViewHolder(final View _view){
            initHolder(_view);
        }

    private void initHolder(View _view){
        this.ivAvatar = (ImageView) _view.findViewById(R.id.ivAvatar);
        this.tvMessage = (TextView) _view.findViewById(R.id.tvChatText);
        this.tvTimedate = (TextView) _view.findViewById(R.id.tvTimeDate);
    }
    private void setData(final int _pos){
        setAvatar(this.ivAvatar, _pos);
        setMessage(this.tvMessage, _pos);
        setTimeDate(this.tvTimedate, _pos);
    }

    }

    private boolean isIncomingMessage(int position) {


        if (mListMessages.get(position).getOwner().getNumber().equals(mOwnerNumber)) return true;

        return false;

    }

    private void setAvatar(ImageView iv, int position) {
        iv.setTag(position);


    }

    private void setMessage(TextView tv, int position) {
        tv.setText(mListMessages.get(position).getBody());

    }

    private void setTimeDate(TextView tv, int position) {
        String timeDate = Utils.stringToDate(mListMessages.get(position).getPostedDate());
        tv.setText(timeDate);
    }
    public void updateItems() {

        notifyDataSetChanged();
    }
    public void addNewMessage(MessageModel newMessage) {

        mListMessages.add(0,newMessage);

        notifyDataSetChanged();
    }
    public void setAnimationsLocked(boolean animationsLocked) {
        this.animationsLocked = animationsLocked;
    }

    public void setDelayEnterAnimation(boolean delayEnterAnimation) {
        this.delayEnterAnimation = delayEnterAnimation;
    }
    private void runEnterAnimation(View view, int position) {
        if (animationsLocked) return;

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(100);
            view.setAlpha(0.f);
            view.animate()
                    .translationY(0).alpha(1.f)
                    .setStartDelay(delayEnterAnimation ? 20 * (position) : 0)
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationsLocked = true;
                        }
                    })
                    .start();
        }
    }
}
