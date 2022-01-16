package shop.zeedeco.security.jwt;

import java.io.IOException;
import java.util.HashMap;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    //@Autowired
    //private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
        	
        	if(request.getHeader("Authorization")!= null) {

        	HashMap<String, Object> tokenData  = jwtUtil.getTokenDataFromJwtToken(request);

            //UserDetails userDetails = userDetailsService.loadUserByUsername(tokenData.get("userNo").toString());
            //UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            //authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    		
        		Authentication authentication2 = jwtUtil.getAuthentication(request);
        		SecurityContextHolder.getContext().setAuthentication(authentication2);
        		log.info(authentication2 + " Authentication ¿Œ¡ı");
        	}
     
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

}
