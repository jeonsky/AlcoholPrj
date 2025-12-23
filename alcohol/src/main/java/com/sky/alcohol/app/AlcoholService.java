package com.sky.alcohol.app;

import com.sky.alcohol.domain.Alcohol;
import com.sky.alcohol.domain.AlcoholRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlcoholService {

    private final AlcoholRepository alcoholRepository;

    @Transactional(readOnly = true)
    public List<Alcohol> findAllDesc() {
        return alcoholRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional
    public Long write(String title, String content, String author) {
        return alcoholRepository.save(new Alcohol(title, content, author)).getId();
    }

    @Transactional(readOnly = true)
    public Alcohol getOne(Long id) {
        return alcoholRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void delete(Long id) {
        var post = alcoholRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        alcoholRepository.delete(post);
    }

    @Transactional
    public void update(Long id, String title, String content) {
        var post = alcoholRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        post.change(title, content);
    }
}