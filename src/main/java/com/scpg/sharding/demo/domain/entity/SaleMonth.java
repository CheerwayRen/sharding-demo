package com.scpg.sharding.demo.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @program: study
 * @description:
 * @author: cheerway
 * @create: 2021-06-12 10:53
 */
@Data
@TableName(value="MNG_SALE_AMOUNT_MONTH")
public class SaleMonth {
    /**
     * 主键
     */
    @TableId(value="ID",type = IdType.AUTO)
    private Long id;

    /**
     * 项目ID
     */
    @TableField(value="MALL_ID")
    private Long mallId;
}
