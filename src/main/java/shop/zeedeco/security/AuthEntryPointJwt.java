package shop.zeedeco.security;

//import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

// AuthenticationEntryPoint�뒗 commence()-誘몄씤利� �궗�슜�옄 �젒洹� �떆 �삁�쇅 泥섎━ �젣怨�
@Slf4j
@Component
public class AuthEntryPointJwt  implements AuthenticationEntryPoint { 
 
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        log.error("沅뚰븳�뾾�쓬 :::: Responding with unauthorized error. Message - {}", e.getMessage());
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    }

}
