package shop.zeedeco.security.jwt;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import shop.zeedeco.config.ApplicationProperties;
import shop.zeedeco.security.UserDetailsImpl;

@Slf4j
@Service
public class JwtUtil {

	private static final String AUTHORITIES_KEY = "authorities";

	private byte[] secretKey;

	private long tokenValidityInMilliseconds;

	private long tokenValidityInMillisecondsForRememberMe;

	private ApplicationProperties applicationProperties;
	
    public JwtUtil(ApplicationProperties applicationProperties) {
    		this.applicationProperties = applicationProperties;
    		this.secretKey = applicationProperties.getSecurity().getJwt().getSecret().getBytes(Charset.forName("UTF-8"));
    		
    		this.tokenValidityInMilliseconds = 1000
    				* applicationProperties.getSecurity().getJwt().getTokenValidityInSeconds();
    		this.tokenValidityInMillisecondsForRememberMe = 1000
    				* applicationProperties.getSecurity().getJwt().getTokenValidityInSecondsForRememberMe();
    
    }	

	public String createToken(Authentication authentication) {
		return createToken(authentication, false);
	}

	public String createToken(Authentication authentication, Boolean rememberMe) {
		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		long now = (new Date()).getTime();
		Date validity;
		if (rememberMe) {
			validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
		} else {
			validity = new Date(now + this.tokenValidityInMilliseconds);
		}

		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authentication.getPrincipal();

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", userDetailsImpl.getId());
		map.put("memberSeq", userDetailsImpl.getMemberSeq());

		return
		 Jwts.builder()
		 		.setId(userDetailsImpl.getId())
		 		.setSubject(String.valueOf(userDetailsImpl.getMemberSeq()))
				.setIssuedAt(new Date()) // 활성화
				.setExpiration(validity) // 만료시각
				.claim(AUTHORITIES_KEY, authorities)
				.claim("Info", map)
				.signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(secretKey))
				.compact();
		

	}

	
	public String createAdminToken() {
		return Jwts.builder().setSubject("admin").claim(AUTHORITIES_KEY, "ROLE_ADMIN")
				.signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encodeToString(secretKey))
				.setExpiration(new Date((new Date()).getTime() + 1000 * 3600 * 24 * 365)).compact();
	}

	public String getUserIdFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secretKey)).parseClaimsJws(token).getBody();

		return claims.getSubject();
	}
	
	public Authentication  getAuthentication(HttpServletRequest request) {
		
		String tokenString = getJwtFromRequest(request);
		
		Claims claims = Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secretKey)).parseClaimsJws(tokenString).getBody();

		String[] roles = claims.get(AUTHORITIES_KEY).toString().split(",");
		List<SimpleGrantedAuthority> authorities = Arrays.stream(roles).map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());

		return new UsernamePasswordAuthenticationToken(claims.getSubject(), "", authorities);
	}
	

	public HashMap<String, Object> getTokenDataFromJwtToken(HttpServletRequest request) {

		String tokenString = getJwtFromRequest(request);
		
		if(tokenString.equals(null)) return null;
		
		Jws<Claims> parsedToken = Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secretKey)).parseClaimsJws(tokenString);

		Claims tokenClaims = parsedToken.getBody();
		HashMap<String, Object> tokenClaimsData = new HashMap<>(tokenClaims);


		ObjectMapper objectMapper = new ObjectMapper();
		String tmp;
		try {
			tmp = objectMapper.writeValueAsString(tokenClaimsData.get("Info"));
			return objectMapper.readValue(tmp, HashMap.class);

		} catch (JsonProcessingException e) {

			e.printStackTrace();
			return null;
		}

	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

	public boolean validateToken(HttpServletRequest request) {
		try {
			
			String tokenString = getJwtFromRequest(request);
			
			Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secretKey)).parseClaimsJws(tokenString);

			return true;
		} catch (SignatureException e) {
			log.info("Invalid JWT signature.");
			log.trace("Invalid JWT signature trace: {}", e);
		} catch (MalformedJwtException e) {
			log.info("Invalid JWT token.");
			log.trace("Invalid JWT token trace: {}", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT token.");
			log.trace("Expired JWT token trace: {}", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT token.");
			log.trace("Unsupported JWT token trace: {}", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT token compact of handler are invalid.");
			log.trace("JWT token compact of handler are invalid trace: {}", e);
		}
		return false;
	}

}
