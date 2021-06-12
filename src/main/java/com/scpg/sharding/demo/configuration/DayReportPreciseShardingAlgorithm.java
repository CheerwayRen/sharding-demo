package com.scpg.sharding.demo.configuration;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @program: profile-demo
 * @description:
 * @author: cheerway
 * @create: 2021-06-12 11:49
 */


/**
 * @program: mod_backend
 * @description: 分表规则类
 * @author: cheerway
 * @create: 2021-06-06 11:23
 */
@Slf4j
public class DayReportPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String>, RangeShardingAlgorithm<String> {
    private static String month_format = "yyyyMM";


        //精准匹配  in 和 =
        @Override
        public String doSharding(Collection<String> tableNames, PreciseShardingValue<String> shardingValue) {
            Date reportDay = DateUtil.parse(shardingValue.getValue(), "yyyyMMdd");
            String reportMonth = DateUtil.format(reportDay, month_format);
            return "mng_sale_amount_day_" + reportMonth;
        }

        //范围匹配  between
        @Override
        public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<String> rangeShardingValue) {
            Range<String> reportDayRange = rangeShardingValue.getValueRange();
            //最小值
            String lowerDate = reportDayRange.lowerEndpoint();
            if (StringUtils.isEmpty(lowerDate)) {
                //数据从2011年开始
                lowerDate = "201101";
            } else {
                lowerDate = lowerDate.substring(0, 6);
            }
            //最大值
            String upperDate = reportDayRange.upperEndpoint();
            if (StringUtils.isEmpty(upperDate)) {
                //数据到当前时间结束
                upperDate = DateUtil.format(new Date(), month_format);
            }else {
                upperDate = upperDate.substring(0, 6);
            }
            return getMonthTableEnums("mng_sale_amount_day_", DateUtil.parse(lowerDate, month_format), DateUtil.parse(upperDate, month_format));
        }


        private List<String> getMonthTableEnums(String table, DateTime start, DateTime end) {
            List<String> list = new ArrayList<>();
            while (!start.isAfter(end)) {
                list.add(table + DateUtil.format(start, month_format));
                start.offset(DateField.MONTH, 1);
            }
            return list;
        }
}
