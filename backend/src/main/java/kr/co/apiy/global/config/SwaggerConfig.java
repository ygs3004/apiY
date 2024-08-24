package kr.co.apiy.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.log4j.Log4j2;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Components components = new Components();
        components.addSecuritySchemes(
                "bearer-key",
                new SecurityScheme().type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .info(apiInfo())
                .components(components);
    }

    public Info apiInfo() {
        return new Info()
                .title("ApiY")
                .version("1.0")
                .description("Swagger 화면");
    }

    // @Bean
    // public GroupedOpenApi groupSample(){
    //     String[] pathToMatch = {"/sample/*"};
    //     return GroupedOpenApi.builder()
    //             .group("Sample")
    //             .pathsToMatch(pathToMatch)
    //             .addOpenApiCustomizer(authOperationCustomizer()) // 태그 한번에 추가하기 가능
    //             .build();
    // }

    @Bean
    public GroupedOpenApi groupMember(){
        String[] pathToMatch = {"/member/*"};
        return GroupedOpenApi.builder()
                .group("회원 API")
                .pathsToMatch(pathToMatch)
                .build();
    }

    @Bean
    public GroupedOpenApi groupToday(){
        String[] pathToMatch = {"/news/**", "/movie/**"};
        return GroupedOpenApi.builder()
                .group("일간정보 API")
                .pathsToMatch(pathToMatch)
                .build();
    }

    // login 필요시
    // public OpenApiCustomizer authOperationCustomizer() {
    //     return openApi -> {
    //         Paths paths = openApi.getPaths();
    //         if (paths != null) {
    //             paths.forEach((path, pathItem) ->
    //                 pathItem.readOperations().forEach(operation -> {
    //                             SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearer-key");
    //                             operation.addSecurityItem(securityRequirement);
    //                 })
    //             );
    //         }
    //     };
    // }

}
