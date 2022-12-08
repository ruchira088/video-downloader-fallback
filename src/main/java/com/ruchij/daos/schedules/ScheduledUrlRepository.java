package com.ruchij.daos.schedules;

import com.ruchij.daos.schedules.models.ScheduledUrl;
import com.ruchij.daos.schedules.models.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface ScheduledUrlRepository extends PagingAndSortingRepository<ScheduledUrl, String>,
    ListCrudRepository<ScheduledUrl, String> {
    Page<ScheduledUrl> findScheduledUrlsByUserId(String userId, Pageable pageable);

    Page<ScheduledUrl> findByStatus(Status status, Pageable pageable);

    Optional<ScheduledUrl> findScheduledUrlByUrlAndUserId(String url, String userId);

    Optional<ScheduledUrl> findScheduledUrlByIdAndUserId(String id, String userId);

    @Modifying
    @Query("UPDATE scheduled_url SET status = :toStatus, updatedAt = :updatedAt WHERE id = :id AND status = :fromStatus")
    int updateStatusById(String id, Status fromStatus, Instant updatedAt, Status toStatus);
}
