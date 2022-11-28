package shop.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "API ê²°ê³¼ê°?")
public class ApiResult {

    @Schema(description = "?ƒ?ƒœê°?", example = "200")
    private int status;

    @Schema(description = "?ƒ?ƒœê°?:ì½”ë“œ", example = "Success")
    private String code;

    @Schema(description = "response", example = "{ generate_key : 200 }")
    private Object data;

    public ApiResult() {

    }

    public static ApiResultBuilder builder() {
        return new ApiResultBuilder();
    }

    public static ApiSuccessResultBuilder successBuilder() {
        return new ApiSuccessResultBuilder();
    }

    public static ApiResult successBuilder(Object data) {
        return new ApiSuccessResultBuilder(data).build();
    }
}
