package com.college.cms.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI configuration.
 *
 * <p>After starting the application, open Swagger UI at:
 * <ul>
 *   <li>http://localhost:8080/swagger-ui</li>
 * </ul>
 * and the OpenAPI JSON at:
 * <ul>
 *   <li>http://localhost:8080/v3/api-docs</li>
 * </ul>
 */
@Configuration
@OpenAPIDefinition(
    info =
        @Info(
            title = "College POC API",
            description =
                "REST API for a simple college management POC (courses, students, enrollments, payments).",
            version = "v1",
            contact = @Contact(name = "College POC Team"),
            license = @License(name = "Proprietary")),
    servers = {@Server(url = "http://localhost:8082", description = "Local")})
public class OpenApiConfig {}
