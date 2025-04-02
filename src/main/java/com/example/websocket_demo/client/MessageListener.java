package com.example.websocket_demo.client;

import com.example.websocket_demo.model.Message;

import java.util.ArrayList;

public interface MessageListener {
    void onMessageReceived(Message message);
    void onActiveUsersUpdated(ArrayList<String> users);
}
