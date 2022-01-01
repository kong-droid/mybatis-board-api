package shop.zeedeco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan(basePackages={"shop.zeedeco"})
@SpringBootApplication
public class ZeedecoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZeedecoApiApplication.class, args);
	}

}
