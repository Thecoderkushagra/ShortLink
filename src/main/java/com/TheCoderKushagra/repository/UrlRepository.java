package com.TheCoderKushagra.repository;

import com.TheCoderKushagra.entity.UrlEntity;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM UrlEntity u WHERE u.id = :id")
    int deleteUrlById(@Param("id") Long id);

    @Query("SELECT p FROM UrlEntity p " +
            "WHERE (:cursor IS NULL OR p.id > :cursor) " + "ORDER BY p.id ASC")
    List<UrlEntity> fetchNextPage(@Param("cursor") Long cursor, Pageable pageable);

    @Query("SELECT p FROM UrlEntity p " + "WHERE p.user.id = :userId " +
            "AND (:cursor IS NULL OR p.id > :cursor) " + "ORDER BY p.id ASC")
    List<UrlEntity> fetchNextPageByUser(
            @Param("userId") Long userId, @Param("cursor") Long cursor, Pageable pageable
    );

    @Modifying
    @Query(" UPDATE UrlEntity u " + " SET u.clickCount = u.clickCount + :count " + " WHERE u.id = :id ")
    void incrementClickCount(@Param("id") long id, @Param("count") long count);

}
