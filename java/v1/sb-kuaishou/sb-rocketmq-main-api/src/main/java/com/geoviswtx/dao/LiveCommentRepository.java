package com.geoviswtx.dao;

import com.geoviswtx.entity.LiveCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveCommentRepository extends JpaRepository<LiveCommentEntity, Long> {
}
