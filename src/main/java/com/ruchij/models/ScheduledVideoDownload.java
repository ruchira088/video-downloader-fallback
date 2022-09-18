package com.ruchij.models;

import com.ruchij.daos.user.models.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Setter
@Getter
@Entity(name = "scheduled_video_download")
public class ScheduledVideoDownload {
    @Id
    private String id;

    @CreationTimestamp
    @Column(nullable = false, name = "created_at")
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, name = "video_url")
    private String videoUrl;
}
