package club.javafamily.mongodbtest.dao;

import club.javafamily.mongodbtest.entity.UserFavoriteEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Jack Li
 * @date 2022/3/19 3:52 下午
 * @description
 */
public interface UserFavoriteRepository extends MongoRepository<UserFavoriteEntity, String> {
}
