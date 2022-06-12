package com.madirex.gameserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;

@EnableWebMvc
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    /**
     *  API Product
     * @return Docket
     */
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.madirex.gameserver.controller"))
                .build()
                .apiInfo(metaInfo());
    }

    /**
     * Info de API
     * @return API Info
     */
    private ApiInfo metaInfo() {
        return new ApiInfo(
            APIConfig.API_NAME, APIConfig.API_DESCRIPTION, APIConfig.API_VERSION, "Terms of Service",
            new Contact(APIConfig.AUTHOR_NAME, APIConfig.AUTHOR_URL, APIConfig.AUTHOR_EMAIL), APIConfig.API_LICENSE,
            APIConfig.API_LICENSE_URL, new ArrayList<>()
        );
    }
}
