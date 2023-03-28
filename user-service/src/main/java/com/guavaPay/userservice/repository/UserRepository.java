package com.guavaPay.userservice.repository;

import com.guavaPay.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Query(value = "update Users set access_token = ? where login = ?", nativeQuery = true)
    void updateAccessTokenByLogin(String accessToken, String login);
    @Modifying
    @Query(value = "update Users set access_token = ? where access_token = ?", nativeQuery = true)
    void updateToken(String token, String tokenLoc);
    Optional<User> findByLogin(String login);
    User getByLogin(String login);
    Optional<User> getByAccessToken(String token);
    Optional<User> getById(long id);
}