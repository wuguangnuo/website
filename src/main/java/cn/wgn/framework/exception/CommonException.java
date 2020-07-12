package cn.wgn.framework.exception;

/**
 * 通用异常
 *
 * @author WuGuangNuo
 * @date Created in 2020/5/30 22:05
 */
public class CommonException extends RuntimeException {
    /**
     * CODE
     */
    private Integer code;
    /**
     * MESSAGE
     */
    private String message;

    public CommonException(String message) {
        this.message = message;
    }

    public CommonException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public CommonException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "[" + code + "]" + message;
    }
}
