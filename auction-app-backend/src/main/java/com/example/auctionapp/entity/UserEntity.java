package com.example.auctionapp.entity;

import com.example.auctionapp.entity.enums.UserRoles;
import com.example.auctionapp.model.User;
import com.example.auctionapp.util.builderpattern.GenericBuilder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Entity
@Table(name = "users", schema="auction_app")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRoles role;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_info_id")
    private PaymentInfoEntity paymentInfoEntity;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;

    @Column(name="is_active")
    private boolean isActive;

    public UserEntity() {}

    public UserEntity(final UUID userId,
                      final String firstName,
                      final String lastName,
                      final String password,
                      final String email,
                      final UserRoles role,
                      final PaymentInfoEntity paymentInfoEntity,
                      final String profilePicture,
                      final LocalDate dateOfBirth) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.role = role;
        this.paymentInfoEntity = paymentInfoEntity;
        this.profilePicture = profilePicture;
        this.dateOfBirth = dateOfBirth;
    }

    public User toDomainModel() {
        return GenericBuilder.of(User::new)
                .with(User::setUserId, this.userId)
                .with(User::setFirstName, this.firstName)
                .with(User::setLastName, this.lastName)
                .with(User::setEmail, this.email)
                .with(User::setRole, this.role)
                .with(User::setProfilePicture, this.profilePicture)
                .with(User::setPaymentInfoEntity, this.paymentInfoEntity)
                .with(User::setDateOfBirth, this.dateOfBirth)
                .build();
    }

    public UUID getUserId() {
        return this.userId;
    }

    public void setUserId(final UUID userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public UserRoles getRole() {
        return this.role;
    }

    public void setRole(final UserRoles role) {
        this.role = role;
    }

    public PaymentInfoEntity getPaymentInfo() {
        return this.paymentInfoEntity;
    }

    public void setPaymentInfo(final PaymentInfoEntity paymentInfoEntity) {
        this.paymentInfoEntity = paymentInfoEntity;
    }

    public PaymentInfoEntity getPaymentInfoEntity() {
        return this.paymentInfoEntity;
    }

    public void setPaymentInfoEntity(final PaymentInfoEntity paymentInfoEntity) {
        this.paymentInfoEntity = paymentInfoEntity;
    }

    public String getProfilePicture() {
        return this.profilePicture;
    }

    public void setProfilePicture(final String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(final LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean getActive() {
        return this.isActive;
    }

    public void setActive(final boolean active) {
        isActive = active;
    }
}
