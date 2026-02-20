package club.javafamily.mongodbtest.entity;

import club.javafamily.mongodbtest.enums.UserType;
import lombok.*;
import lombok.experimental.Tolerate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * @author Jack Li
 * @date 2022/3/18 3:25 下午
 * @description
 */
@Builder
@ToString
@Data
@Document("d_user")
public class UserEntity extends BaseDocument {

   private String name;

   private Integer age;

   private Date birthday;

   private Double score;

   private UserType type;

   @DBRef
   private UserFavoriteEntity favoriteEntity;

   @DBRef
   private List<UserFavoriteEntity> favorites;

   @Tolerate
   public UserEntity() {
   }
}
