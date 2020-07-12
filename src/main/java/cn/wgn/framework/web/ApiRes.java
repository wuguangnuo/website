package cn.wgn.framework.web;

import cn.wgn.framework.constant.HttpStatus;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * ApiRes 返回
 *
 * @param <T>
 * @author WuGuangNuo
 * @date Created in 2020/2/15 20:12
 */
@Slf4j
@Data
public class ApiRes<T> {
    private Integer status;
    private String message;
    private T data;

    public static <T> ApiRes<T> suc() {
        return suc("Success", null);
    }

//    public static <T> ApiRes<T> suc(String message) {
//        return suc(message, null);
//    }

    public static <T> ApiRes<T> suc(T obj) {
        return suc("Success", obj);
    }

    public static <T> ApiRes<T> suc(String message, T obj) {
        return res(HttpStatus.SUCCESS, message, obj);
    }

    public static <T> ApiRes<T> fail() {
        return fail("Query Empty!");
    }

    public static <T> ApiRes<T> fail(String message) {
        log.warn("ApiRes Fail: " + message);
        return res(HttpStatus.NOT_FOUND, message, null);
    }

    public static <T> ApiRes<T> err(String message) {
        log.error("ApiRes Error: " + message);
        return res(HttpStatus.ERROR, message, null);
    }

    public static <T> ApiRes<T> unAuthorized() {
        return unAuthorized("unAuthorization");
    }

    public static <T> ApiRes<T> unAuthorized(String message) {
        return res(HttpStatus.UNAUTHORIZED, message, null);
    }

    public static <T> ApiRes<T> res(Integer status, String message, T data) {
        ApiRes<T> result = new ApiRes<>();
        result.setStatus(status);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
}
