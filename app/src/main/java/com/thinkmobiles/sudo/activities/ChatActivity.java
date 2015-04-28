package com.thinkmobiles.sudo.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.Utils;
import com.thinkmobiles.sudo.adapters.ChatsListAdapter;
import com.thinkmobiles.sudo.models.addressbook.UserModel;
import com.thinkmobiles.sudo.models.chat.ChatModel;

/**
 * Created by omar on 28.04.15.
 */
public class ChatActivity extends ActionBarActivity {

    private ListView lvChatList;
    private EditText etMessage;
    private Button btnSend;
    private String message;

    private ChatModel thisChat;
    private UserModel thisUser;

    public static final String CHAT_MODEL = "chat";
    public static final String USER_MODEL = "user_model";
    public static final String BUNDLE = "bundle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initComponents();
        setContent();
        initListeners();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void initComponents() {
        setContentView(R.layout.activity_chat);
        lvChatList = (ListView) findViewById(R.id.lvChatList);
        etMessage = (EditText) findViewById(R.id.etMessage);
        btnSend = (Button) findViewById(R.id.btnSend);

    }

    private void setContent() {
        loadChat();
        loadUser();

    }

    private void loadChat() {
        thisChat = (ChatModel) getIntent().getExtras().getBundle(BUNDLE).getSerializable(CHAT_MODEL);

    }

    private void loadUser() {
        thisChat = (ChatModel) getIntent().getExtras().getBundle(BUNDLE).getSerializable(USER_MODEL);

    }

    private void initListeners() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = String.valueOf(etMessage.getText());
                if (Utils.checkString(message)) sendMessage();
            }
        });

    }

    private void sendMessage() {
    }


}
