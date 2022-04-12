package com.xianghong.life.advise;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jboss.netty.handler.timeout.ReadTimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: CommonExceptionSupport
 * @Description:
 * @Author: lujinxiang
 * @Date: 21-07-26
 */
@Slf4j
@ControllerAdvice(annotations = {RestController.class})
public class CommonExceptionSupport {

    @ExceptionHandler({Throwable.class})
    @ResponseBody
    public CommonResponse handleException(HttpServletRequest request, Throwable e) {
        logAndMetric(request, "CMS异常未知", "cmsError.unknown", e, true);
        return errorResponse(request.getServletPath(), HttpStatus.INTERNAL_SERVER_ERROR, com.xianghong.life.advise.ErrorCode.SYSTEM_ERROR, "InternalServerError");
    }

    @ExceptionHandler({com.xianghong.life.advise.CommonException.class})
    @ResponseBody
    public CommonResponse handleException(HttpServletRequest request, com.xianghong.life.advise.CommonException e) {
        logAndMetric(request, "CMS异常已知", "cmsError", e, false);
        return errorResponse(request.getServletPath(), e.getHttpStatus(), e.getCode(), e.getMessage());
    }

    /**
     * argument required
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public CommonResponse handleException(HttpServletRequest request, MissingServletRequestParameterException e) {
        logAndMetric(request, "CMS异常已知", "cmsError", e, false);
        return errorResponse(request.getServletPath(), HttpStatus.BAD_REQUEST, com.xianghong.life.advise.ErrorCode.ARGUMENT_NOT_VALID, e.getMessage());
    }

    /**
     * argument {@code @Valid} {@code @Validated}
     */
    @ExceptionHandler({BindException.class})
    @ResponseBody
    public CommonResponse handleException(HttpServletRequest request, BindException e) {
        logAndMetric(request, "CMS异常已知", "cmsError", e, false);
        return errorResponse(request.getServletPath(), HttpStatus.BAD_REQUEST, com.xianghong.life.advise.ErrorCode.ARGUMENT_NOT_VALID, "BindValid", e.getBindingResult());
    }

    /**
     * argument {@code @Valid} {@code @Validated}
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public CommonResponse handleException(HttpServletRequest request, MethodArgumentNotValidException e) {
        logAndMetric(request, "CMS异常已知", "cmsError", e, false);
        return errorResponse(request.getServletPath(), HttpStatus.BAD_REQUEST, com.xianghong.life.advise.ErrorCode.ARGUMENT_NOT_VALID, "ArgumentNotValid", e.getBindingResult());
    }


    /**
     * errorResponse include errors
     *
     * @param servletPath
     * @param httpStatus
     * @param errorCode
     * @param message
     * @param errors
     * @return
     */
    private CommonResponse errorResponse(String servletPath, HttpStatus httpStatus, int errorCode, String message, Errors errors) {
        CommonResponse.ErrorResponse errorResponse = CommonResponse.ErrorResponse.error(servletPath, httpStatus.value(), errorCode, message, errors);
        if (CommonCode.SUCCESS_CODE == errorResponse.getCode()) {
            errorResponse.setCode(com.xianghong.life.advise.ErrorCode.SYSTEM_ERROR);
        }
        return errorResponse;
    }

    /**
     * errorResponse no errors
     *
     * @param servletPath
     * @param httpStatus
     * @param errorCode
     * @param message
     * @return
     */
    private CommonResponse errorResponse(String servletPath, HttpStatus httpStatus, int errorCode, String message) {
        return errorResponse(servletPath, httpStatus, errorCode, message, null);
    }


    /**
     * logAndMetric include log Exception StackTrace
     *
     * @param request
     * @param label
     * @param metricName
     * @param e
     * @param logStackTrace
     */
    private void logAndMetric(HttpServletRequest request, String label, String metricName, Throwable e, boolean logStackTrace) {
        // log
        String servletPath = request.getServletPath();
        String msg = StringUtils.left(StringUtils.defaultString(e.getMessage()), 50).replaceAll(" ", "+");
        String method = request.getMethod();
        String exceptionName = e.getClass().getSimpleName();
        if (logStackTrace) {
            if (!ReadTimeoutException.class.getSimpleName().equals(exceptionName) && containReadTimeoutException(e)) {
                exceptionName = ReadTimeoutException.class.getSimpleName();
            }
            log.error("ExceptionAdvise error {},{},{},{}", label, exceptionName, method, servletPath, e);
        } else {
            log.warn("ExceptionAdvise warn {},{},{},{},{}", label, exceptionName, method, servletPath, e.getMessage());
        }
    }

    public boolean containReadTimeoutException(Throwable e) {
        return ExceptionUtils.getThrowableList(e).stream().anyMatch(t -> t instanceof ReadTimeoutException);
    }
}
