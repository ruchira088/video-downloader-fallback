package com.ruchij.services.schedules;

import com.ruchij.daos.schedules.models.ScheduledUrl;
import com.ruchij.exceptions.ResourceConflictException;
import com.ruchij.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface SchedulingService {
    ScheduledUrl insert(String url, String userId) throws ResourceConflictException;

    Optional<ScheduledUrl> findByUrl(String url, String userId);

    ScheduledUrl getById(String id, String userId) throws ResourceNotFoundException;

    List<ScheduledUrl> getByUserId(String userId);
}
