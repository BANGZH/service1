package com.cloud.service01.exception;

import org.springframework.http.HttpStatus;

/**
 * @ClassName RightModuleException
 * @Description: RightModuleException 类（或接口）是
 * @Author: zhonghanbang
 * @Date: 2020/11/2515:08
 */
public class BusinessRuntimeException extends RuntimeException {
    private HttpStatus httpStatus = HttpStatus.OK;
    private String errorCode = "-1";
    private String errorMsg = "系统内部异常";

    public BusinessRuntimeException() {
        super();
    }

    public BusinessRuntimeException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public BusinessRuntimeException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusinessRuntimeException(HttpStatus httpStatus, String errorCode, String errorMsg) {
        super(errorMsg);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
