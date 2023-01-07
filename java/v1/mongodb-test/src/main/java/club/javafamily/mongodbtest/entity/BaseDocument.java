package club.javafamily.mongodbtest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jack Li
 * @date 2022/3/23 10:05 下午
 * @description
 */
@Data
@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class BaseDocument implements Serializable {

   @Id
   private String id;

   @CreatedDate
   private Date createDate;

   @LastModifiedDate
   private Date updateDate;
}
