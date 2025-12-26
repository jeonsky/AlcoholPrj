package com.sky.alcohol.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "alcohol_info")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class AlcoholInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;      // 이름

    @Column(nullable = false, length = 50)
    private String type;      // 주종
    private Double abv;       // 도수 %
    private Double volume;   // 용량(ml)
    private String origin;    // 원산지
    private String brand;     // 제조사/브랜드
    private String category;  // IPA, lager 등 세부 분류

    @Column(columnDefinition = "TEXT")
    private String description; // 상세 설명
    private Integer price;      // 가격(원)
    private String imageUrl;    // 사진 경로

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void change(String title) {
    }
}
