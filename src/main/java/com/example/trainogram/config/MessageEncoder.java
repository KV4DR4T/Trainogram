package com.example.trainogram.config;


import com.example.trainogram.model.ChatMessage;
import com.google.gson.Gson;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<ChatMessage> {
    private static Gson gson = new Gson();

    @Override
    public String encode(ChatMessage chatMessage) throws EncodeException {
        return gson.toJson(chatMessage);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    @Override
    public void destroy() {
        // Close resources
    }
}
