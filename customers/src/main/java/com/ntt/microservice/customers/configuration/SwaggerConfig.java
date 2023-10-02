package com.ntt.microservice.customers.configuration;

import static com.ntt.microservice.customers.configuration.Constants.AUTHOR_EMAIL;
import static com.ntt.microservice.customers.configuration.Constants.AUTHOR_FULL_NAME;
import static com.ntt.microservice.customers.configuration.Constants.AUTHOR_GITHUB;
import static com.ntt.microservice.customers.configuration.Constants.DESCRIPTION_APPLICATION;
import static com.ntt.microservice.customers.configuration.Constants.LICENSE;
import static com.ntt.microservice.customers.configuration.Constants.LICENSE_URL;
import static com.ntt.microservice.customers.configuration.Constants.REPOSITORY_URL;
import static com.ntt.microservice.customers.configuration.Constants.TITLE_APPLICATION;
import static com.ntt.microservice.customers.configuration.Constants.VERSION_APPLICATION;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration class for Swagger documentation.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  /**
   * Configures and initializes Swagger for API documentation.
   *
   * @return Docket instance for Swagger configuration.
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.ntt.microservice.customers.api.controller"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(getApiInfo());
  }

  /**
   * Constructs API information for Swagger documentation.
   *
   * @return ApiInfo instance containing API metadata.
   */
  private ApiInfo getApiInfo() {
    return new ApiInfo(
        TITLE_APPLICATION, DESCRIPTION_APPLICATION, VERSION_APPLICATION, REPOSITORY_URL,
        new Contact(AUTHOR_FULL_NAME, AUTHOR_GITHUB, AUTHOR_EMAIL),
        LICENSE, LICENSE_URL, Collections.emptyList());
  }
}
