package com.ruchij.services.generator;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UuidGenerator implements IdGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
