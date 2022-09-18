package com.ruchij.web.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserRequest {
    private String email;
    private String firstName;
    private String lastName;
}
