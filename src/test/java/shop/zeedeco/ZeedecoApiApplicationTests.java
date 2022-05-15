package shop.zeedeco;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
class ZeedecoApiApplicationTests {

	@Autowired
	private Environment environment;
	
	@Test
	void contextLoads() {
		System.err.println("INFO >>>>>>>>>>>>>>>>>>>>>>" + environment.getProperty("spring.profiles.active"));
	}

}
