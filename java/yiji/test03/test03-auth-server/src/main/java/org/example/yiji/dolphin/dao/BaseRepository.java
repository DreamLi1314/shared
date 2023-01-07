package org.example.yiji.dolphin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author wanglin
 * @date 2020/5/12 16:50
 */
@NoRepositoryBean
public interface BaseRepository<E, ID> extends JpaRepository<E, ID>,
    JpaSpecificationExecutor<E> {

}
