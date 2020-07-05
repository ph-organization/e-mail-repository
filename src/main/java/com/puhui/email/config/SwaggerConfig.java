package com.puhui.email.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @author: 邹玉玺
 * @date: 2020/7/3-14:19
 */
@Configuration
@EnableSwagger2   //开启swagger2
public class SwaggerConfig {


    //注入一个docket对象,配置bean实例
    public Docket getDocket01(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("杨利华");
    }
    @Bean
    public Docket getDocket(Environment environment){
        //设置需要显示的swagger环境
        Profiles profiles=Profiles.of("dev");
        //获取项目环境    判断是否处在自己设定的环境
        boolean flag = environment.acceptsProfiles(profiles);
        System.out.println(flag);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .enable(true)//是否开启swagger
                .select()
                //配置要扫描接口的方式
                /**
                 * basePackage()  指定包扫描  （通常用这个）
                 * any     扫描全部
                 * none      全部不扫描
                 * withMethodAnnotation     扫描方法上的注解（参数是一个注解的反射对象）
                 * withClassAnnotation        扫描类上的注解（）
                 */
                .apis(RequestHandlerSelectors.basePackage("com.puhui.email.controller" ))
                .paths(PathSelectors.ant("/mail/*"))//只扫描mail有关的接口
                .build()
                .groupName("邹玉玺");


    }

    //配置api信息
    private ApiInfo getApiInfo(){
        //作者信息
        Contact contact = new Contact("邹玉玺","https://user.qzone.qq.com/1003941268/infocenter","1003941268@qq.com");
        return new ApiInfo("邮件发送的swagger API文档",
                "Api 描述",       //描述
                "1.0",     //版本
                "urn:https://user.qzone.qq.com/1003941268/infocenter",     //地址
                contact,     //作者信息
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );
    }
}
