package shop.zeedeco.exception;

import shop.zeedeco.exception.DefaultNestedRuntimeExcepiton;

@SuppressWarnings("serial")
public class AuthorizationException extends DefaultNestedRuntimeExcepiton {
    public AuthorizationException(String code) {
        super(code);
    }

    public AuthorizationException(String code, String reason) {
        super(code, reason);
    }

    public AuthorizationException(String code, String reason, Object data) {
        super(code, reason, data);
    }

    public AuthorizationException(String code, String reason, Object data, Throwable cause) {
        super(code, reason, data, cause);
    }

    public AuthorizationException(String code, String reason, Throwable cause) {
        super(code, reason, cause);
    }
}
