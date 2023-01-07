package org.example.yiji.dolphin.service;

import org.example.yiji.dolphin.org.examplemon.PageResponse;
import org.example.yiji.dolphin.model.VO.FunctionTreeVo;
import org.example.yiji.dolphin.model.primary.Function;

import java.util.List;

/**
 * @author wanglin
 * @date 2020/5/11 19:42
 */
public interface FunctionService extends BaseService<Function,String> {

  List<Function> findByName(String name);
  PageResponse<Function> findBySearch(String str, int pageNum, int pageSize);
  List<FunctionTreeVo> tree();

}
