package com.scpg.sharding.demo.controller;

import com.scpg.sharding.demo.domain.ApiRespJsonObj;
import com.scpg.sharding.demo.service.ISaleDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: study
 * @description:
 * @author: cheerway
 * @create: 2021-06-12 10:55
 */
@RequestMapping("/sale/day")
@RestController
public class SaleDayController {

    @Autowired
    private ISaleDayService saleDayService;

    @PostMapping("/selectLeftJoinAndOrderBy")
    public ApiRespJsonObj selectLeftJoinAndOrderBy(){
        saleDayService.selectLeftJoinAndOrderBy();
        return ApiRespJsonObj.success();
    }
}
