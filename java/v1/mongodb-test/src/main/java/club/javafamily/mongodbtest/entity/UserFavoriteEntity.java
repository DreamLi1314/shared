package club.javafamily.mongodbtest.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Jack Li
 * @date 2022/3/18 3:34 下午
 * @description
 */
@Builder
@Data
@Document("d_user_favorite")
public class UserFavoriteEntity {

   @Id
   private String id;

   private String name;

   private Double score;

   @Tolerate
   public UserFavoriteEntity() {
   }
}
