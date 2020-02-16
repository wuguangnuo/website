package cn.wgn.website.dto;

import lombok.extern.slf4j.Slf4j;

import static java.net.HttpURLConnection.*;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/15 20:12
 */
@Slf4j
public class ApiRes {
    private Integer code;
    private String msg;
    private Object data;

    public ApiRes(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ApiRes suc() {
        return suc("Success", null);
    }

    public static ApiRes suc(String msg) {
        return suc(msg, null);
    }

    public static ApiRes suc(Object obj) {
        return suc("Success", obj);
    }

    public static ApiRes suc(String msg, Object obj) {
        return res(HTTP_OK, msg, obj);
    }

    public static ApiRes fail() {
        return fail("Query Empty!");
    }

    public static ApiRes fail(String msg) {
        log.warn("ApiRes Fail: " + msg);
        return res(HTTP_NOT_IMPLEMENTED, msg, null);
    }

    public static ApiRes err(String msg) {
        log.error("ApiRes Error: " + msg);
        return res(HTTP_INTERNAL_ERROR, msg, null);
    }

    public static ApiRes unAuthorized() {
        return unAuthorized("unAuthorization");
    }

    public static ApiRes unAuthorized(String msg) {
        return res(HTTP_UNAUTHORIZED, msg, null);
    }

    private static ApiRes res(Integer code, String msg, Object data) {
        return new ApiRes(code, msg, data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
