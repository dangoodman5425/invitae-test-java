package com.invitae.lab.platform.variant.api.configuration;

import com.google.common.base.Strings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Application configuration for Swagger documentation
 *
 * @author dgoodman
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    @Profile({"local", "dev"})
    public Docket apiLocalAndDev(final ApiInfo apiInfo) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // Ignore the Error from showing up in Swagger
                .paths(path -> !Strings.nullToEmpty(path).startsWith("/error"))
                .apis(RequestHandlerSelectors.basePackage("com.invitae.lab.platform.variant.api.controller"))
                .build()
                .pathMapping("/")
                .apiInfo(apiInfo);
    }

    @Bean
    @Profile("prod")
    public Docket apiProd() {
        // Turn off swagger docs in production
        return new Docket(DocumentationType.SWAGGER_2).
                enable(false)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build();
    }

    @Bean
    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Invitae Variant API")
                .contact(new Contact("Dan G", null, "danielgoodman5425@gmail.com"))
                .description("API for exposing and persisting variants")
                .build();
    }
}
