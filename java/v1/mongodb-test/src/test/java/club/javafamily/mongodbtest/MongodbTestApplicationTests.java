package club.javafamily.mongodbtest;

import club.javafamily.mongodbtest.dao.UserFavoriteRepository;
import club.javafamily.mongodbtest.dao.UserRepository;
import club.javafamily.mongodbtest.entity.UserEntity;
import club.javafamily.mongodbtest.entity.UserFavoriteEntity;
import club.javafamily.mongodbtest.enums.UserType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@Slf4j
@SpringBootTest
class MongodbTestApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFavoriteRepository userFavoriteRepository;

    private static final String ID = "62345aa2f62cb83e788eee27";

    @Test
    void contextLoads() {
        Assertions.assertNotNull(userRepository);
    }

    @Test
    void insertTest() {
        List<UserFavoriteEntity> entities = Arrays.asList(
           UserFavoriteEntity.builder()
              .name("篮球").score(0.8)
              .build(),
           UserFavoriteEntity.builder()
              .name("羽毛球").score(0.2)
              .build());

        entities = userFavoriteRepository.saveAll(entities);

        UserEntity user = UserEntity.builder()
//           .id(ID) // 可以为空
           .name("Test0")
           .age(24)
           .birthday(new Date())
           .type(UserType.NORMAL)
           .score(88.88)
           .favoriteEntity(entities.get(0))
           .favorites(entities)
           .build();

        // 可以为空
//        user.setId(ID);

        user = userRepository.insert(user);

        log.info(user.toString());

        Assertions.assertNotNull(user.getId());
    }

    @Test
    void queryTest() {
        final UserEntity user
           = userRepository.findById(ID)
           .orElse(null)
           ;

        Assertions.assertNotNull(user);

        log.info(user.toString());
    }

    @Test
    void updateTest() {
        UserEntity user
           = userRepository.findById(ID)
           .orElse(null)
           ;

        Assertions.assertNotNull(user);

        user.setName("Test");

        UserEntity user2 = userRepository.save(user);

        Assertions.assertEquals(user, user2);
    }

    @Test
    void deleteTest() {
        String id = ID;
        Assertions.assertTrue(userRepository.existsById(id));

        userRepository.deleteById(id);
    }

}
