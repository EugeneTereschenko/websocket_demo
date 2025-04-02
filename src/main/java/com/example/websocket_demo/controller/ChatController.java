package com.example.websocket_demo.controller;

import com.example.websocket_demo.model.Message;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {

    private final List<String> Users;
    private final List<Message> messages;

    public ChatController() {
        this.Users = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("users", Users);
        model.addAttribute("messages", messages);
        model.addAttribute("message", new Message());
        return "index"; // Refers to "index.html" in src/main/resources/templates/
    }

    @PostMapping(value = "/saveMessage")
    public String saveMessage(@ModelAttribute("message") Message message, Model model) {
        // logic to process input data
        String username = message.getUser();
        String messageContent = message.getMessage();
        System.out.println("Received message: " + username + " - " + messageContent);
        messages.add(message);
       // model.addAttribute("message", new Message());
        return "redirect:/";
    }

    @PostMapping(value = "/addUser")
    public String addUser(HttpServletRequest request) {
        String username = request.getParameter("username");
        if (username != null && !username.trim().isEmpty()) {
            Users.add(username);
            System.out.println("User connected: " + username);
        }
        return "redirect:/";
    }

    @GetMapping("/messages")
    @ResponseBody
    public List<Message> getMessages() {
        return messages;
    }




}
