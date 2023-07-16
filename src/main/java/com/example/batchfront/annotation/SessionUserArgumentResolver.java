package com.example.batchfront.annotation;

import com.example.batchfront.Entity.User;
import com.example.batchfront.annotation.SessionUser;
import com.example.batchfront.session.SessionStore;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public class SessionUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final SessionStore sessionStore;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SessionUser.class) && parameter.getGenericParameterType().equals(User.class);

    }

    @Override
    public User resolveArgument(MethodParameter parameter,
                                ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory) {
        HttpServletRequest req = webRequest.getNativeRequest(HttpServletRequest.class);
        String sessionName = parameter.getParameterAnnotation(SessionUser.class).value();

        Optional<Cookie> cacheCookie = findCookieByName(req.getCookies(), sessionName);

        String cacheKey = cacheCookie.map(Cookie::getValue).orElse("");
        return sessionStore.getUser(cacheKey);
    }

    private Optional<Cookie> findCookieByName(@Nullable Cookie[] cookies, String sessionName) {
        return Optional.ofNullable(cookies)
                .stream()
                .flatMap(Arrays::stream)
                .filter(cookie -> cookie.getName().equals(sessionName))
                .findFirst();
    }
}
