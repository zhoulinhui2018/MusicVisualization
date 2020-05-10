package com.example.demo.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Documentation;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(Environment environment) {

        //配置在dev和prod环境中才启动swagger
        Profiles profiles=Profiles.of("dev","prod");
        boolean flag = environment.acceptsProfiles(profiles);


        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //如果为false，则swagger不能启动
                .enable(flag)
                .groupName("周林辉")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
//                扫描指定方法
//                .apis(RequestHandlerSelectors.withMethodAnnotation(GetMapping.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //配置扫描的方式与指定的包
//                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                //.paths(PathSelectors.ant("/music"))  指定Url才被扫描
                .build();
    }


    private ApiInfo apiInfo() {
        //作者信息
        Contact contact = new Contact("周林辉", "", "1047043261@qq.com");
        return new ApiInfo("周林辉的SwaggerApi文档",
                "Api Documentation",
                "1.0",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );
    }
}
