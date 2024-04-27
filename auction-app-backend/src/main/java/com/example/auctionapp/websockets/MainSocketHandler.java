package com.example.auctionapp.websockets;

import com.example.auctionapp.entity.UserEntity;
import com.example.auctionapp.exceptions.GeneralException;
import com.example.auctionapp.model.User;
import com.example.auctionapp.service.implementation.JwtService;
import com.example.auctionapp.service.implementation.UserDetailsServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MainSocketHandler implements WebSocketHandler {
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userService;

    public Map<String, WebSocketSession> sessions = new HashMap<>();

    public MainSocketHandler(JwtService jwtService, UserDetailsServiceImpl userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
        final User user = getUser(session);

        if(user==null){
            return;
        }

        sessions.put(String.valueOf(user.getUserId()), session);

        // debugging
        System.out.println("Session created for the user" + user.getUserId() + "where the " +
                "session id is" + session.getId());
    }

    @Override
    public void handleTransportError(final WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("Error happened" + session.getId() + "with reason###" + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus closeStatus)
            throws Exception {
        System.out.println("Connection closed for session " + session.getId()
                + "with status: " + closeStatus.getReason());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    @Override
    public void handleMessage(final WebSocketSession session, final WebSocketMessage<?> message)
            throws Exception {
        final String messageReceived = (String) message.getPayload();
    }

    public void broadcastMessage(final String message) throws IOException {
        sessions.forEach((key, session) -> {
            try {
                if (session.isOpen()) session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void sendMessage(final String userId, String message) {
        final WebSocketSession session = sessions.get(userId);

        if (session == null) {
            return;
        }

        try {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(message));
            } else {
                System.out.println("Cannot send message. Session closed for user ID: " + userId); 
            }
        } catch (IOException e) {
            throw new GeneralException(e);
        }
    }

    private User getUser(final WebSocketSession session) throws IOException{
        final List<String> headers = session.getHandshakeHeaders().getOrEmpty("Authorization");

        if(headers.isEmpty()){
            session.close();
            return null;
        }

        final String jwt = headers.get(0).substring(7);
        final String userEmail= jwtService.extractUsername(jwt);

        final UserDetails userDetails = userService.loadUserByUsername(userEmail);

        return ((UserEntity) userDetails).toDomainModel();
    }
}
