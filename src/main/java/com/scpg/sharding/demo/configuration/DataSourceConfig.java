package com.scpg.sharding.demo.configuration;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @program: profile-demo
 * @description:
 * @author: cheerway
 * @create: 2021-06-12 11:54
 */
@Configuration
public class DataSourceConfig {
    @Value("${mybatis-plus.mapper-locations}")
    private String MAPPER_LOCATION;
    @Bean(name = "base")
    @ConfigurationProperties(prefix = "spring.datasource.druid.base")
    public DataSource base() {
        DruidDataSource datasource = DruidDataSourceBuilder.create().build();
        List<Filter> filters = datasource.getProxyFilters();
        if (filters != null && filters.size() > 0) {
            for (Filter filter : filters) {
                if (filter instanceof WallFilter) {
                    WallConfig wallConfig = new WallConfig();
                    wallConfig.setMultiStatementAllow(true);
                    ((WallFilter) filter).setConfig(wallConfig);
                }
            }
        }
        return datasource;
    }

    @Bean(name = "aliyun")
    @ConfigurationProperties(prefix = "spring.datasource.druid.aliyun")
    public DataSource units() {
        DruidDataSource datasource = DruidDataSourceBuilder.create().build();
        List<Filter> filters = datasource.getProxyFilters();
        if (filters != null && filters.size() > 0) {
            for (Filter filter : filters) {
                if (filter instanceof WallFilter) {
                    WallConfig wallConfig = new WallConfig();
                    wallConfig.setMultiStatementAllow(true);
                    ((WallFilter) filter).setConfig(wallConfig);
                }
            }
        }
        return datasource;
    }

    @Bean
    @Primary
    public DataSource multipleDataSource(@Qualifier("base") DataSource base, @Qualifier("aliyun") DataSource aliyun) throws SQLException {
        Map<String, DataSource> targetDataSources = new HashMap<>(2);
        targetDataSources.put("base", base);
        targetDataSources.put("aliyun", aliyun);
        // 配置 day_report 表规则
        ShardingTableRuleConfiguration dayReportRuleConfiguration = new ShardingTableRuleConfiguration("mng_sale_amount_day", "base.mng_sale_amount_day_$->{2011..2030}0$->{1..9},base.mng_sale_amount_day_$->{2011..2030}1$->{0..2},aliyun.mng_sale_amount_day_$->{2011..2030}0$->{1..9},aliyun.mng_sale_amount_day_$->{2011..2030}1$->{0..2}");

        // 配置分表规则  第一个精确，第二个范围
        dayReportRuleConfiguration.setTableShardingStrategy(new StandardShardingStrategyConfiguration("SALE_YMD", "dayReportTableShardingAlgorithm"));

        // Sharding全局配置
        //多表的话,就配置多个就好了
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        shardingRuleConfiguration.getTables().add(dayReportRuleConfiguration);
        //分库规则配置

        shardingRuleConfiguration.getShardingAlgorithms().put("dayReportTableShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("dayReportRule", new Properties()));
        // 创建数据源
        DataSource dataSource = ShardingSphereDataSourceFactory.createDataSource(targetDataSources, Collections.singleton(shardingRuleConfiguration), new Properties());
        return dataSource;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(multipleDataSource(base(), units()));
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));

        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        sqlSessionFactory.setConfiguration(configuration);

        return sqlSessionFactory.getObject();
    }
}
