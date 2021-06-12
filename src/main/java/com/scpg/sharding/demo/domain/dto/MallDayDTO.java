package com.scpg.sharding.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: profile-demo
 * @description:
 * @author: cheerway
 * @create: 2021-06-12 11:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MallDayDTO {
    private Long mallId;

    private String saleYmd;
}
