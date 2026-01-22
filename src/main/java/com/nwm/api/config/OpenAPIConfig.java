package com.nwm.api.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenAPIConfig {
    @Bean
    public GroupedOpenApi siteApi() {
        return GroupedOpenApi.builder()
                .group("site-api")
                .pathsToMatch("/3rd-party/device-data")
                .build();
    }

//    @Bean
//    public OpenAPI customOpenAPI() {
//        Info info = new Info();
//        Components components = new Components();
//        info.title("NextWave APIs").version("v1.0.0").description("Contact Next Wave Energy Monitoring");
//        components.addSecuritySchemes("bearerAuth",
//                new SecurityScheme()
//                        .type(SecurityScheme.Type.HTTP)
//                        .scheme("bearer")
//                        .bearerFormat("JWT")
//                        .in(SecurityScheme.In.HEADER)
//                        .name("Authorization")
//        );
//        return new OpenAPI()
//                .info(info)
//                .components(components)
//                .addSecurityItem( new SecurityRequirement().addList("bearerAuth"));
//    }
}
