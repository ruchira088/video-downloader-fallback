package com.ruchij.services.schedules;

import com.ruchij.daos.schedules.ScheduledUrlRepository;
import com.ruchij.daos.schedules.models.ScheduledUrl;
import com.ruchij.exceptions.ResourceConflictException;
import com.ruchij.exceptions.ResourceNotFoundException;
import com.ruchij.services.generator.IdGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchedulingServiceImpl implements SchedulingService {
    private final ScheduledUrlRepository scheduledUrlRepository;
    private final IdGenerator idGenerator;

    public SchedulingServiceImpl(ScheduledUrlRepository scheduledUrlRepository, IdGenerator idGenerator) {
        this.scheduledUrlRepository = scheduledUrlRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public ScheduledUrl insert(String url, String userId) throws ResourceConflictException {
        if (findByUrl(url, userId).isPresent()) {
            throw new ResourceConflictException("url=%s is already scheduled for userId=%s".formatted(url, userId));
        }

        return scheduledUrlRepository.save(new ScheduledUrl(idGenerator.generate(), url, userId));
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

    @Override
    public List<ScheduledUrl> getByUserId(String userId) {
        return scheduledUrlRepository.findScheduledUrlsByUserId(userId);
    }
}
