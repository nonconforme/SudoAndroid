package com.thinkmobiles.sudo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.Utils;
import com.thinkmobiles.sudo.adapters.ChatListAdapter;
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
    private ChatListAdapter chatListAdapter;
    private ChatModel thisChat;
    private Toolbar toolbar;


    public static final String CHAT_MODEL = "chat";
    public static final String BUNDLE = "bundle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();
        loadContent();
        initListeners();
        reloadContent();
        this.overridePendingTransition(R.anim.anim_edit_profile_slide_in, R.anim.anim_view_profile_slide_out);
        ToolbarManager.getInstance(this).changeToolbarTitleAndIcon("Chat", R.drawable.ic_launcher);


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
        chatListAdapter.reloadContent(thisChat.getListMessages(), thisChat.getReceiver());
    }

    private void loadContent() {
        loadChat();
    }

    private void loadChat() {
        thisChat = (ChatModel) getIntent().getExtras().getBundle(BUNDLE).getSerializable(CHAT_MODEL);

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

    public static void launch(Activity activity, ChatModel chatModel) {

        Intent intent = new Intent(activity, ChatActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(CHAT_MODEL, chatModel);
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
}
