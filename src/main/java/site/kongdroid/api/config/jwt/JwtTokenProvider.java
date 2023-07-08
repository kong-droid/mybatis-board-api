package site.kongdroid.api.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.cglib.core.internal.LoadingCache;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import site.kongdroid.api.config.security.ExtendedUserDetailsService;
import site.kongdroid.api.constants.UserRole;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;

    private final ExtendedUserDetailsService userService;


    public String create(String userAgent, Integer memberSeq, UserRole role) {
        val claims = Jwts.claims().setSubject(memberSeq.toString());
        claims.put("memberSeq", memberSeq);
        claims.put("role", role.name());
        claims.put("agent", userAgent); // todo shorter agent
        val issuedAt = new Date();
        val expireAt = new Date(issuedAt.getTime() + jwtConfig.getValidTime().toMillis());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(expireAt)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getEncodedSecret())
                .compact();
    }

    public Authentication getAuthentication(String token) {
        val memberSeq = getUserNo(token);
        return new UsernamePasswordAuthenticationToken(memberSeq, null, null);
    }

    private UserDetails getDetail(Integer memberSeq) {
        return userService.loadUserByNo(memberSeq);
    }

    public String resolve(HttpServletRequest req) {
        val token = req.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) return token.substring(7);
        return token;
    }

    public boolean isValid(String userAgent, String token) {
        try {
            val claims = Jwts.parser().setSigningKey(jwtConfig.getEncodedSecret()).parseClaimsJws(token);
            val body = claims.getBody();
            val memberSeq =  body.getSubject();
//            val lastLogin = historyLoginService.getFirstByMethod(userId, method);
//            if (lastLogin != null) {
//                if (!agent.equals(lastLogin.getAgent())) {
//                    throw new AuthException(ErrorMessage.AUTH_LOGIN_OTHER);
//                }
//            }
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserNo(String token) {
        return Jwts.parser().setSigningKey(jwtConfig.getEncodedSecret())
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
