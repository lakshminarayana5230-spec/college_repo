package com.college.cms.config;

import io.swagger.v3.oas.models.tags.Tag;
import java.util.List;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Forces a fixed tag order in the generated OpenAPI spec so Swagger UI shows controllers in a
 * business-friendly order.
 */
@Configuration
public class SwaggerTagOrderConfig {

  @Bean
  public OpenApiCustomizer swaggerTagOrderCustomizer() {
    return openApi ->
        openApi.setTags(
            List.of(
                new Tag().name("Courses").description("CRUD APIs for courses"),
                new Tag().name("Students").description("CRUD APIs for students"),
                new Tag().name("Enrollments").description("CRUD APIs for enrollments"),
                new Tag().name("Payments").description("CRUD APIs for payments")));
  }
}
