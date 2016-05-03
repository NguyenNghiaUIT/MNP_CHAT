package com.nghianv.mnp_chat.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.nghianv.mnp_chat.R;
import com.nghianv.mnp_chat.model.MessageChat;

import java.util.List;

/**
 * Created by NguyenNghia on 4/2/2016.
 */
public class ChatAdapter extends ArrayAdapter<MessageChat> {
    private Context mContext;
    private List<MessageChat> mMessageChats;
    private FrameLayout flContainer;

    public ChatAdapter(Context context, List<MessageChat> objects) {
        super(context, 0, objects);
        mContext = context;
        mMessageChats = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.row_chat, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvChat = (TextView) convertView.findViewById(R.id.txtMessageChat);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MessageChat messageChat = mMessageChats.get(position);

        viewHolder.tvChat.setText(messageChat.getMessage());

        if (messageChat.getIsMine()) {
            ((FrameLayout.LayoutParams) viewHolder.tvChat.getLayoutParams()).gravity = Gravity.RIGHT;
            if (Build.VERSION.SDK_INT >= 16) {
                viewHolder.tvChat.setBackground(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.state_item_chat_mine, mContext.getTheme()));
            } else {
                viewHolder.tvChat.setBackgroundDrawable(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.state_item_chat_mine, mContext.getTheme()));
            }
        } else {
            ((FrameLayout.LayoutParams) viewHolder.tvChat.getLayoutParams()).gravity = Gravity.LEFT;
            if (Build.VERSION.SDK_INT >= 16) {
                viewHolder.tvChat.setBackground(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.state_item_chat_friend, mContext.getTheme()));
            } else {
                viewHolder.tvChat.setBackgroundDrawable(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.state_item_chat_friend, mContext.getTheme()));
            }

        }
        return convertView;
    }

    public static class ViewHolder {
        TextView tvChat;
    }
}
