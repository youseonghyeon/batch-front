package com.example.batchfront.Entity;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class User {

    private String username;
    private String password;
}
