package com.sky.alcohol.domain;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlcoholInfoRepository extends JpaRepository<AlcoholInfo, Long> {
    default List<AlcoholInfo> findAllDesc() {
        return findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
}