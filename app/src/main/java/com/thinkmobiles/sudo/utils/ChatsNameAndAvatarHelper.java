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
import com.thinkmobiles.sudo.global.CircleTransform;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import com.thinkmobiles.sudo.models.chat.MessageModel;

/**
 * Created by omar on 21.05.15.
 */
public class ChatsNameAndAvatarHelper {

    private Context context;

    public ChatsNameAndAvatarHelper(Context context) {
        this.context = context;
    }


    public void setCompanionNameAndAvatar(TextView textView, ImageView imageView, MessageModel messageModel, int position) {

        MyAsyncTask myAsyncTask = new MyAsyncTask(context, textView, imageView, messageModel, position);

        myAsyncTask.execute();
    }


    private UserModel getCompanion(String companionNumber) {


        for (UserModel contact : App.getContactsList()) {
            for (NumberModel numberModel : contact.getNumbers()) {
                if (numberModel.getNumber().equalsIgnoreCase(companionNumber)) return contact;
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
        Context context;
        ImageView imageView;
        TextView textView;
        MessageModel messageModel;
        int pos;

        public MyAsyncTask(Context context, TextView textView, ImageView imageView, MessageModel messageModel, int pos) {

            this.context = context;
            this.imageView = imageView;
            this.textView = textView;
            this.messageModel = messageModel;
            this.pos = pos;
        }

        @Override
        protected UserModel doInBackground(Void... voids) {


            return getCompanion(discoverCompanionNumber(messageModel));
        }

        @Override
        protected void onPostExecute(UserModel companion) {

            textView.setText(getCompanionName(companion));
            setAvatar(context, imageView, companion, pos);
        }
    }

    private String getCompanionName(UserModel companion) {
        if (companion == null) return "Unknown";
        else return companion.getCompanion();
    }


    private void setAvatar(final Context mActivity, final ImageView imageView, UserModel companion, final int pos) {
        if (companion != null && !companion.getAvatar().equalsIgnoreCase("") && (int) imageView.getTag() == pos) {


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
}