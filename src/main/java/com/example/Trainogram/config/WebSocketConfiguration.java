package com.example.Trainogram.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(getChatWebSocketHandler(),CHAT_ENDPOINT).setAllowedOrigins("*");
    }

//    @Bean
//    public WebSocketHandler getChatWebSocketHandler(){
//        return new ChatWebSocketHandler();
//    }
    @Bean
    public ChatEndpoint chatEndpoint(){
        return new ChatEndpoint();
    }
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return  new ServerEndpointExporter();
    }

}
