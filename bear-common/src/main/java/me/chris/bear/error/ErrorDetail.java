package me.chris.bear.error;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author Christopher
 * @date 2023/11/8
 **/
@JsonDeserialize(as = Error.class)
public interface ErrorDetail {

    /** Error code */
    int getCode();

    /** Error status - map to http status */
    int getStatus();

    /** Error messages */
    String getMessage();

    /** Extra data attached to this error */
    Object getData();

    /** Path of this error */
    String getPath();

    /** Timestamp of this error */
    long getTimestamp();

    default ErrorDetail withMessage(String format, Object... args) {
        return withMessage(String.format(format, args));
    }

    default ErrorDetail withMessage(String message) {
        return Errors.builder(this).message(message).build();
    }

    default ErrorDetail withTimestamp(long timestamp) {
        return Errors.builder(this).timestamp(timestamp).build();
    }

    default ErrorDetail withPath(String path) {
        return Errors.builder(this).path(path).build();
    }

    default ErrorDetail withCode(int code) {
        return Errors.builder(this).code(code).build();
    }

    default ErrorDetail withData(Object data) {
        return Errors.builder(this).data(data).build();
    }

    default ErrorDetailException asException() {
        return new ErrorDetailException(now());
    }

    default ErrorDetailException asException(Throwable cause) {
        return new ErrorDetailException(now(), cause);
    }

    default ErrorDetailException asException(String message) {
        return withMessage(message).asException();
    }

    default ErrorDetailException asException(String format, Object... args) {
        return withMessage(format, args).asException();
    }

    default ErrorDetailException asException(Throwable cause, String format, Object... args) {
        return new ErrorDetailException(now().withMessage(format, args), cause);
    }

    default ErrorDetail check(boolean condition, String format, Object... args) {
        if (!condition) {
            throw asException(String.format(format, args));
        }
        return this;
    }

    default ErrorDetail check(boolean condition, String message) {
        if (!condition) {
            throw asException(message);
        }
        return this;
    }

    default ErrorDetail check(boolean condition) {
        if (!condition) {
            throw asException();
        }
        return this;
    }

    /** Set timestamp to current */
    default ErrorDetail now() {
        return withTimestamp(System.currentTimeMillis());
    }
}
