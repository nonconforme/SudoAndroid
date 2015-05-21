package com.thinkmobiles.sudo.utils;

import android.os.AsyncTask;
import android.widget.TextView;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.models.addressbook.NumberModel;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import com.thinkmobiles.sudo.models.chat.LastChatsModel;
import com.thinkmobiles.sudo.models.chat.MessageModel;

/**
 * Created by omar on 21.05.15.
 */
public class ChatsNameHelper {

    private static ChatsNameHelper chatsNameHelper;
   public static ChatsNameHelper getInstance(){
        if(chatsNameHelper != null)
        return chatsNameHelper;
        else{
            chatsNameHelper = new ChatsNameHelper();
            return chatsNameHelper;}
    }

    public   void setCompanionName(TextView textView,MessageModel messageModel){

        MyAsyncTask myAsyncTask = new MyAsyncTask(textView,messageModel);

        myAsyncTask.execute();
    }

    private   String getCompanionName(String companionNumber){


        for (UserModel contact :App.getContactsList())
        {
             for(NumberModel numberModel : contact.getNumbers()){
                 if(numberModel.getNumber().equalsIgnoreCase(companionNumber))
                     return contact.getCompanion();
             }
        }


        return "Unknown";
    }




    private   String  discoverCompanionNumber(MessageModel message) {

        String number ;

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


        if(!App.getCurrentMobile().equalsIgnoreCase(message.getCompanion().getNumber())){
            number = message.getCompanion().getNumber();
        }
        else{
            number = message.getOwner().getNumber();
        }
        return number;

    }



    private   class MyAsyncTask extends AsyncTask <Void,Void,String>{
        TextView textView;
        MessageModel messageModel;

        public MyAsyncTask(TextView textView,MessageModel messageModel){
            this.textView = textView;
            this.messageModel = messageModel;
        }
        @Override
        protected String doInBackground(Void... voids) {


            return getCompanionName(discoverCompanionNumber(messageModel));
        }

        @Override
        protected void onPostExecute(String s) {
            textView.setText(s);
        }
    }



}
