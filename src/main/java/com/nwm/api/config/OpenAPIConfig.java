package com.nwm.api.config;

import com.nwm.api.utils.Constants;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
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
@OpenAPIDefinition(
        servers = {
                @Server(url = Constants.SWAGGER_API_URL)
        }
)
public class OpenAPIConfig {

    String[] sitePaths = {
            "/3rd-party/device-info",
            "/3rd-party/device-data",
            "/alert/external/**",
            "/device/external/**",
            "/site/external/get-site"
    };

    @Bean
    public GroupedOpenApi siteApi() {
        return GroupedOpenApi.builder()
                .group("site-api")
                .pathsToMatch(sitePaths)
                .build();
    }

//    @Bean
//    public GroupedOpenApi externalApi() {
//        return GroupedOpenApi.builder()
//                .group("external-api")
//                .pathsToMatch("/alert/external/**", "/device/external/**")
//                .build();
//    }
//    @Bean
//    public GroupedOpenApi siteExternalApi() {
//        return GroupedOpenApi.builder()
//                .group("2-site-external-api")
//                .pathsToMatch("/site/external/get-site")
//                .build();
//    }

    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info();
        Components components = new Components();
        info.title("NextWave APIs").version("v1.0.0").description("Contact Next Wave Energy Monitoring");
        components.addSecuritySchemes("bearerAuth",
                new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization")
        );
        return new OpenAPI()
                .info(info);
//                .components(components)
//                .addSecurityItem( new SecurityRequirement().addList("bearerAuth"));
    }
}
