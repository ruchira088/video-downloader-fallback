package com.ruchij.daos.schedules;

import com.ruchij.daos.schedules.models.ScheduledUrl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScheduledUrlRepository extends PagingAndSortingRepository<ScheduledUrl, String> {
    Page<ScheduledUrl> findScheduledUrlsByUserId(String userId, Pageable pageable);

    Optional<ScheduledUrl> findScheduledUrlByUrlAndUserId(String url, String userId);

    Optional<ScheduledUrl> findScheduledUrlByIdAndUserId(String id, String userId);
}
