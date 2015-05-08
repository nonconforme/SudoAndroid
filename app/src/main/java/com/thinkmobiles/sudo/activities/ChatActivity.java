package com.thinkmobiles.sudo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.ToolbarManager;
import com.thinkmobiles.sudo.adapters.ChatListAdapter;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.models.DefaultResponseModel;
import com.thinkmobiles.sudo.models.chat.MessageModel;
import com.thinkmobiles.sudo.utils.ContactManager;
import com.thinkmobiles.sudo.utils.Utils;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by omar on 28.04.15.
 */
public class ChatActivity extends ActionBarActivity {

    private ListView lvChatList;
    private EditText etMessage;
    private Button btnSend;
    private String message;
    private ChatListAdapter chatListAdapter;
    private Callback<List<MessageModel>> mMessagesCB;
    private Callback<DefaultResponseModel> mSendMessageCB;
    private MessageModel thisChat;
    private Toolbar toolbar;
    private String mOwnerNumber;
    private String mCompanionNumber;


    public static final String CHAT_MODEL = "chat";
    public static final String BUNDLE = "bundle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();
        loadContent();
        initListeners();
        reloadContent();
        initGetMessageCB();
        initSendMessageCB();
        getMessages();
        this.overridePendingTransition(R.anim.anim_edit_profile_slide_in, R.anim.anim_view_profile_slide_out);
        ToolbarManager.getInstance(this).changeToolbarTitleAndIcon("Chat", R.drawable.ic_launcher);


    }

    private void initSendMessageCB() {
        mSendMessageCB = new Callback<DefaultResponseModel>() {
            @Override
            public void success(DefaultResponseModel defaultResponseModel, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        };
    }

    private void initGetMessageCB() {
        mMessagesCB = new Callback<List<MessageModel>>() {
            @Override
            public void success(List<MessageModel> messageModel, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_view_profile_slide_in, R.anim.anim_edit_profile_slide_out);
    }


    private void initComponents() {
        setContentView(R.layout.activity_chat);
        lvChatList = (ListView) findViewById(R.id.lvChatList);
        lvChatList.setDivider(null);
        lvChatList.setDividerHeight(0);
        etMessage = (EditText) findViewById(R.id.etMessage);
        btnSend = (Button) findViewById(R.id.btnSend);
        chatListAdapter = new ChatListAdapter(this);
        lvChatList.setAdapter(chatListAdapter);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void reloadContent() {
//        chatListAdapter.reloadContent(thisChat.getListMessages(), thisChat.getReceiver());
    }

    private void loadContent() {
        loadChat();
    }

    private void loadChat() {
        mOwnerNumber        = getIntent().getExtras().getBundle(BUNDLE).getString(Constants.FROM_NUMBER);
        mCompanionNumber    =  getIntent().getExtras().getBundle(BUNDLE).getString(Constants.TO_NUMBER);

    }


    private void initListeners() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = String.valueOf(etMessage.getText());
                if (Utils.checkString(message)) sendMessage(message);
            }
        });

    }

    private void sendMessage(final String _message) {
        RetrofitAdapter.getInterface().senMessage(mOwnerNumber, mCompanionNumber, _message, "sms" ,mSendMessageCB);
    }

    public static void launch(final Activity activity, final String _ownerNumber, final String _companionNumber) {

        Intent intent = new Intent(activity, ChatActivity.class);
        Bundle b = new Bundle();
        if (ContactManager.isMyNumber(_ownerNumber)) {
            b.putString(Constants.FROM_NUMBER, _ownerNumber);
            b.putString(Constants.TO_NUMBER, _companionNumber);
        } else {
            b.putString(Constants.FROM_NUMBER, _companionNumber  );
            b.putString(Constants.TO_NUMBER, _ownerNumber);
        }
        intent.putExtra(BUNDLE, b);
        activity.startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:

                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

   private void getMessages(){
       RetrofitAdapter.getInterface().getConversation(mOwnerNumber, mCompanionNumber, mMessagesCB);
   }
}
