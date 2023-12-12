package com.example.collegeflight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.collegeflight.R;
import com.example.collegeflight.bean.ChatMessage;

import java.util.List;

public class ChatAdapter extends BaseAdapter {
    private List<ChatMessage> chatMessages;
    private LayoutInflater inflater;

    public ChatAdapter(Context context, List<ChatMessage> chatMessages) {
        this.inflater = LayoutInflater.from(context);
        this.chatMessages = chatMessages;
    }

    @Override
    public int getCount() {
        return chatMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return chatMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage message = chatMessages.get(position);
        TextView textView;
        if (message.getType() == ChatMessage.SEND) {
            convertView = inflater.inflate(R.layout.item_chat_send, parent, false);
            textView=convertView.findViewById(R.id.rightMsg);
        } else {
            convertView = inflater.inflate(R.layout.item_chat_receive, parent, false);
            textView=convertView.findViewById(R.id.leftMsg);
        }
        textView.setText(message.getMessage());
        return convertView;
    }
}
