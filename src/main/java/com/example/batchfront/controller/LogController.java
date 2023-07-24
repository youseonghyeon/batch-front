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
public class LogController {

    private final OkHttpClient client;
    private final SettlementNewProperties properties;

    @GetMapping("/logging")
    public String getLoggingSpec() {

        Request request = new Request.Builder()
                .url(properties.getUrl() + "/logging")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                System.out.println("에러 응답: " + response.code() + " " + response.message());
                return "";
            }
        } catch (Exception e) {
            return "Internal Server Error.. ㅠㅠ";
        }
    }

}
