package com.xianghong.life.advise;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.client.HttpClientErrorException;

/**
 * @author lujinxiang
 * @create 21-07-26
 * @description ErrorCode
 * <p>
 * 1.89951000~899519999为业务异常码, 日志打印时不需要打印堆栈日志
 * 2.后4为1xxx标识业务异常;2xxx标识系统异常;3xxx标识统一异常处理中异常
 * </p>
 */
public final class ErrorCode {
    /**
     * 入参错误
     */
    public static final int WRONG_PARAM = 89951001;

    /**
     * 验证码错误
     */
    public static final int WRONG_CODE = 89951002;

    /**
     * 登录失败码，用户名/密码错误
     */
    public static final int LOGIN_FAIL = 89951003;

    /**
     * 用户名不存在
     */
    public static final int USER_NOT_EXIST = 89951004;

    /**
     * 用户名已存在
     */
    public static final int USER_HAS_EXIST = 89951005;

    /**
     * 用户名已删除
     */
    public static final int USER_HAS_DELETED = 8995106;

    /**
     * 数据不存在
     */
    public static final int DATA_NOT_EXIST = 89951007;

    /**
     * 用户未登录
     */
    public static final int USER_NOT_LOGIN = 89951008;

    /**
     * 用户不在白名单
     */
    public static final int USER_NOT_IN_WHITE_LIST = 89951009;

    /**
     * 数据已存在
     */
    public static final int DATA_HAS_EXIST = 89951010;

    /**
     * 数据冲突
     */
    public static final int DATA_CONFLICT = 89951011;

    /**
     * 数据状态错误
     */
    public static final int DATA_STATUS_ERROR = 89951012;

    /**
     * 非法操作
     */
    public static final int ILLEGAL_OPERATION = 89951020;

    /**
     * 系统异常
     */
    public static final int SYSTEM_ERROR = 89952000;

    /**
     * 类型匹配异常
     * @see TypeMismatchException
     */
    public static final int TYPE_MISMATCH = 89953001;

    /**
     * 不支持的媒体类型
     * @see HttpMediaTypeNotSupportedException
     */
    public static final int UNSUPPORTED_MEDIA_TYPE = 89953002;

    /**
     * 入参缺失
     * @see MissingServletRequestParameterException
     */
    public static final int MISS_PARAMETER = 89953003;

    /**
     * 请求方法不允许
     * @see HttpRequestMethodNotSupportedException
     */
    public static final int METHOD_NOT_ALLOWED = 89953004;

    /**
     * 消息体不可读
     * @see HttpMessageNotReadableException
     */
    public static final int MESSAGE_NOT_READABLE = 89953005;

    /**
     * 参数非法
     * @see MethodArgumentNotValidException
     */
    public static final int ARGUMENT_NOT_VALID = 89953006;

    /**
     * http客户端错误
     * @see HttpClientErrorException
     */
    public static final int HTTP_CLIENT_ERROR = 89953007;

    /**
     * 未获取到分布式锁
     */
    public static final int NOT_GET_REDIS_LOCK = 89953008;
}
