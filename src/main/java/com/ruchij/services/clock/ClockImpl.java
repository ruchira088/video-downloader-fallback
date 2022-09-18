package com.ruchij.services.clock;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ClockImpl implements Clock {
    @Override
    public Instant timestamp() {
        return Instant.now();
    }
}
