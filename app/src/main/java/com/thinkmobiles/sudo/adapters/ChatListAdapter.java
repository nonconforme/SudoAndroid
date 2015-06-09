package com.thinkmobiles.sudo.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.audio.VoiceMessagePlayer;
import com.thinkmobiles.sudo.audio.VoiceMessagePlayerCallback;
import com.thinkmobiles.sudo.core.APIConstants;
import com.thinkmobiles.sudo.models.chat.MessageModel;
import com.thinkmobiles.sudo.utils.Utils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by omar on 19.04.15.
 */
public class ChatListAdapter extends BaseAdapter implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private List<MessageModel> mListMessages;
    private LayoutInflater mInflater;
    private Context context;
    private String mOwnerNumber;
    private int lastAnimatedPosition = -1;

    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;
    private boolean selectionMode = false;
    private boolean[] selectionArray;
    private String mAvatarUrl;
    private Bitmap mBitmapAvatar;
    private ArrayList<ImageView> mIncomingAvatarArray;

    public ChatListAdapter(Context context, String mAvatarUrl) {
        this.context = context;
        this.mAvatarUrl = mAvatarUrl;
        mListMessages = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
        mIncomingAvatarArray = new ArrayList<>();

    }

    private void loadAvatar() {
        if (mAvatarUrl != null && !mAvatarUrl.equalsIgnoreCase("")) {
            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    mBitmapAvatar = bitmap;
                    for (ImageView imageView : mIncomingAvatarArray) {
                        imageView.setImageBitmap(bitmap);
                    }
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    mBitmapAvatar = null;
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };

            Picasso.with(context).load(APIConstants.SERVER_URL + "/" + mAvatarUrl).into(target);
        }

    }


    public void reloadContent(List<MessageModel> _mListMessages, String _mOwnerNumber) {
        mListMessages.clear();
        mListMessages.addAll(_mListMessages);
        mOwnerNumber = _mOwnerNumber;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return mListMessages.size();
    }

    @Override
    public MessageModel getItem(int position) {
        return mListMessages.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int position = getCount() - i - 1;
        if (isIncomingMessage(position)) view = mInflater.inflate(R.layout.list_item_chat_outgoing, viewGroup, false);
        else view = mInflater.inflate(R.layout.list_item_chat_incoming, viewGroup, false);

        ViewHolder holder = new ViewHolder(view);

        if (!isIncomingMessage(position)) holder.ivAvatar.setTag(0);
        else holder.ivAvatar.setTag(1);

        if (!isVoiceMessage(position)) holder.playerContainer.setVisibility(View.GONE);


        holder.setData(position, holder);
        runEnterAnimation(view, i);

        return view;
    }

    @Override
    public void onClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        int position = (int) holder.playerContainer.getTag();

        switch (view.getId()) {
            case R.id.ivPlay:
                String voiceURL = mListMessages.get(position).getVoiceURL();

                VoiceMessagePlayerCallback voiceMessagePlayerCallback = new VoiceMessagePlayerCallback(holder.seekBar, holder.tvAudioCurrnet);
                VoiceMessagePlayer.getInstance(context,voiceURL, voiceMessagePlayerCallback);
                break;
            case R.id.ivStop:
                break;
        }

    }


    private boolean isVoiceMessage(int position) {
        if (mListMessages.get(position).getType().equalsIgnoreCase("VOICE")) return true;
        return false;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    private class ViewHolder {
        ImageView ivAvatar;
        TextView tvTimedate, tvMessage, tvNumber;
        View container;

        View playerContainer;
        TextView tvAudioCurrnet;
        TextView tvAudioRemaining;
        SeekBar seekBar;
        ImageView ivPlay;
        ImageView ivStop;


        public ViewHolder(final View _view) {
            initHolder(_view);
        }

        private void initHolder(View _view) {
            this.ivAvatar = (ImageView) _view.findViewById(R.id.ivAvatar);
            this.tvMessage = (TextView) _view.findViewById(R.id.tvChatText);
            this.tvTimedate = (TextView) _view.findViewById(R.id.tvTimeDate);
            this.tvNumber = (TextView) _view.findViewById(R.id.tvCompanionNumber);
            this.container = _view.findViewById(R.id.container);

            this.playerContainer = _view.findViewById(R.id.playerContainer);
            this.seekBar = (SeekBar) _view.findViewById(R.id.seekBar);
            this.tvAudioCurrnet = (TextView) _view.findViewById(R.id.tvAudioCurrnet);
            this.tvAudioRemaining = (TextView) _view.findViewById(R.id.tvAudioRemaining);
            this.ivPlay = (ImageView) _view.findViewById(R.id.ivPlay);
            this.ivStop = (ImageView) _view.findViewById(R.id.ivStop);
        }

        private void setData(final int _pos, ViewHolder holder) {
            setAvatar(this.ivAvatar);
            setMessage(this.tvMessage, _pos);
            setTimeDate(this.tvTimedate, _pos);
            setNumber(this.tvNumber, _pos);
            setSelectionBG(this.container, _pos);

            this.ivPlay.setOnClickListener(ChatListAdapter.this);
            this.ivStop.setOnClickListener(ChatListAdapter.this);
            this.ivPlay.setTag(holder);
            this.ivStop.setTag(holder);
            this.playerContainer.setTag(_pos);

        }

    }

    private boolean isIncomingMessage(int position) {

        if (mListMessages.get(position).getOwner().getNumber().equals(mOwnerNumber)) return true;

        return false;
    }

    private void setMessage(TextView tv, int position) {
        tv.setText(mListMessages.get(position).getBody());

    }

    private void setNumber(TextView tv, int position) {
        tv.setText(mListMessages.get(position).getCompanion().getNumber());
    }

    private void setSelectionBG(View view, int position) {
        if (selectionMode && selectionArray != null && selectionArray[position])
            view.setBackground(context.getResources().getDrawable(R.drawable.bg_chats_item_long_pressed));

        else view.setBackground(context.getResources().getDrawable(R.drawable.bg_chats_item_default));

    }

    private void setTimeDate(TextView tv, int position) {
        String timeDate = Utils.stringToDate(mListMessages.get(position).getPostedDate());
        tv.setText(timeDate);
    }


    public void addNewMessage(MessageModel newMessage) {

        mListMessages.add(0, newMessage);
        notifyDataSetChanged();
    }

    private void runEnterAnimation(View view, int position) {
        if (animationsLocked) return;

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(100);
            view.setAlpha(0.f);
            view.animate().translationY(0).alpha(1.f).setStartDelay(delayEnterAnimation ? 20 * (position) : 0).setInterpolator(new DecelerateInterpolator(2.f)).setDuration(300).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    animationsLocked = true;
                }
            }).start();
        }
    }

    public List<MessageModel> getList() {
        return mListMessages;
    }

    public void setSelection(boolean selectionMode, boolean[] selectionArray) {
        if (selectionArray == null || selectionArray.length < 1) {
            this.selectionMode = false;
            notifyDataSetChanged();
            return;
        }
        this.selectionMode = selectionMode;
        this.selectionArray = selectionArray;
        notifyDataSetChanged();
    }


    private void setAvatar(final ImageView imageView) {

        int tag = (int) imageView.getTag();
        if (tag == 0) {
            loadAvatar();
            mIncomingAvatarArray.add(imageView);

            if (mBitmapAvatar != null) imageView.setImageBitmap(mBitmapAvatar);

        }

    }
}
