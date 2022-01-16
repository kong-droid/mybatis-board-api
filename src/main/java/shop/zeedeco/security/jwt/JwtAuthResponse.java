package shop.zeedeco.security.jwt;
import org.apache.tomcat.util.modeler.BaseModelMBean;

public class JwtAuthResponse extends BaseModelMBean {

    private final String token;

    public JwtAuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}