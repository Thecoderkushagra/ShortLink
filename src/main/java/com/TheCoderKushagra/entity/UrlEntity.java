package com.TheCoderKushagra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "url",
        indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_user_id_id", columnList = "user_id, id")
        })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_url")
    @SequenceGenerator(name = "seq_gen_url", sequenceName = "url_db_sequence", initialValue = 10000, allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(length = 3000)
    private String url;
    private LocalDateTime expiresAt;
    private Long clickCount = 0L;

    @CreatedDate
    private LocalDateTime createdAt;
}
