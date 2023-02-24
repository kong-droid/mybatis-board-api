package site.kongdroid.api.exception;

@SuppressWarnings("serial")
public class ResourceNotFoundException extends DefaultNestedRuntimeExcepiton {
    public ResourceNotFoundException(String code) {
        super(code);
    }

    public ResourceNotFoundException(String code, String reason) {
        super(code, reason);
    }

    public ResourceNotFoundException(String code, String reason, Object data) {
        super(code, reason, data);
    }

    public ResourceNotFoundException(String code, String reason, Object data, Throwable cause) {
        super(code, reason, data, cause);
    }

    public ResourceNotFoundException(String code, String reason, Throwable cause) {
        super(code, reason, cause);
    }
}
