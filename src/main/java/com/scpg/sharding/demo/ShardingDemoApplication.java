package com.scpg.sharding.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author renzh05
 */
@MapperScan(basePackages={"com.scpg.sharding.demo.mapper"})
@SpringBootApplication(scanBasePackages = {"com.scpg"})
public class ShardingDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingDemoApplication.class, args);
    }

}
