package org.example.yiji.dolphin.org.examplemon;

import lombok.Getter;

/**
 * @author wanglin
 * @date 2020/5/19 16:34
 */
@Getter
public enum PageStaticField {

  //总记录数
  PAGE_TOTAL("total"),
  //查询集合信息
  PAGE_ROWS("rows"),
  //验证
  PAGE_VALID("valid");

  private String value;

  PageStaticField(String value) {
    this.value = value;
  }

}
