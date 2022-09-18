package com.ruchij.web.controllers;

import com.ruchij.daos.schedules.models.ScheduledUrl;
import com.ruchij.daos.user.models.User;
import com.ruchij.exceptions.ResourceConflictException;
import com.ruchij.exceptions.ResourceNotFoundException;
import com.ruchij.services.schedules.SchedulingService;
import com.ruchij.web.requests.InsertScheduledUrlRequest;
import com.ruchij.web.response.ResultsList;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/schedule")
@RestController
public class SchedulingController {
    private final SchedulingService schedulingService;

    public SchedulingController(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduledUrl insert(@RequestBody InsertScheduledUrlRequest insertScheduledUrlRequest)
        throws ResourceConflictException {
        return schedulingService.insert(insertScheduledUrlRequest.getUrl(), getUser().getId());
    }

    @GetMapping
    public ResultsList<ScheduledUrl> getAll() {
        return new ResultsList<>(schedulingService.getByUserId(getUser().getId()));
    }

    @GetMapping("/id/{scheduledUrlId}")
    public ScheduledUrl getById(@PathVariable String scheduledUrlId) throws ResourceNotFoundException {
        return schedulingService.getById(scheduledUrlId, getUser().getId());
    }

    @GetMapping("/search")
    public ResultsList<ScheduledUrl> search(@RequestParam String url) {
        return new ResultsList<>(schedulingService.findByUrl(url, getUser().getId()).stream().toList());
    }

    private User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
