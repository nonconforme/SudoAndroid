package com.thinkmobiles.sudo.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.core.APIConstants;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import com.thinkmobiles.sudo.models.chat.MessageModel;

import java.util.HashMap;

/**
 * Created by omar on 21.05.15.
 */
public class ChatsNameAndAvatarHelper {

    private Context context;

    public ChatsNameAndAvatarHelper(Context context) {
        this.context = context;
    }


    public void setCompanionNameAndAvatar(TextView textView, ImageView imageView, MessageModel messageModel, int position, HashMap<Integer, String> mAvatarUrlMap) {

        MyAsyncTask myAsyncTask = new MyAsyncTask(context, textView, imageView, messageModel, position, mAvatarUrlMap);
        myAsyncTask.execute();
    }


    private UserModel getCompanion(String companionNumber) {
        if (App.getContactsList() != null) {
            for (UserModel contact : App.getContactsList()) {
                for (NumberModel numberModel : contact.getNumbers()) {
                    if (numberModel.getNumber().equalsIgnoreCase(companionNumber)) return contact;
                }
            }
        }
        return null;
    }


    private String discoverCompanionNumber(MessageModel message) {
        String number;
        for (NumberModel numberModel : App.getCurrentUser().getNumbers()) {
            if (numberModel.getNumber().equalsIgnoreCase(message.getCompanion().getNumber())) {

                number = message.getOwner().getNumber();
                return number;
            }
        }
        for (NumberModel numberModel : App.getCurrentUser().getNumbers()) {
            if (numberModel.getNumber().equalsIgnoreCase(message.getOwner().getNumber())) {

                number = message.getCompanion().getNumber();
                return number;
            }
        }

        if (!App.getCurrentMobile().equalsIgnoreCase(message.getCompanion().getNumber())) {
            number = message.getCompanion().getNumber();
        } else {
            number = message.getOwner().getNumber();
        }
        return number;

    }


    private class MyAsyncTask extends AsyncTask<Void, Void, UserModel> {

        private Context context;
        private ImageView imageView;
        private TextView textView;
        private MessageModel messageModel;
        private HashMap<Integer, String> mAvatarUrlMap;
        private int pos;

        public MyAsyncTask(Context context, TextView textView, ImageView imageView, MessageModel messageModel, int pos, HashMap<Integer, String> mAvatarUrlMap) {
            this.context = context;
            this.imageView = imageView;
            this.textView = textView;
            this.messageModel = messageModel;
            this.pos = pos;
            this.mAvatarUrlMap = mAvatarUrlMap;
        }

        @Override
        protected UserModel doInBackground(Void... voids) {
            return getCompanion(discoverCompanionNumber(messageModel));
        }

        @Override
        protected void onPostExecute(UserModel companion) {
            textView.setText(getCompanionName(companion));
            if (companion != null && !companion.getAvatar().equalsIgnoreCase("") && (int) imageView.getTag() == pos) {
                setAvatar(context, imageView, companion, pos);
                mAvatarUrlMap.put(pos, companion.getAvatar());
            }

        }
    }

    private String getCompanionName(UserModel companion) {
        if (companion == null) return context.getString(R.string.unknown);
        else return companion.getCompanion();
    }

    private void setAvatar(final Context mActivity, final ImageView imageView, UserModel companion, final int pos) {
        Picasso.with(mActivity).load(APIConstants.SERVER_URL + "/" + companion.getAvatar()).transform(new CircleTransform()).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {
                Picasso.with(mActivity).load(R.drawable.ic_man).transform(new CircleTransform()).into(imageView);
            }
        });


    }
}