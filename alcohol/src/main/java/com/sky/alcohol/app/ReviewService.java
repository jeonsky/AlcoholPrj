package com.sky.alcohol.app;

import com.sky.alcohol.domain.Review;
import com.sky.alcohol.domain.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public List<Review> findAllDesc() {
        return reviewRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional
    public Long write(String title, String content, String author) {
        return reviewRepository.save(new Review(title, content, author)).getId();
    }

    @Transactional(readOnly = true)
    public Review getOne(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    public void delete(Long id) {
        var post = reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        reviewRepository.delete(post);
    }

    @Transactional
    public void update(Long id, String title, String content) {
        var post = reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        post.change(title, content);
    }
}
