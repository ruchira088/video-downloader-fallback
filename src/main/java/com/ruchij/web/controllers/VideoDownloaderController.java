package com.ruchij.web.controllers;

import com.ruchij.daos.schedules.models.ScheduledUrl;
import com.ruchij.exceptions.ResourceNotFoundException;
import com.ruchij.services.schedules.SchedulingService;
import com.ruchij.web.response.ResultsList;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/video-downloader")
@RestController
public class VideoDownloaderController {
    private final SchedulingService schedulingService;

    public VideoDownloaderController(SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    @GetMapping("/scheduled-urls")
    public ResultsList<ScheduledUrl> pull(@RequestParam(defaultValue = "10") int size) {
        return new ResultsList<>(schedulingService.pull(size), Optional.empty());
    }

    @DeleteMapping("/scheduled-urls/id/{scheduledUrlId}")
    public ScheduledUrl acknowledge(@PathVariable String scheduledUrlId) throws ResourceNotFoundException {
        return schedulingService.acknowledge(scheduledUrlId);
    }
}
