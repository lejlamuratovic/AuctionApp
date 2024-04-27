package com.example.auctionapp.service.implementation;

import com.example.auctionapp.service.NotificationService;
import com.example.auctionapp.websockets.MainSocketHandler;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final MainSocketHandler mainSocketHandler;

    public NotificationServiceImpl(final MainSocketHandler mainSocketHandler) {
        this.mainSocketHandler = mainSocketHandler;
    }

    @Override
    public void sendMessage(final String userId, final String message){
         mainSocketHandler.sendMessage(userId, message);
    }
}
