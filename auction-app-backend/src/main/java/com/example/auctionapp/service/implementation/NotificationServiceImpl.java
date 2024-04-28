package com.example.auctionapp.service.implementation;

import com.example.auctionapp.entity.NotificationEntity;
import com.example.auctionapp.entity.UserEntity;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.repository.NotificationRepository;
import com.example.auctionapp.repository.UserRepository;
import com.example.auctionapp.service.NotificationService;
import com.example.auctionapp.websockets.MainSocketHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final MainSocketHandler mainSocketHandler;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationServiceImpl(final MainSocketHandler mainSocketHandler, NotificationRepository notificationRepository, UserRepository userRepository) {
        this.mainSocketHandler = mainSocketHandler;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void notifyUser(final UUID userId, final String message){
        NotificationEntity notificationEntity = new NotificationEntity();

        notificationEntity.setMessageContent(message);
        notificationEntity.setNotificationTime(LocalDateTime.now());

        final UserEntity userEntity = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User with the given ID does not exist"));

        notificationEntity.setUser(userEntity);

        this.notificationRepository.save(notificationEntity);
        this.mainSocketHandler.sendMessage(String.valueOf(userId), message);
    }
}
