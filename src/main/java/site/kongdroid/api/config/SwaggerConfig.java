package site.kongdroid.api.config;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;


import java.util.List;

@OpenAPIDefinition(
    info = @Info(
        title = "BOARD-API",
        description = "kong-droid board-api(myBatis)",
        version = "v2.0"
    )
)
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(getComponents())
                .security(List.of(new SecurityRequirement().addList("Authentication")));
    }

    private Components getComponents(){
        return new Components().addSecuritySchemes("Authentication", new SecurityScheme().type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT"));
    }
}
