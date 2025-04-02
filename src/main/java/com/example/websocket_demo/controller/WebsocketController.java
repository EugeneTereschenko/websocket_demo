package com.example.websocket_demo.controller;

import com.example.websocket_demo.manager.WebSocketSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.websocket_demo.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final WebSocketSessionManager sessionManager;

    @Autowired
    public WebsocketController(SimpMessagingTemplate messagingTemplate, WebSocketSessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/message")
    public void handleMessage(Message message) {
        System.out.println("Received message: " + message.getUser() + " - " + message.getMessage());
        messagingTemplate.convertAndSend("/topic/messages", message);
        System.out.println("Sent message to /topic/messages: " + message.getUser() + " - " + message.getMessage());
    }

    @MessageMapping("/connect")
    public void connectUser(String username) {
        sessionManager.addUsername(username);
        sessionManager.broadcastActiveUsernames();
        System.out.println("User connected: " + username);
    }

    @MessageMapping("/disconnect")
    public void disconnectUser(String username) {
        sessionManager.removeUsername(username);
        sessionManager.broadcastActiveUsernames();
        System.out.println("User disconnected: " + username);
    }

    @MessageMapping("/request-users")
    public void requestUsers(){
        sessionManager.broadcastActiveUsernames();
        System.out.println("Requesting Users");
    }
}
