package com.ruchij.services.schedules;

import com.ruchij.daos.schedules.models.ScheduledUrl;
import com.ruchij.exceptions.ResourceConflictException;
import com.ruchij.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SchedulingService {
    ScheduledUrl insert(String url, String userId) throws ResourceConflictException;

    Optional<ScheduledUrl> findByUrl(String url, String userId);

    ScheduledUrl getById(String id, String userId) throws ResourceNotFoundException;

    Page<ScheduledUrl> getByUserId(String userId, Pageable pageable);

    Page<ScheduledUrl> getAll(Pageable pageable);

    List<ScheduledUrl> pull(int size);

    ScheduledUrl acknowledge(String scheduledUrlId) throws ResourceNotFoundException;
}
