package com.my.sendemail;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = {RunApplication.class})
@EnableSwagger2
//事务必加
@EnableAutoConfiguration
// 开启缓存
@EnableCaching
//MyBatis 支持(扫描dao接口)
@MapperScan("com.my.sendemail.dao")
public class RunApplication extends SpringBootServletInitializer{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.sources(this.getClass());
        return super.configure(builder);
    }
	public static void main(String[] args) {
		SpringApplication.run(RunApplication.class, args);
	}
}
