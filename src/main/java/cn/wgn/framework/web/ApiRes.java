package cn.wgn.framework.web;

import cn.wgn.framework.constant.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ApiRes 返回
 *
 * @param <T>
 * @author WuGuangNuo
 * @date Created in 2020/2/15 20:12
 */
public class ApiRes<T> {
    private static final Logger log = LoggerFactory.getLogger(ApiRes.class);

    private Integer status;
    private String message;
    private T data;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

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


//httpstatus 利用起来，到apires中和其他需要定义返回的地方。
//减少模块间的引用和依赖
//删除无用注释
//某些非static 的 util改名成service
