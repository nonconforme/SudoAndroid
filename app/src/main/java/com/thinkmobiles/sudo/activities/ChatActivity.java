package com.thinkmobiles.sudo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.utils.ToolbarManager;
import com.thinkmobiles.sudo.adapters.ChatListAdapter;
import com.thinkmobiles.sudo.core.APIConstants;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.models.DefaultResponseModel;
import com.thinkmobiles.sudo.models.chat.CompanionModel;
import com.thinkmobiles.sudo.models.chat.MessageModel;
import com.thinkmobiles.sudo.utils.ContactManager;
import com.thinkmobiles.sudo.utils.Utils;

import java.net.URISyntaxException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
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
    private ChatListAdapter mListAdapter;
    private Callback<List<MessageModel>> mMessagesCB;
    private Callback<DefaultResponseModel> mSendMessageCB;

    private Toolbar toolbar;

    private String mOwnerNumber;
    private String mCompanionNumber;
    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(APIConstants.SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    private MessageModel mSendMessageModel;
    private MessageModel mFirstMessageModel;


    public static final String BUNDLE = "bundle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();
        loadContent();
        initListeners();

        initGetMessageCB();
        initSendMessageCB();
        getMessages();
        initSocked();
        this.overridePendingTransition(R.anim.anim_edit_profile_slide_in, R.anim.anim_view_profile_slide_out);
        ToolbarManager.getInstance(this).changeToolbarTitleAndIcon("Chat", 0);

        mSocket.connect();
    }

    private void initSocked() {
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("receiveMessage", onReceive);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uId", App.getuId());
            mSocket.emit("authorize", jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Emitter.Listener onReceive = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            Gson gson = new Gson();
            final MessageModel message = gson.fromJson(data.toString(), MessageModel.class);
            Log.d("socket", "received");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mListAdapter.addNewMessage(message);
                }
            });

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.off("receiveMessage", onReceive);
        mSocket.disconnect();
    }

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

                    Toast.makeText(getApplicationContext(),
                            "Connection problims ", Toast.LENGTH_LONG).show();

        }
    };

    private void initSendMessageCB() {
        mSendMessageCB = new Callback<DefaultResponseModel>() {
            @Override
            public void success(DefaultResponseModel defaultResponseModel, Response response) {
                mListAdapter.addNewMessage(mSendMessageModel);
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
                if (messageModel.size() > 0) {
                    mFirstMessageModel = messageModel.get(0);
                    mListAdapter.reloadContent(messageModel, mOwnerNumber);

                }

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
        mListAdapter = new ChatListAdapter(this);
        lvChatList.setAdapter(mListAdapter);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void loadContent() {
        loadChat();
    }

    private void loadChat() {
        mOwnerNumber = getIntent().getExtras().getBundle(BUNDLE).getString(Constants.FROM_NUMBER);
        mCompanionNumber = getIntent().getExtras().getBundle(BUNDLE).getString(Constants.TO_NUMBER);

    }


    private void initListeners() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = String.valueOf(etMessage.getText());
                if (Utils.checkString(message)) sendMessage(message);
                etMessage.setText("");
            }
        });

    }

    private void sendMessage(final String _message) {
        RetrofitAdapter.getInterface().senMessage(mOwnerNumber, mCompanionNumber, _message, "sms", mSendMessageCB);
        setSendMessageModel(mOwnerNumber, mCompanionNumber, _message);
    }

    private void setSendMessageModel(String _mOwnerNumber, String _mCompanionNumber, String _message) {
        mSendMessageModel = new MessageModel();
        mSendMessageModel.setCompanion(createCompanion(_mCompanionNumber, mFirstMessageModel));
        mSendMessageModel.setOwner(createCompanion(_mOwnerNumber, mFirstMessageModel));
        mSendMessageModel.setPostedDate(Utils.getDateServerStyle());
        mSendMessageModel.setBody(_message);
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

    private void getMessages() {
        RetrofitAdapter.getInterface().getConversation(mOwnerNumber, mCompanionNumber, mMessagesCB);
    }

    private CompanionModel createCompanion(String number, MessageModel messageModel) {

        CompanionModel companionModel = new CompanionModel();
        companionModel.setNumber(number);

        if (messageModel != null) {

            if (number.equalsIgnoreCase(messageModel.getCompanion().getNumber()))
                companionModel.setAvatar(messageModel.getCompanion().getAvatar());
            else companionModel.setAvatar(messageModel.getOwner().getAvatar());
        }

        return companionModel;
    }


}
