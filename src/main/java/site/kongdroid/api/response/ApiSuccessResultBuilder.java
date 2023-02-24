package site.kongdroid.api.response;

public class ApiSuccessResultBuilder {
    
    private int status = 200;
    private String code = "SUCCESS";
    private Object data;

    public ApiSuccessResultBuilder() {

    }

    public ApiSuccessResultBuilder(Object data) {
        this.data = data;
    }

    public ApiSuccessResultBuilder status(int status) {
        this.status = status;
        return this;
    }

    public ApiSuccessResultBuilder code(String code) {
        this.code = code;
        return this;
    }

    public ApiSuccessResultBuilder data(Object data) {
        this.data = data;
        return this;
    }

    public ApiResult build() {
        ApiResult apiResult = new ApiResult();
        apiResult.setStatus(this.status);
        apiResult.setCode(this.code);
        apiResult.setData(this.data);
        return apiResult;
    }
}
