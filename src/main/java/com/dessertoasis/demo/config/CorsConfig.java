package com.dessertoasis.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // project 中所有 api 都支持跨域
		        .allowedOrigins("http://localhost:8081/", "http://localhost:5173/") // 設置放行哪些原始域
		        .allowCredentials(true) // 是否發送 Cookie
		        .allowedMethods("*") // 放行哪些請求方法 GET, POST, PUT, DELETE,...
//		        .maxAge(3600) // 跨域允許時間
		        ;
	}
	
}
