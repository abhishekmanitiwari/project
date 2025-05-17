package com.dailycodework.dreamshops.repository;

import com.dailycodework.dreamshops.dto.ImageDto;
import com.dailycodework.dreamshops.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query(value = "SELECT new com.dailycodework.dreamshops.dto.ImageDto(id, fileName, downloadUrl) FROM Image i where i.id=:id ")
    List<ImageDto> findByProductId(Long id);
}
