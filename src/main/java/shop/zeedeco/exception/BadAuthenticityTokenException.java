package shop.zeedeco.exception;

@SuppressWarnings("serial")
public class BadAuthenticityTokenException extends DefaultNestedRuntimeExcepiton {
    public BadAuthenticityTokenException(String code) {
        super(code);
    }

    public BadAuthenticityTokenException(String code, String reason) {
        super(code, reason);
    }

    public BadAuthenticityTokenException(String code, String reason, Object data) {
        super(code, reason, data);
    }

    public BadAuthenticityTokenException(String code, String reason, Object data, Throwable cause) {
        super(code, reason, data, cause);
    }

    public BadAuthenticityTokenException(String code, String reason, Throwable cause) {
        super(code, reason, cause);
    }
}
