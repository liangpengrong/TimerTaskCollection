package com.my.timertask.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


/**
<blockquote>
<h1>Swagger2配置</h1>
* @author [lpr]
* @date [2018年11月6日 下午3:31:17]
*/
@Configuration
public class Swagger2Config {
    //swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.my.sendemail.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("我的发送邮件项目")
                .description("提供发送邮件服务")// API描述
                .version("1.0.0")// 联系人
                .termsOfServiceUrl("http://xxx.xxx.xxx:x/MySendEmail/")//服务地址
                .contact("你我他")// 联系人
                .licenseUrl("http://xxx.xxx.xxx:x/MySendEmail/")//许可URL
                .build();
    }
}
