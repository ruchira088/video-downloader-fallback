package com.ruchij.services.schedules;

import com.ruchij.daos.schedules.ScheduledUrlRepository;
import com.ruchij.daos.schedules.models.ScheduledUrl;
import com.ruchij.daos.schedules.models.Status;
import com.ruchij.exceptions.ResourceConflictException;
import com.ruchij.exceptions.ResourceNotFoundException;
import com.ruchij.services.generator.IdGenerator;
import com.ruchij.services.system.SystemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Semaphore;

@Service
public class SchedulingServiceImpl implements SchedulingService {
    private final ScheduledUrlRepository scheduledUrlRepository;
    private final SystemService systemService;
    private final IdGenerator idGenerator;
    private final Semaphore semaphore = new Semaphore(1);

    public SchedulingServiceImpl(ScheduledUrlRepository scheduledUrlRepository, SystemService systemService, IdGenerator idGenerator) {
        this.scheduledUrlRepository = scheduledUrlRepository;
        this.systemService = systemService;
        this.idGenerator = idGenerator;
    }

    @Override
    public ScheduledUrl insert(String url, String userId) throws ResourceConflictException {
        if (findByUrl(url, userId).isPresent()) {
            throw new ResourceConflictException("url=%s is already scheduled for userId=%s".formatted(url, userId));
        }

        String id = idGenerator.generate();
        Instant timestamp = systemService.timestamp();

        return scheduledUrlRepository.save(new ScheduledUrl(id, url, userId, timestamp, Status.PENDING));
    }

    @Override
    public Optional<ScheduledUrl> findByUrl(String url, String userId) {
        return scheduledUrlRepository.findScheduledUrlByUrlAndUserId(url, userId);
    }

    @Override
    public ScheduledUrl getById(String id, String userId) throws ResourceNotFoundException {
        return scheduledUrlRepository.findScheduledUrlByIdAndUserId(id, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Unable to find id=%s and userId=%s".formatted(id, userId)));
    }

    @PreAuthorize("hasPermission(#userId, 'USER', 'READ')")
    @Override
    public Page<ScheduledUrl> getByUserId(String userId, Pageable pageable) {
        return scheduledUrlRepository.findScheduledUrlsByUserId(userId, pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Page<ScheduledUrl> getAll(Pageable pageable) {
        return scheduledUrlRepository.findAll(pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<ScheduledUrl> pull(int size) {
        try {
            semaphore.acquire();
            Instant timestamp = systemService.timestamp();

            Page<ScheduledUrl> page = scheduledUrlRepository.findByStatus(Status.PENDING, Pageable.ofSize(size));

            return page.stream()
                .filter(scheduledUrl ->
                    scheduledUrlRepository.updateStatusById(scheduledUrl.getId(), Status.PENDING, timestamp, Status.LOCKED) == 1
                )
                .toList();
        } catch (InterruptedException interruptedException) {
            return List.of();
        } finally {
            semaphore.release();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public ScheduledUrl acknowledge(String scheduledUrlId) throws ResourceNotFoundException {
        return scheduledUrlRepository.findById(scheduledUrlId)
            .map(scheduledUrl -> {
                Instant timestamp = systemService.timestamp();
                scheduledUrl.setUpdatedAt(timestamp);
                scheduledUrl.setStatus(Status.ACKNOWLEDGED);

                return scheduledUrlRepository.save(scheduledUrl);
            })
            .orElseThrow(() ->
                new ResourceNotFoundException("Unable to find scheduled URL id=%s".formatted(scheduledUrlId))
            );

    }
}
