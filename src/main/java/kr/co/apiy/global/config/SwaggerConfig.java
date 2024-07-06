package kr.co.apiy.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi groupMember(){
        String[] pathToMatch = {"/member/*"};
        return GroupedOpenApi.builder()
                .group("Member")
                .pathsToMatch(pathToMatch)
                .build();
    }

    @Bean
    public GroupedOpenApi groupSample(){
        String[] pathToMatch = {"/sample/*"};
        return GroupedOpenApi.builder()
                .group("Sample")
                .pathsToMatch(pathToMatch)
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .components(new Components()
                        .addSecuritySchemes(
                                "bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    public Info apiInfo() {
        return new Info()
                .title("ApiY")
                .version("1.0")
                .description("Swagger 화면");
    }


}
