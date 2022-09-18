package com.ruchij.web.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUserRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
