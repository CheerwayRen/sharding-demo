package com.scpg.sharding.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scpg.sharding.demo.domain.dto.MallDayDTO;
import com.scpg.sharding.demo.domain.entity.SaleDay;

import java.util.List;

/**
 * @program: study
 * @description:
 * @author: cheerway
 * @create: 2021-06-12 10:50
 */
public interface SaleDayMapper extends BaseMapper<SaleDay> {
    List<MallDayDTO> selectLeftJoinAndOrderBy();
}
