package com.shoppingMall.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${uploadPath}")  // application.properties 설정한 uploadPath
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                // images로 시작하는 경우 /static/images/에 설정한 폴더를 기준으로 파일을 읽어오도록 설정
                .addResourceLocations("classpath:/static/img/");  // 로컬 컴퓨터에서 root 경로를 설정
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }
}