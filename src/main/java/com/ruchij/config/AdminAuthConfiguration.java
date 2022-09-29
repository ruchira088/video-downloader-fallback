package com.ruchij.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AdminAuthConfiguration {

    @Value("${admin.authentication.bearerToken}")
    private String bearerToken;

}
