package club.javafamily;

import club.javafamily.dao.HiveQueryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import club.javafamily.domain.HiveResultEntity;

import java.util.List;

/**
 * @author Jack Li
 * @date 2023/10/25 下午7:37
 * @description
 */
@SpringBootTest
public class QueryTests {

   @Autowired
   private HiveQueryRepository repository;

   @Test
   void test() {
      final List<HiveResultEntity> list = repository.findAllByNameLike("%");

      System.out.println(list);
   }

}
