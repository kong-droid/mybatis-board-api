package site.kongdroid.api.exception;


@SuppressWarnings("serial")
public class BadRequestException extends DefaultNestedRuntimeExcepiton {
    public BadRequestException(String code) {
        super(code);
    }

    public BadRequestException(String code, String reason) {
        super(code, reason);
    }

    public BadRequestException(String code, String reason, Object data) {
        super(code, reason, data);
    }

    public BadRequestException(String code, String reason, Object data, Throwable cause) {
        super(code, reason, data, cause);
    }

    public BadRequestException(String code, String reason, Throwable cause) {
        super(code, reason, cause);
    }

}
