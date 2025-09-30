package com.sky.alcohol.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "alcohol")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class Alcohol {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")   // 내용 길면 TEXT가 편함
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Alcohol(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Column(nullable = false, length = 40)
    private String author;

    public Alcohol(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void changeAuthor(String author) { this.author = author; }
    public void changeTitle(String title) { this.title = title; }
    public void changeContent(String content) { this.content = content; }
}
