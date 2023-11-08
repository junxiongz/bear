package me.chris.bear.error;

/**
 * @author Christopher
 * @date 2023/11/8
 **/
public class ErrorDetailException extends RuntimeException{

    private final ErrorDetail detail;

    public ErrorDetailException(ErrorDetail detail) {
        super(detail.getCode() + ": " + detail.getMessage());
        this.detail = detail;
    }

    public ErrorDetailException(ErrorDetail detail, Throwable cause) {
        super(detail.getCode() + ": " + detail.getMessage(), cause);
        this.detail = detail;
    }

    public ErrorDetail getDetail() {
        return detail;
    }
}
