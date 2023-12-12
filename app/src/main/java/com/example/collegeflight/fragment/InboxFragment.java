package com.example.collegeflight.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.collegeflight.ChatActivity;
import com.example.collegeflight.R;


public class InboxFragment extends Fragment {
    private int[] messageIds = {R.id.tvInboxMessage1, R.id.tvInboxMessage2, R.id.tvInboxMessage3,
            R.id.tvInboxMessage4, R.id.tvInboxMessage5};
    private int[] queryTypes = {1, 2, 3, 4, 5};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);

        for (int i = 0; i < messageIds.length; i++) {
            TextView tvInboxMessage = view.findViewById(messageIds[i]);
            int queryType = queryTypes[i];
            tvInboxMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    intent.putExtra("queryType", queryType);
                    startActivity(intent);
                }
            });
        }

        return view;
    }

}
