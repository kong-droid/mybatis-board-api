package shop.zeedeco;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(basePackages= "shop.zeedeco")
@SpringBootApplication
public class ZeedecoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZeedecoApiApplication.class, args);
	}

}
