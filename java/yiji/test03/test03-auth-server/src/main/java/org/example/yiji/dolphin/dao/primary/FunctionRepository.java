package org.example.yiji.dolphin.dao.primary;

import org.example.yiji.dolphin.dao.BaseRepository;
import org.example.yiji.dolphin.model.primary.Function;

import java.util.List;

public interface FunctionRepository extends BaseRepository<Function, String> {

  List<Function> findByName(String name);
  List<Function> findByHead(Boolean head);

}
