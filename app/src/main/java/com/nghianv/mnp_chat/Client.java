package com.nghianv.mnp_chat;

import android.util.Log;


import com.nghianv.mnp_chat.model.ChatObject;
import com.nghianv.mnp_chat.model.Command;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by NguyenNghia on 4/2/2016.
 */
public class Client {
    private final String TAG = Client.class.getSimpleName();
    private Socket mSocketClient;
    private BufferedReader in;
    private BufferedWriter out;
    private boolean mRunning;
    private OnRecievedMessage listener;
    private String serverMessage;
    private String mId;
    private String mName;


    public interface OnRecievedMessage{
        public void onRecieved(String message);
    }

    public void setOnRecievedMessage(OnRecievedMessage listener){
        this.listener = listener;
    }

    public Client(){
        mId = "";
        mName = "";
    }
    public Client(String id, String name){
        this.mId = id;
        this.mName = name;
    }

    public void setName(String name){
        this.mName = name;
    }

    public String getName(){
        return this.mName;
    }

    public void setId(String id){
        this.mId = id;
    }

    public String getId(){
        return this.mId;
    }

    public void startClient(){
        try {
            mSocketClient = new Socket(Config.HOST_IP, Config.HOST_PORT);
            Log.i(TAG, mSocketClient + "");
            Log.i(TAG, mSocketClient.getPort() + "--:--" + mSocketClient.getInetAddress() + ", " + mSocketClient.getLocalPort() + "," + mSocketClient.getLocalAddress() + "," + mSocketClient.getLocalSocketAddress());
            in = new BufferedReader(new InputStreamReader(mSocketClient.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(mSocketClient.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRunning = true;
        try {
            while(mRunning) {
                if ((serverMessage = in.readLine()) != null && listener != null) {
                    listener.onRecieved(serverMessage);
                    serverMessage = null;
                }
            }
            in.close();
            out.close();
            mSocketClient.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeClient(){
        mRunning = false;
        if(mSocketClient != null){
            try {
                out.close();
                in.close();
                mSocketClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            try {
                ChatObject chatObject = new ChatObject(Command.SEND_MESSAGE, mName, message);
                out.write(chatObject.getChatString());
                out.newLine();
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendNameToServer(String name){
        if (out != null) {
            try {
                ChatObject chatObject = new ChatObject(Command.SEND_REQUEST_NAME, mName, name);
                out.write(chatObject.getChatString());
                out.newLine();
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
