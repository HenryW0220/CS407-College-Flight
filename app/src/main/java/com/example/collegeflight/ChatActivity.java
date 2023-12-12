package com.example.collegeflight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.collegeflight.adapter.ChatAdapter;
import com.example.collegeflight.bean.ChatMessage;
import com.example.collegeflight.bean.Order;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ListView listView;
    private List<ChatMessage> chatMessages;
    private ChatAdapter adapter;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Customer Service");
        }

        listView = findViewById(R.id.listViewChat);
        editText = findViewById(R.id.editTextChatInput);
        Button sendButton = findViewById(R.id.buttonChatSend);

        chatMessages = new ArrayList<>();
        adapter = new ChatAdapter(this, chatMessages);
        listView.setAdapter(adapter);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMessage = editText.getText().toString();
                if (!userMessage.isEmpty()) {
                    chatMessages.add(new ChatMessage(ChatMessage.SEND, userMessage));
                    editText.setText("");
                    adapter.notifyDataSetChanged();
                    listView.setSelection(chatMessages.size() - 1);

                    int queryType = getIntent().getIntExtra("queryType", -1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String autoReplyMessage = generateAutoReply(queryType);
                            chatMessages.add(new ChatMessage(ChatMessage.RECEIVE, autoReplyMessage));
                            adapter.notifyDataSetChanged();
                            listView.setSelection(chatMessages.size() - 1);
                        }
                    }, 1000);
                }
            }
        });


    }
    private String generateAutoReply(int queryType) {
        String userId = getUserIdFromPreferences();
        DBHelper dbHelper = new DBHelper(this);

        switch (queryType) {
            case 1:
                List<Order> orders = dbHelper.getOrdersByUserId(userId);
                if (!orders.isEmpty()) {
                    Order firstOrder = orders.get(0);
                    return "Your ticket details:\n" +
                            "Departure: " + firstOrder.getDepartureCity() + "\n" +
                            "Destination: " + firstOrder.getDestinationCity() + "\n" +
                            "Departure Time: " + firstOrder.getDepartureTime() + "\n" +
                            "Arrival Time: " + firstOrder.getArrivalTime() + "\n" +
                            "Duration: " + firstOrder.getTotalDuration() + "\n" +
                            "Flight Date: " + firstOrder.getFlightDate();
                } else {
                    return "You have not booked any tickets yet.";
                }
            case 2:
                return "Venture to the charming streets of Savannah, Georgia, or the pristine beaches of Oregon's coastline. Experience the vibrant cultural tapestry of New Orleans' French Quarter and the breathtaking views of Montana's Glacier National Park. Immerse yourself in the natural beauty of the Great Smoky Mountains or explore the historic landmarks of Boston. From the sun-kissed vineyards of Napa Valley to the rugged wilderness of Alaska, the United States offers a multitude of unforgettable adventures. Embark on a journey through these lesser-known yet spectacular locales for an authentic American experience!";
            case 3:
                return "Experience the joy of travel without breaking the bank with the latest flight discounts! Seize exceptional deals on airfare, unlocking destinations worldwide at unbeatable prices. Whether planning a spontaneous getaway or a long-awaited vacation, these discounts make it easier to explore new cities, cultures, and landscapes. Ideal for budget-conscious travelers seeking adventure, these offers provide an opportunity to create unforgettable memories without straining your wallet. Book now and embark on your dream journey, taking advantage of these limited-time flight specials. Your next great adventure awaits, more affordable than ever!";
            case 4:
                return "UW students, take off with amazing flight specials! Exclusive airfare discounts await, making your dream destinations more accessible. Whether it's a spring break adventure or a journey home, travel economically with these special deals tailored just for you. Embrace the freedom to explore and connect without straining your budget. Book now and soar into new experiences with your fellow Huskies. Fly smarter, not harder, with UW student flight specials! ";
            case 5:
                return "For most refund and change requests, we have a new manual customer service hotline. If you feel that AI is not enough for your needs, please call 608-769-2048 and we will provide human service for you.";
            default:
                return "Can I help you?";
        }
    }


    private String getUserIdFromPreferences() {
        SharedPreferences preferences = getSharedPreferences("user_preferences", MODE_PRIVATE);
        return preferences.getString("userId", null);
    }
}