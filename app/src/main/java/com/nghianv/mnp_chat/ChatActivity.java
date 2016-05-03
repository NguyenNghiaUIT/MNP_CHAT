package com.nghianv.mnp_chat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.nghianv.mnp_chat.adapter.ChatAdapter;
import com.nghianv.mnp_chat.model.Command;
import com.nghianv.mnp_chat.model.MessageChat;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements Client.OnRecievedMessage {


    private final String TAG = "ChatActivity";
    ListView lvChat;
    Button btnSend;
    EditText txtMessage;
    List<MessageChat> messageChats = new ArrayList<>();
    ChatAdapter adapter;
    Client mClient;
    String mCommand;
    boolean isRequireName = true;

    private static Handler handlerUpdateUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        //update ui on main thread
        handlerUpdateUI = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                adapter.notifyDataSetChanged();
            }
        };


        txtMessage = (EditText) findViewById(R.id.txtMessage);
        btnSend = (Button) findViewById(R.id.btnSend);
        lvChat = (ListView) findViewById(R.id.lvChat);
        adapter = new ChatAdapter(this, messageChats);
        lvChat.setAdapter(adapter);

        mClient = new Client();
        mClient.setOnRecievedMessage(this);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mClient.startClient();
            }
        });
        //start thread chat
        thread.start();


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contentChat = txtMessage.getText().toString();
                if (!contentChat.equals("")) {
                    if (isRequireName) {
                        isRequireName = false;
                        mClient.setName(contentChat);
                        mClient.sendNameToServer(contentChat);
                        messageChats.add(new MessageChat(contentChat, true));
                        handlerUpdateUI.sendEmptyMessage(0);
                    } else {
                        mClient.sendMessage(contentChat);
                        messageChats.add(new MessageChat(contentChat, true));
                    }
                    handlerUpdateUI.sendEmptyMessage(0);
                    txtMessage.setText("");
                }

            }
        });


//        lvChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(ChatActivity.this, messageChats.get(position).getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
    }

    @Override
    public void onRecieved(String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);
            mCommand = jsonObject.getString(Command.COMMAND_KEY);
            Log.i(TAG, mCommand);
            //Toast.makeText(this, mCommand, Toast.LENGTH_LONG).show();
            switch (mCommand) {
                case Command.SEND_ID:
                    mClient.setId(jsonObject.getString(Command.CONTENT_KEY));
                    break;
                case Command.SEND_REQUEST_NAME:
                    isRequireName = true;
                    messageChats.add(new MessageChat("Server: " + jsonObject.get(Command.CONTENT_KEY).toString(), false));
                    handlerUpdateUI.sendEmptyMessage(0);
                    break;
                case Command.SEND_MESSAGE:
                    messageChats.add(new MessageChat(jsonObject.getString(Command.NAME_KEY) + ": " + jsonObject.getString(Command.CONTENT_KEY) , false));
                    handlerUpdateUI.sendEmptyMessage(0);
                    break;
                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
