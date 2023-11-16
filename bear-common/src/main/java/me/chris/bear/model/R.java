package me.chris.bear.model;

import lombok.Data;
import me.chris.bear.error.ErrorDetail;
import me.chris.bear.error.Errors;

import java.io.Serializable;

/**
 * @author Christopher
 * @date 2023/11/10
 **/
@Data
public class R<T> implements Serializable {

    protected boolean success = true;

    /**
     * 响应码
     */
    private int code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    private R() {
    }

    private R(int code, String msg) {
        this.code = code;
        this.msg = msg;
        generaSuccess();
    }

    private R(int code, String msg,T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        generaSuccess();
    }

    private R(ErrorDetail err) {

        this.code = err.getCode();
        this.msg = err.getMessage();
        generaSuccess();
    }

    private R(ErrorDetail err, T data) {
        this.code = err.getCode();
        this.msg = err.getMessage();
        this.data = data;
        generaSuccess();
    }

    public static <T> R<T> ok() {
        return ok(null);
    }

    public static <T> R<T> ok(T data) {
        return new R<>(200,null, data);
    }

    public static <T> R<T> error() {
        return new R<>(Errors.internalServerError());
    }

    /**
     * 自定义 不建议用
     */
    public static <T> R<T> error(int code, String msg) {
        return new R<>(code, msg);
    }

    public static <T> R<T> error(ErrorDetail err) {
        return new R<>(err);
    }

    public static <T> R<T> error(T data) {
        return new R<>(Errors.internalServerError(), data);
    }

    private void generaSuccess() {
        if (this.code < 200 || this.code >= 300) {
            this.success = false;
        }
    }
}
