package club.javafamily.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import club.javafamily.domain.HiveResultEntity;

import java.util.List;

/**
 * @author Jack Li
 * @date 2023/10/25 下午7:33
 * @description
 */
public interface HiveQueryRepository
   extends JpaRepository<HiveResultEntity, String>
{
   @Query(value = "SELECT * FROM t_test", nativeQuery = true)
   List<HiveResultEntity> findAllFromHiveTable();

   List<HiveResultEntity> findAllByNameLike(String prefix);
}
