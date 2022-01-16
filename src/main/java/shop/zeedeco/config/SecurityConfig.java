//package shop.zeedeco.config;
//
//
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import lombok.extern.slf4j.Slf4j;
//import shop.zeedeco.security.AuthEntryPointJwt;
//import shop.zeedeco.security.SimpleCorsFilter;
//import shop.zeedeco.security.jwt.JwtAuthFilter;
//
//
//// 시큐리티 유저 서비스가 안만들어져서 잠시 줄석 처리 후 진행
//@Slf4j
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(
//		securedEnabled = true,
//		jsr250Enabled = true,
//		prePostEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	
////	@Autowired
////	UserDetailsServiceImpl UserDetailsServiceImpl;
//	
//	@Autowired
//	AuthEntryPointJwt unauthorizedHandler;
//	
//    @PostConstruct
//    public void init() {
//        log.info(">>> SecurityConfigConfig.init===================================>");
//    }
//
//
//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//		auth.userDetailsService(UserDetailsServiceImpl)
//			.passwordEncoder(customPasswordEncoder());
//	}
//	
//    @Bean
//    public UserDetailsServiceImpl userDetailsService() {
//        return new UserDetailsServiceImpl();
//    }
//	
//	@Bean
//	@Override
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();		
//	}
//	
//
//
//	@Bean
//    public PasswordEncoder customPasswordEncoder() {
//        return new PasswordEncoder() {
//            @Override
//            public String encode(CharSequence rawPassword) {
//                return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(4));
//            }
//
//            @Override
//            public boolean matches(CharSequence rawPassword, String encodedPassword) {
//                return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
//            }
//        };
//    }
//	
//    @Bean
//    public JwtAuthFilter tokenAuthenticationFilter() {
//        return new JwtAuthFilter();
//    }
//
//    @Bean
//    public SimpleCorsFilter simpleFilter() {
//        return new SimpleCorsFilter();
//    }
//
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//			.antMatchers("/auth/**").permitAll() 
//			.anyRequest().permitAll()  //.authenticated() 
//		.and().cors()
//		.and().csrf().disable()
//		.formLogin().disable()
//		.httpBasic().disable()
//		.addFilterBefore(simpleFilter(), UsernamePasswordAuthenticationFilter.class)
//        .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) 
//        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//		.and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
//	}
//}