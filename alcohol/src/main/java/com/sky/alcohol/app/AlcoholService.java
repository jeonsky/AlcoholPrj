package com.sky.alcohol.app;

import com.sky.alcohol.domain.Alcohol;
import com.sky.alcohol.domain.AlcoholRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // ★ spring-tx
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AlcoholService {

    private final AlcoholRepository alcoholRepository;

    @Transactional
    public Long write(String title, String content, String author) {
        Alcohol post = new Alcohol(title, content, author);
        return alcoholRepository.save(post).getId();
    }

    @Transactional(readOnly = true)
    public java.util.List<Alcohol> findAllDesc() {
        return alcoholRepository.findAll(org.springframework.data.domain.Sort
                .by(org.springframework.data.domain.Sort.Direction.DESC, "id"));
    }

    @Transactional(readOnly = true)
    public Alcohol getOne(Long id) {
        return alcoholRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글 없음"));
    }
}
