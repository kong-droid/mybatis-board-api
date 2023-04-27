package site.kongdroid.api.exception;

@SuppressWarnings("serial")
public class BadRequestException extends RuntimeException {

    private final String code;
    private final Object data;

    @Override
    public String getMessage() {
    	return this.code;
    }

    public String getCode() {
        return this.code;
    }

    public Object getData() {
        return this.data;
    }
    
    public BadRequestException(String code) {
    	super(code);
        this.code = code;
        this.data = null;
    }

    public BadRequestException(String code, Object data) {
    	super(code);
        this.code = code;
        this.data = data;
    }
}
