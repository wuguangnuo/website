package cn.wgn.website.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import static java.net.HttpURLConnection.*;

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
    private Integer state;
    private String msg;
    private T data;

    public static <T> ApiRes<T> suc() {
        return suc("Success", null);
    }

    public static <T> ApiRes<T> suc(String msg) {
        return suc(msg, null);
    }

    public static <T> ApiRes<T> suc(T obj) {
        return suc("Success", obj);
    }

    public static <T> ApiRes<T> suc(String msg, T obj) {
        return res(HTTP_OK, msg, obj);
    }

    public static <T> ApiRes<T> fail() {
        return fail("Query Empty!");
    }

    public static <T> ApiRes<T> fail(String msg) {
        log.warn("ApiRes Fail: " + msg);
        return res(HTTP_NOT_IMPLEMENTED, msg, null);
    }

    public static <T> ApiRes<T> err(String msg) {
        log.error("ApiRes Error: " + msg);
        return res(HTTP_INTERNAL_ERROR, msg, null);
    }

    public static <T> ApiRes<T> unAuthorized() {
        return unAuthorized("unAuthorization");
    }

    public static <T> ApiRes<T> unAuthorized(String msg) {
        return res(HTTP_UNAUTHORIZED, msg, null);
    }

    public static <T> ApiRes<T> res(Integer state, String msg, T data) {
        ApiRes<T> result = new ApiRes<>();
        result.setState(state);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
