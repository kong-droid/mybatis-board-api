package site.kongdroid.api.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

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
    public GroupedOpenApi boardOpenApi() {
        String[] paths = {"/**"};
 
        return GroupedOpenApi.builder()
            .group("kong-droid BOARD-API(Mybatis)")
            .pathsToMatch(paths)
            .build();
    }
}
