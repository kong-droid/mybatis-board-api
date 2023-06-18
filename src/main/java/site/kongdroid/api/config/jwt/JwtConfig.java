package site.kongdroid.api.config.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Base64;


@Data
@Component
@ConfigurationProperties(prefix = "api.jwt")
public class JwtConfig{

    private String secret;
    private Duration validTime;

    public String getEncodedSecret(){
        return Base64.getEncoder().encodeToString(getSecret().getBytes());
    }
}
