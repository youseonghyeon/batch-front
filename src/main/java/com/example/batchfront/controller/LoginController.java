package com.example.batchfront.controller;

import com.example.batchfront.Entity.User;
import com.example.batchfront.annotation.SessionUser;
import com.example.batchfront.dto.LoginRequest;
import com.example.batchfront.session.SessionStore;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final SessionStore sessionStore;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest, HttpServletResponse response, @CookieValue(value = "MY-SESSION-ID", required = false) String sid) {
        if (sid != null && sessionStore.isExist(sid)) {
            sessionStore.removeSession(sid);
        }

        log.info("loginRequest={}", loginRequest);
        User user = new User(loginRequest.getUsername(), loginRequest.getPassword());
        String sessionId = sessionStore.createSession(user);

        response.addCookie(new Cookie("MY-SESSION-ID", sessionId));
        return sessionId;
    }

    @PostMapping("/logout")
    public String logout(@CookieValue("MY-SESSION-ID") String sessionId) {
        log.info("logout sessionId={}", sessionId);
        sessionStore.removeSession(sessionId);
        return "logout";
    }
}
