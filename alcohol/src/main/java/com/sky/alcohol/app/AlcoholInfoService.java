package com.sky.alcohol.app;

import com.sky.alcohol.domain.Alcohol;
import com.sky.alcohol.domain.AlcoholInfo;
import com.sky.alcohol.domain.AlcoholInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlcoholInfoService {

    private final AlcoholInfoRepository alcoholInfoRepository;

    @Transactional(readOnly = true)
    public List<AlcoholInfo> findAllDesc() {
        return alcoholInfoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
}
