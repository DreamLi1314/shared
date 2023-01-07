package org.example.yiji.dolphin.service.impl;

import org.example.yiji.dolphin.dao.secondary.SaltRepository;
import org.example.yiji.dolphin.model.secondary.Salt;
import org.example.yiji.dolphin.service.SaltService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wanglin
 * @date 2020/5/11 19:44
 */
@Service
public class SaltServiceImpl extends BaseServiceImpl<Salt,Long> implements SaltService {

  @Autowired
  private SaltRepository saltRepository;

}
