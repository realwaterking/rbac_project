package com.aqua.rbaccommon.common;

import org.apache.poi.ss.formula.functions.T;

/**
 * 统一返回工具类
 * @author water king
 * @time 2024/2/6
 */
public class ResultUtils {

    /**
     * 成功
     * @param data
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 失败
     * @param errorCode
     * @return
     */
    public static BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     * @param code
     * @param message
     * @return
     */
    public static BaseResponse<T> error(int code, String message) {
        return new BaseResponse<>(code, null, message);
    }

    public static BaseResponse<T> error(ErrorCode errorCode, String message) {
        return new BaseResponse<>(errorCode.getCode(), null, errorCode.getMessage());
    }
}