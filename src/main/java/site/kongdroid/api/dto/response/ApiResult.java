package site.kongdroid.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "API 결과")
public class ApiResult {

    @Schema(description = "상태정보", example = "200")
    private int status;

    @Schema(description = "응답 코드", example = "Success")
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
