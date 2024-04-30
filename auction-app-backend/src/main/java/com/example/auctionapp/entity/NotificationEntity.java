package com.example.auctionapp.entity;

import com.example.auctionapp.model.Notification;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notification", schema = "auction_app")
public class NotificationEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "notification_id")
    private UUID notificationId;

    @Column(name = "message_content", columnDefinition = "TEXT")
    private String messageContent;

    @Column(name = "notification_time")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime notificationTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    public NotificationEntity() { }

    public NotificationEntity(
            final UUID notificationId,
            final String messageContent,
            final LocalDateTime notificationTime,
            final UserEntity userEntity,
            final ProductEntity productEntity) {
        this.notificationId = notificationId;
        this.messageContent = messageContent;
        this.notificationTime = notificationTime;
        this.userEntity = userEntity;
        this.productEntity = productEntity;
    }

    public Notification toDomainModel() {
        Notification notification = new Notification();

        notification.setNotificationId(this.notificationId);
        notification.setMessageContent(this.messageContent);
        notification.setNotificationTime(this.notificationTime);
        notification.setUserId(this.userEntity.getUserId());
        notification.setProductId(this.productEntity.getProductId());

        return notification;
    }

    public UUID getNotificationId() {
        return this.notificationId;
    }

    public void setNotificationId(final UUID notificationId) {
        this.notificationId = notificationId;
    }

    public String getMessageContent() {
        return this.messageContent;
    }

    public void setMessageContent(final String messageContent) {
        this.messageContent = messageContent;
    }

    public LocalDateTime getNotificationTime() {
        return this.notificationTime;
    }

    public void setNotificationTime(final LocalDateTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    public UserEntity getUser() {
        return this.userEntity;
    }

    public void setUser(final UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(final UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public ProductEntity getProductEntity() {
        return this.productEntity;
    }

    public void setProductEntity(final ProductEntity productEntity) {
        this.productEntity = productEntity;
    }
}
