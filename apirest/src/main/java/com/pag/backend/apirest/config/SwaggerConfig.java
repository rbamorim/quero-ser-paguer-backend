package com.pag.backend.apirest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.pag.backend.apirest.controller"))
					.paths(PathSelectors.any())
					.build()
				.apiInfo(apiInfo())
				.pathMapping("/");
	}
	
    private ApiInfo apiInfo() {
    	 
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title ("Desafio PAG")
                .description ("API Rest em JAVA - quero ser PAG backend")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("Rafael Barroca Amorim", "https://www.linkedin.com/in/rafael-barroca-amorim-9980bb164/", "rbamorim@gmail.com"))
                .version("1.0.0")
                .build();
 
        return apiInfo;
    }
}
