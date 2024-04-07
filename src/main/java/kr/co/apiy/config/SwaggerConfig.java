package kr.co.apiy.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("springdoc-openapi")
                        .version("1.8")
                        .description("Swagger 화면")
                );
    }


    @Bean
    public GroupedOpenApi api() {
        String[] paths = {"/sample/**"};
        String[] packagesToScan = {"kr.co.frameworky"};
        return GroupedOpenApi.builder().group("springdoc-openapi")
                .pathsToMatch(paths)
                .pathsToMatch(packagesToScan)
                .build();
    }

}
