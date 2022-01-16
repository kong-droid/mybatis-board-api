package shop.zeedeco.config;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ApplicationProperties {
    
    private final Security security = new Security();

    public Security getSecurity() {
        return security;
    }

    public static class Security {

        private final Jwt jwt = new Jwt();

        public Jwt getJwt() {
            return jwt;
        }

        public static class Jwt {
        	
        	// 이 부분 수정해야함
            private String secret = "test";

            private long tokenValidityInSeconds = 1800;

            private long tokenValidityInSecondsForRememberMe = 2592000;

            public String getSecret() {
                return secret;
            }

            public void setSecret(String secret) {
                this.secret = secret;
            }

            public long getTokenValidityInSeconds() {
                return tokenValidityInSeconds;
            }

            public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
                this.tokenValidityInSeconds = tokenValidityInSeconds;
            }

            public long getTokenValidityInSecondsForRememberMe() {
                return tokenValidityInSecondsForRememberMe;
            }

            public void setTokenValidityInSecondsForRememberMe(long tokenValidityInSecondsForRememberMe) {
                this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
            }
        }
    }
}
