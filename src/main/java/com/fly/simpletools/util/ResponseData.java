package com.fly.simpletools.util;

/**
 * ResponseData 相应数据结构
 *
 * @author Mr_Fei
 * @version V1.0
 * @date 2020/6/24 13:06
 **/
public class ResponseData<T> {

    /**
     * 成功状态码
     */
    public static final int SUCCESS_CODE = 666;
    /**
     * 失败状态码
     */
    public static final int ERROR_CODE = 888;
    /**
     * 系统异常状态码
     */
    public static final int SYS_ERROR_CODE = 1000;
    /**
     * 系统异常消息
     */
    public static final String SYS_ERROR_MSG = "系统错误！";

    /**
     * 参数设置异常
     */
    public static final String PARAMETER_ERROR_MSG = "参数异常！";
    /**
     * 操作失败异常消息
     */
    public static final String OPERATION_FAILED = "操作失败！";

    /**
     * 成功信息
     */
    public static final String SUCCESS_MSG = "操作成功！";
    /**
     * 状态码
     */
    private int code;
    /**
     * 状态消息
     */
    private String responseMsg;
    /**
     * 相应信息
     */
    private T tData;

    public ResponseData(int code, String responseMsg, T tData) {
        this.code = code;
        this.responseMsg = responseMsg;
        this.tData = tData;
    }

    public ResponseData(int code, T tData) {
        this.code = code;
        this.tData = tData;
    }

    public ResponseData(int code, String responseMsg) {
        this.code = code;
        this.responseMsg = responseMsg;
    }

    public ResponseData(String errorMsg) {
        this.code = ERROR_CODE;
        this.responseMsg = errorMsg;
    }

    public ResponseData() {

    }

    public static ResponseData success() {
        return new ResponseData(SUCCESS_CODE, SUCCESS_MSG);
    }

    public static ResponseData success(String message) {
        return new ResponseData(SUCCESS_CODE, message);
    }

    public static ResponseData failed() {
        return new ResponseData(ERROR_CODE, OPERATION_FAILED);
    }

    public static ResponseData failed(String message) {
        return new ResponseData(ERROR_CODE, message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public T gettData() {
        return tData;
    }

    public void settData(T tData) {
        this.tData = tData;
    }
}
