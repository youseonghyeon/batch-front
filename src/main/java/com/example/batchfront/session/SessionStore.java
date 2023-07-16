package com.example.batchfront.session;


import com.example.batchfront.Entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class SessionStore {

    private Map<String, MySession> cache = new HashMap<>();

    private final int SESSION_TIMEOUT = 30;

    public String createSession(User user) {
        String sessionId = UUID.randomUUID().toString().toUpperCase();
        cache.put(sessionId, new MySession(user));
        return sessionId;
    }

    public User getUser(String sessionId) {
        MySession session = getSession(sessionId);
        if (session == null) {
            return null;
        }
        return session.getUser();
    }

    public MySession getSession(String sessionId) {
        MySession session = cache.get(sessionId);
        if (session == null || session.getCreateTime().isBefore(LocalDateTime.now().minusMinutes(SESSION_TIMEOUT))) {
            cache.remove(sessionId);
            return null;
        }
        return session;
    }

    public boolean isExist(String sessionId) {
        return cache.containsKey(sessionId);
    }

    public void removeSession(String sessionId) {
        cache.remove(sessionId);
    }
}
