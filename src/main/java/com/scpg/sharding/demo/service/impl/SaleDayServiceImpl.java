package com.scpg.sharding.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.scpg.sharding.demo.domain.entity.SaleDay;
import com.scpg.sharding.demo.mapper.SaleDayMapper;
import com.scpg.sharding.demo.service.ISaleDayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: study
 * @description:
 * @author: cheerway
 * @create: 2021-06-12 11:02
 */
@Service
@Slf4j
public class SaleDayServiceImpl implements ISaleDayService {

    @Autowired
    private SaleDayMapper saleDayMapper;

    @Override
    public void selectLeftJoinAndOrderBy() {
        List list = saleDayMapper.selectLeftJoinAndOrderBy();
        log.info("*****************************");
        log.info("size:{}", list.size());
    }
}
