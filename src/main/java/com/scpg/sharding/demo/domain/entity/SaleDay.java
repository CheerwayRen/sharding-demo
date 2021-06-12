package com.scpg.sharding.demo.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName MngSaleAmountDay
 * @Description day表
 * @Author cheerweay
 * @Date 2020/6/12
 */
@Data
@TableName(value="MNG_SALE_AMOUNT_DAY")
public class SaleDay {
    /**
     * 主键
     */
    @TableId(value = "ID",type = IdType.AUTO)
    private Long id;

    /**
     * 月销售额ID
     */
    @TableField(value="MONTH_ID")
    private Long monthId;

    /**
     * 年月日
     */
    @TableField(value="SALE_YMD")
    private String saleYmd;
}
