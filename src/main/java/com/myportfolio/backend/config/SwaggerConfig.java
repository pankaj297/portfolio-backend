package com.myportfolio.backend.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Portfolio Management API", version = "1.0", description = "REST APIs for Portfolio Management System"), security = {
        @SecurityRequirement(name = "bearerAuth")
})
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")
public class SwaggerConfig {

    @Bean
    public OpenAPI myCustomConfig() {

        return new OpenAPI()
                .info(
                        new io.swagger.v3.oas.models.info.Info()
                                .title("Portfolio App API Testing")
                                .description("By Pankaj"))
                .servers(
                        List.of(
                                new Server()
                                        .url("http://localhost:8080")
                                        .description("Local"),

                                new Server()
                                        .url("http://localhost:8081")
                                        .description("Live")))
                    .tags(List.of(new Tag().name("Auth APIs"),
                            new Tag().name("Profile APIs"),
                             new Tag().name("Education APIs")       
                        ));
    }
}




// @OpenAPIDefinition(info = @Info(title = "Portfolio Management API", version = "1.0", description = "REST APIs for Portfolio Management System"), security = {
//         @SecurityRequirement(name = "bearerAuth")
// })
// @SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT")