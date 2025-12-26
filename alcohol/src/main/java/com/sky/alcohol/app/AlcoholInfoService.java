package com.sky.alcohol.app;

import com.sky.alcohol.domain.AlcoholInfo;
import com.sky.alcohol.domain.AlcoholInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlcoholInfoService {

    private final AlcoholInfoRepository alcoholInfoRepository;

    @Transactional(readOnly = true)
    public List<AlcoholInfo> findAllDesc() {
        return alcoholInfoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Transactional(readOnly = true)
    public Object getOne(Long id) {
        return alcoholInfoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void delete(Long id) {
        var post = alcoholInfoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        alcoholInfoRepository.delete(post);
    }

    @Transactional
    public void update(Long id, String title, String content) {
        var post = alcoholInfoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        post.change(title);
    }

    public void save(AlcoholInfo info) {
        alcoholInfoRepository.save(info);
    }
}
