package com.ruchij.daos.schedules;

import com.ruchij.daos.schedules.models.ScheduledUrl;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduledUrlRepository extends CrudRepository<ScheduledUrl, String> {
    List<ScheduledUrl> findScheduledUrlsByUserId(String userId);
    Optional<ScheduledUrl> findScheduledUrlByUrlAndUserId(String url, String userId);
    Optional<ScheduledUrl> findScheduledUrlByIdAndUserId(String id, String userId);
}
