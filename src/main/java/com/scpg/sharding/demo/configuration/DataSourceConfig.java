package com.scpg.sharding.demo.configuration;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
        // 配置 t_user 表规则
        TableRuleConfiguration dayReportRuleConfiguration = new TableRuleConfiguration("mng_sale_amount_day", "base.mng_sale_amount_day_$->{2015..2030}0$->{1..9},base.mng_sale_amount_day_$->{2015..2030}1$->{0..2},aliyun.mng_sale_amount_day_$->{2015..2030}0$->{1..9},aliyun.mng_sale_amount_day_$->{2015..2030}1$->{0..2}");
       // 配置分表规则  第一个精确，第二个范围
        dayReportRuleConfiguration.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("SALE_YMD", new DayReportPreciseShardingAlgorithm(), new DayReportPreciseShardingAlgorithm()));
        // Sharding全局配置
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        //多表的话,就配置多个就好了
        shardingRuleConfiguration.getTableRuleConfigs().add(dayReportRuleConfiguration);
        // 创建数据源
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(targetDataSources, shardingRuleConfiguration, new Properties());
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
