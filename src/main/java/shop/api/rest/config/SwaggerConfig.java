package shop.api.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket autoApiConfig() {
		return new Docket(DocumentationType.SWAGGER_2)
		        .select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiConfig());
	}
	
	private ApiInfo getApiConfig() {
		return new ApiInfoBuilder()
			.title("Kong Free Project")
			.version("v1.0")
			.contact(new Contact("Kong", "https://github.com/kong-driod", "rhdalgid134@gmail.com"))
			.build();
	}
	
}
