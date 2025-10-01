// src/main/java/com/sky/alcohol/domain/Review.java
package com.sky.alcohol.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "review") // 테이블명 분리
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=200)
    private String title;

    @Column(columnDefinition = "TEXT", nullable=false)
    private String content;

    @Column(nullable=false, length=40)
    private String author;

    // ★ 옵션: 후기 전용 필드(나중에 확장)
    // private Integer rating;           // 별점 1~5
    // private String drinkCategory;     // 위스키/와인/맥주 등

    @CreatedDate
    @Column(updatable=false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Review(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
