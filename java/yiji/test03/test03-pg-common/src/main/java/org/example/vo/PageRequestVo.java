package org.example.vo;

import lombok.*;

import java.io.Serializable;

/**
 * @author Jack Li
 * @date 2022/5/17 下午4:52 @description
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageRequestVo implements Serializable {
   private int page;
   private int size;
}
