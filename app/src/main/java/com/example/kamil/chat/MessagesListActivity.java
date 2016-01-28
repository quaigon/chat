package com.example.kamil.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboListActivity;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

public class MessagesListActivity extends RoboListActivity {


    private MessageAdapter messageAdapter;
    private List<Message> messages;



    @InjectExtra("userName")
    private String username;

    @InjectView(R.id.sendMessage)
    private Button sendButton;

    @InjectView(R.id.messageContent)
    private EditText messageContentEditText;

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String jsonMessage = intent.getStringExtra("message");
            Gson gson = new Gson();
            MessageContainer messageContainer = gson.fromJson(jsonMessage, MessageContainer.class);
            if (null != messageContainer) {
                Message m = messageContainer.getMessage();
                messages.add(m);
                messageAdapter.notifyDataSetChanged();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_list);
        messages = new ArrayList<>();

        messageAdapter = new MessageAdapter(this,messages);
        setListAdapter(messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = false;
                PostAsyncTask postAsyncTask = new PostAsyncTask(MessagesListActivity.this, username, messageContentEditText.getText().toString(), false);
                postAsyncTask.execute();
                messageContentEditText.setText("");
            }
        });

    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, new IntentFilter("MessageSent"));
        super.onResume();
    }




}
