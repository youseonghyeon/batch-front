package com.example.batchfront.config;

import com.example.batchfront.annotation.SessionUserArgumentResolver;
import com.example.batchfront.session.SessionStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final SessionStore sessionStore;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // 허용할 클라이언트 도메인
                .allowedMethods("GET", "POST") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 허용할 요청 헤더
                .allowCredentials(true) // 인증정보 허용
                .maxAge(3600); // preflight 요청의 유효시간(초)
        WebMvcConfigurer.super.addCorsMappings(registry);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SessionUserArgumentResolver(sessionStore));
    }
}
