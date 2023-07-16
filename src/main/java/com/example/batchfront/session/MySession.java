package com.example.batchfront.session;

import com.example.batchfront.Entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
class MySession {

    private User user;
    private LocalDateTime createTime;
    private LocalDateTime lastAccessTime;
    private SessionMetadata metadata;


    public MySession(User user) {
        this.user = user;
        this.createTime = LocalDateTime.now();
        this.lastAccessTime = LocalDateTime.now();
    }
}
