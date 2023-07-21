package com.example.batchfront.controller;

import com.example.batchfront.config.SettlementNewProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoggingController {

    private final OkHttpClient client;
    private final SettlementNewProperties properties;

    @GetMapping("/logging")
    public String getLoggingSpec() {

        Request request = new Request.Builder()
                .url(properties.getUrl() + "/logging") // 요청할 URL 설정
                .get()
                .build();
        String responseData = "";
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                // 성공적인 응답 처리
                responseData = response.body().string();
            } else {
                // 에러 응답 처리
                System.out.println("에러 응답: " + response.code() + " " + response.message());
            }
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
        }
        return responseData;
    }

    @GetMapping("/log/clear")
    public String cleanUpLog() {
        return null;
    }
}
