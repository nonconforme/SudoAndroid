package com.thinkmobiles.sudo.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.*;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.thinkmobiles.sudo.R;
import com.thinkmobiles.sudo.adapters.ChatListAdapter;
import com.thinkmobiles.sudo.core.APIConstants;
import com.thinkmobiles.sudo.core.rest.RetrofitAdapter;
import com.thinkmobiles.sudo.custom_views.SendCommentButton;
import com.thinkmobiles.sudo.global.App;
import com.thinkmobiles.sudo.global.Constants;
import com.thinkmobiles.sudo.global.Network;
import com.thinkmobiles.sudo.models.DefaultResponseModel;
import com.thinkmobiles.sudo.models.chat.CompanionModel;
import com.thinkmobiles.sudo.models.chat.MessageModel;
import com.thinkmobiles.sudo.utils.ChatSelectionHelper;
import com.thinkmobiles.sudo.utils.ContactManager;
import com.thinkmobiles.sudo.utils.ToolbarManager;
import com.thinkmobiles.sudo.utils.Utils;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by omar on 28.04.15.
 */
public class ChatActivity extends ActionBarActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener, SendCommentButton.OnSendClickListener {
    public static final String ARG_DRAWING_START_LOCATION = "arg_drawing_start_location";

    private ListView mChatList;
    private EditText etMessage;
    private SendCommentButton btnSend;
    private String message;
    private RelativeLayout contentRoot, rlAddComment;
    private ChatListAdapter mListAdapter;
    private Callback<List<MessageModel>> mMessagesCB;
    private Callback<DefaultResponseModel> mSendMessageCB;
    private Callback<DefaultResponseModel> mDeleteMessageCB;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MenuItem menuItemDelete;
    private Toolbar toolbar;
    private Socket mSocket;

    private List<MessageModel> mMessageModelList;
    private MessageModel mSendMessageModel;
    private MessageModel mFirstMessageModel;
    private ChatSelectionHelper mSelectionHelper;

    private int drawingStartLocation;
    /*  private boolean selectMode = false;
      private boolean[] selectionArray;*/
    private int mPageCount = 0;
    private int mLength = 6;
    private String mOwnerNumber;
    private String mCompanionNumber;
    private String mAvatarUrl;


    private void initSelectionHelper() {
        mSelectionHelper = new ChatSelectionHelper() {


            @Override
            public void StopParentSelectionMode() {
                stopSelectionMode();
            }
        };
    }

    private void createSocket() {
        try {
            mSocket = IO.socket(APIConstants.SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!mSelectionHelper.isSelectionMode()) menuItemDelete.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);//Menu Resource, Menu
        menuItemDelete = menu.findItem(R.id.action_detele);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSelectionHelper();
        createSocket();
        loadContent();
        initComponents();
        initListeners();
        setSwipeRefrechLayoutListenerAndColor();
        initGetMessageCB();
        initSendMessageCB();
        initDeleteMessageCB();
        getMessagesPages();
        initSocked();
        initDrawingStartLockation();
        initContentObserver(savedInstanceState);

        ToolbarManager.getInstance(this).changeToolbarTitleAndIcon(getString(R.string.chat_title), 0);

    }

    private void initContentObserver(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            contentRoot.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    contentRoot.getViewTreeObserver().removeOnPreDrawListener(this);
                    startIntroAnimation();
                    return true;
                }
            });
        }
    }


    private void initDrawingStartLockation() {
        drawingStartLocation = getIntent().getIntExtra(ARG_DRAWING_START_LOCATION, 0);
    }

    private void initDeleteMessageCB() {
        mDeleteMessageCB = new Callback<DefaultResponseModel>() {
            @Override
            public void success(DefaultResponseModel defaultResponseModel, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        };
    }


    private void initSocked() {
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on(Constants.SOCKET_RECEIVE_MESSAGE, onReceive);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Constants.UID, App.getuId());
            mSocket.emit(Constants.AUTHORIZE, jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSocket.connect();
    }

    private Emitter.Listener onReceive = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            Gson gson = new Gson();
            final MessageModel message = gson.fromJson(data.toString(), MessageModel.class);


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mListAdapter.addNewMessage(message);
                    mSelectionHelper.addNewMessage(message);
                    mMessageModelList.add(0, mSendMessageModel);
                    mChatList.smoothScrollToPosition(mListAdapter.getCount() - 1);
                }
            });

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.off(Constants.SOCKET_RECEIVE_MESSAGE, onReceive);
        mSocket.disconnect();
    }

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), getString(R.string.connetcion_problems), Toast.LENGTH_LONG).show();
                }
            });


        }
    };

    private void initSendMessageCB() {
        mSendMessageCB = new Callback<DefaultResponseModel>() {
            @Override
            public void success(DefaultResponseModel defaultResponseModel, Response response) {
                if (mListAdapter.getCount() == 0) {
                    mOwnerNumber = App.getCurrentMobile();
                    mListAdapter.reloadContent(mMessageModelList, mOwnerNumber);
                }
                mListAdapter.addNewMessage(mSendMessageModel);
                mMessageModelList.add(0, mSendMessageModel);
                mChatList.smoothScrollToPosition(mListAdapter.getCount() - 1);
                btnSend.setCurrentState(SendCommentButton.STATE_DONE);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(ChatActivity.this, getString(R.string.check_contact_phone), Toast.LENGTH_SHORT).show();
            }
        };
    }


    private void initGetMessageCB() {
        mMessagesCB = new Callback<List<MessageModel>>() {
            @Override
            public void success(List<MessageModel> messageModel, Response response) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (messageModel.size() > 0) {
                    if (mPageCount < 2) {
                        mFirstMessageModel = messageModel.get(0);
                    }
                    mMessageModelList.addAll(messageModel);
                    mListAdapter.reloadContent(mMessageModelList, mOwnerNumber);

                    mChatList.setSelection(messageModel.size());
                    if (messageModel.size() > 0) mChatList.smoothScrollToPosition(messageModel.size() - 1);

                }

            }

            @Override
            public void failure(RetrofitError error) {
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(ChatActivity.this, getString(R.string.error_sending_message), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void onBackPressed() {
        contentRoot.animate().translationY(Utils.getScreenHeight(this)).setDuration(200).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
                overridePendingTransition(0, 0);
            }
        }).start();
    }

    private void initList() {
        mChatList = (ListView) findViewById(R.id.lvChatList);
        mChatList.setDivider(null);
        mChatList.setDividerHeight(0);
        mListAdapter = new ChatListAdapter(this, mAvatarUrl);
        mChatList.setAdapter(mListAdapter);
    }


    private void initComponents() {
        setContentView(R.layout.activity_chat);

        initList();

        contentRoot = (RelativeLayout) findViewById(R.id.rlMain_AC);
        rlAddComment = (RelativeLayout) findViewById(R.id.rlAddComment_AC);
        etMessage = (EditText) findViewById(R.id.etMessage);
        btnSend = (SendCommentButton) findViewById(R.id.btnSendComment);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout_AC);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void loadContent() {
        loadChat();
    }

    private void loadChat() {
        mOwnerNumber = getIntent().getExtras().getBundle(Constants.BUNDLE).getString(Constants.FROM_NUMBER);
        mCompanionNumber = getIntent().getExtras().getBundle(Constants.BUNDLE).getString(Constants.TO_NUMBER);
        mAvatarUrl = getIntent().getExtras().getBundle(Constants.BUNDLE).getString(Constants.AVATAR);
        mMessageModelList = new ArrayList<>();
    }


    private void initListeners() {
        btnSend.setOnSendClickListener(this);
        btnSend.setOnSendClickListener(this);
        mChatList.setOnItemLongClickListener(this);
        mChatList.setOnItemClickListener(this);
        mChatList.setOnScrollListener(this);

    }


    private void sendMessage(final String _message) {
        RetrofitAdapter.getInterface().senMessage(mOwnerNumber, mCompanionNumber, _message, Constants.SMS, mSendMessageCB);
        setSendMessageModel(mOwnerNumber, mCompanionNumber, _message);
    }

    private void setSendMessageModel(String _mOwnerNumber, String _mCompanionNumber, String _message) {
        mSendMessageModel = new MessageModel();
        mSendMessageModel.setCompanion(createCompanion(_mCompanionNumber, mFirstMessageModel));
        mSendMessageModel.setOwner(createCompanion(_mOwnerNumber, mFirstMessageModel));
        mSendMessageModel.setPostedDate(Utils.getDateServerStyle());
        mSendMessageModel.setBody(_message);
    }

    public static void launch(final Activity activity, final String _ownerNumber, final String _companionNumber, final String companionAvatar, int[] _startingLocation) {
        final Intent intent = new Intent(activity, ChatActivity.class);
        intent.putExtra(ChatActivity.ARG_DRAWING_START_LOCATION, _startingLocation[1]);
        Bundle b = new Bundle();
        if (ContactManager.isMyNumber(_ownerNumber)) {
            b.putString(Constants.FROM_NUMBER, _ownerNumber);
            b.putString(Constants.TO_NUMBER, _companionNumber);
            b.putString(Constants.AVATAR, companionAvatar);

        } else {
            b.putString(Constants.FROM_NUMBER, _companionNumber);
            b.putString(Constants.TO_NUMBER, _ownerNumber);
            b.putString(Constants.AVATAR, companionAvatar);
        }
        intent.putExtra(Constants.BUNDLE, b);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);

    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {

        if (!mSelectionHelper.isSelectionMode()) menuItemDelete.setVisible(false);
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                if (!mSelectionHelper.isSelectionMode()) {
                    onBackPressed();
                } else {
                    stopSelectionMode();
                }
                break;
            case R.id.action_detele:
                deleteChatItems();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void getMessagesPages() {
        mPageCount++;
        RetrofitAdapter.getInterface().getConversationPages(mOwnerNumber, mCompanionNumber, mPageCount, mLength, mMessagesCB);
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


    private void startIntroAnimation() {
        ViewCompat.setElevation(toolbar, 0);
        contentRoot.setScaleY(0.1f);
        contentRoot.setPivotY(drawingStartLocation);
        rlAddComment.setTranslationY(200);

        contentRoot.animate().scaleY(1).setDuration(200).setInterpolator(new AccelerateInterpolator()).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ViewCompat.setElevation(toolbar, Utils.dpToPx(8));
                animateContent();

            }
        }).start();
    }

    private void animateContent() {
        rlAddComment.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).setDuration(200).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Network.isInternetConnectionAvailable(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        startSelectionMode();
        mSelectionHelper.addToSelection(position);
        mListAdapter.setSelection(mSelectionHelper.isSelectionMode(), mSelectionHelper.getSelection());


        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (mSelectionHelper.isSelectionMode()) {
            mSelectionHelper.addToSelection(position);
            mSelectionHelper.checkSelectionNotEmpty();
        }
        mListAdapter.setSelection(mSelectionHelper.isSelectionMode(), mSelectionHelper.getSelection());
    }


    private void startSelectionMode() {
        mSelectionHelper.startSelectionMode(mMessageModelList);
        invalidateOptionsMenu();


    }

    private void stopSelectionMode() {
        mSelectionHelper.stopSelectionMode();
        mListAdapter.setSelection(mSelectionHelper.isSelectionMode(), mSelectionHelper.getSelection());
        invalidateOptionsMenu();


    }

    private void deleteChatItems() {
        if (!mSelectionHelper.isSelectionMode()) {
            mSelectionHelper.splitList();
            mListAdapter.reloadContent(mSelectionHelper.getRemainList(), mOwnerNumber);
        }
        stopSelectionMode();

    }


    private void setSwipeRefrechLayoutListenerAndColor() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMessagesPages();
                stopSelectionMode();
            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        if (canScrollUp(mChatList)) {
            mSwipeRefreshLayout.setEnabled(false);
        } else {
            mSwipeRefreshLayout.setEnabled(true);
        }

    }

    public boolean canScrollUp(View view) {
        return ViewCompat.canScrollVertically(view, -1);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onSendClickListener(View v) {
        message = String.valueOf(etMessage.getText());
        if (Utils.checkString(message)) {
            sendMessage(message);
        } else {
            btnSend.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
        }
        etMessage.setText("");
        stopSelectionMode();
    }
}
