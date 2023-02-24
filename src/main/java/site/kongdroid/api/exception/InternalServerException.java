package site.kongdroid.api.exception;

@SuppressWarnings("serial")
public class InternalServerException extends DefaultNestedRuntimeExcepiton {
    public InternalServerException(String code) {
        super(code);
    }

    public InternalServerException(String code, String reason) {
        super(code, reason);
    }

    public InternalServerException(String code, String reason, Object data) {
        super(code, reason, data);
    }

    public InternalServerException(String code, String reason, Object data, Throwable cause) {
        super(code, reason, data, cause);
    }

    public InternalServerException(String code, String reason, Throwable cause) {
        super(code, reason, cause);
    }
}
