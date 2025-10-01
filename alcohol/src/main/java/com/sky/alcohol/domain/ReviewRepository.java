package com.sky.alcohol.domain;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    default List<Review> findAllDesc() {
        return findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
}
