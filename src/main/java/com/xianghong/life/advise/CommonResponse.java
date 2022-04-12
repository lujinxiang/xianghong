package com.xianghong.life.advise;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName: CommonResponse
 * @Description:
 * @Author: lujinxiang
 * @Date: 21-07-26
 */
@Data
@ApiModel(description = "通用响应体")
@SuppressWarnings("WeakerAccess")
public class CommonResponse<T> {
    private static Logger LOGGER = LoggerFactory.getLogger(CommonResponse.class);
    private static String OK_STR = "success";
    private static String FAIL_STR = "fail";
    @ApiModelProperty(value = "时间戳")
    private long timestamp;
    /**
     * @see HttpStatus
     */
    @ApiModelProperty(value = "响应状态码")
    private int status;
    @ApiModelProperty(value = "响应状态吗(内部定义)")
    private int code = com.xianghong.life.advise.CommonCode.SUCCESS_CODE;
    @ApiModelProperty(value = "错误信息")
    private String message;
    @ApiModelProperty(value = "请求路径")
    private String path;
    @ApiModelProperty(value = "返回实体信息")
    private T entity;

    public static CommonResponse ok() {
        return ok(OK_STR);
    }

    public static <T> CommonResponse<T> ok(T entity) {
        CommonResponse<T> commonResponse = new CommonResponse<T>();
        commonResponse.entity = entity;
        commonResponse.timestamp = System.currentTimeMillis();
        commonResponse.status = HttpStatus.OK.value();
        return commonResponse;
    }

    public static CommonResponse<Boolean> isTrue(Boolean entity) {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.entity = entity;
        commonResponse.timestamp = System.currentTimeMillis();
        // code
        if (Objects.nonNull(entity) && entity) {
            commonResponse.status = HttpStatus.OK.value();
        } else {
            commonResponse.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
            commonResponse.code = com.xianghong.life.advise.ErrorCode.SYSTEM_ERROR;
        }
        return commonResponse;
    }

    @ApiModel(description = "通用错误返回对象")
    public static class ErrorResponse extends CommonResponse {
        @Setter
        @Getter
        private List<KeyError> errors;

        public static ErrorResponse error(String path, int status, int code, String message) {
            return error(path, status, code, message, null);
        }

        public static ErrorResponse error(String path, int status, int code, String message, Errors errors) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setTimestamp(System.currentTimeMillis());
            errorResponse.setPath(path);
            errorResponse.setStatus(status);
            errorResponse.setCode(code);
            errorResponse.setMessage(message);
            errorResponse.setErrors(coverToBeanError(errors));
            return errorResponse;
        }

        private static List<KeyError> coverToBeanError(Errors errors) {
            if (Objects.isNull(errors)) {
                return null;
            }
            return errors.getAllErrors().stream().map(error -> new KeyError(error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName(),
                    error.getDefaultMessage())).collect(Collectors.toList());
        }


        @Data
        public static class KeyError {
            private String key;
            private String code;

            public KeyError(String key, String code) {
                this.key = key;
                //this.code = code;
                this.code = code;
            }
        }
    }
}
