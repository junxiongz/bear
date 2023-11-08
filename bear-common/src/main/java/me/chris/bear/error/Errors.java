package me.chris.bear.error;

import lombok.experimental.UtilityClass;

import java.util.Optional;

/**
 * @author Christopher
 * @date 2023/11/8
 **/
public interface Errors {

    static ErrorDetail with(int status, String message) {
        return with(status, status, message, null);
    }

    static ErrorDetail with(int status, int code, String message) {
        return with(status, status, message, null);
    }

    static ErrorDetail with(int status, int code, String message, Object data) {
        return new Error(code, status, message, data, null, 0);
    }

    static ErrorDetail with(
            int code, int status, String message, Object data, String path, long timestamp) {
        return new Error(code, status, message, data, path, timestamp);
    }

    static Optional<ErrorDetail> resolve(Exception ex) {
        int max = 6;
        Throwable cause = ex;
        while (cause != null) {
            cause = cause.getCause();
            if (cause instanceof ErrorDetailException) {
                return Optional.of(((ErrorDetailException) cause).getDetail());
            }
        }
        return Optional.empty();
    }

    static Error.ErrorBuilder builder() {
        return Error.builder();
    }

    static Error.ErrorBuilder builder(ErrorDetail detail) {
        return builder()
                .code(detail.getCode())
                .status(detail.getStatus())
                .message(detail.getMessage())
                .data(detail.getData())
                .path(detail.getPath())
                .timestamp(detail.getTimestamp());
    }

    static ErrorDetail badRequest() {
        return Holder.BAD_REQUEST;
    }

    static ErrorDetail notFound() {
        return Holder.NOT_FOUND;
    }

    static ErrorDetail tenantRequired() {
        return Holder.TENANT_REQUIRED;
    }

    static ErrorDetail forbidden() {
        return Holder.FORBIDDEN;
    }

    static ErrorDetail unauthorized() {
        return Holder.UNAUTHORIZED;
    }

    static ErrorDetail notImplemented() {
        return Holder.NOT_IMPLEMENTED;
    }

    static ErrorDetail serviceUnavailable() {
        return Holder.SERVICE_UNAVAILABLE;
    }

    static ErrorDetail methodNotAllowed() {
        return Holder.METHOD_NOT_ALLOWED;
    }

    static ErrorDetail conflict() {
        return Holder.CONFLICT;
    }

    static ErrorDetail notAcceptable() {
        return Holder.NOT_ACCEPTABLE;
    }

    static ErrorDetail unsupportedMediaType() {
        return Holder.UNSUPPORTED_MEDIA_TYPE;
    }

    static ErrorDetail internalServerError() {
        return Holder.INTERNAL_SERVER_ERROR;
    }

    static ErrorDetail gone() {
        return Holder.GONE;
    }

    static ErrorDetail badJson() {
        return Holder.BAD_JSON;
    }

    @UtilityClass
    class Holder {

        // General errors
        private static final ErrorDetail BAD_JSON = with(400, "错误的JSON数据");
        private static final ErrorDetail BAD_REQUEST = with(400, "错误的请求");
        private static final ErrorDetail UNAUTHORIZED = with(401, "未授权访问");
        private static final ErrorDetail FORBIDDEN = with(403, "拒绝访问");
        private static final ErrorDetail NOT_FOUND = with(404, "内容未找到");
        private static final ErrorDetail TENANT_REQUIRED = with(400, "需要租户信息");
        private static final ErrorDetail METHOD_NOT_ALLOWED = with(405, "请求方法不支持");
        private static final ErrorDetail CONFLICT = with(409, "操作冲突");
        private static final ErrorDetail NOT_ACCEPTABLE = with(406, "不可接收的请求");
        private static final ErrorDetail GONE = with(410, "接口已不存在");
        private static final ErrorDetail UNSUPPORTED_MEDIA_TYPE = with(415, "不支持的请求类型");
        private static final ErrorDetail INTERNAL_SERVER_ERROR = with(500, "服务内部错误");
        private static final ErrorDetail NOT_IMPLEMENTED = with(501, "操作未实现");
        private static final ErrorDetail SERVICE_UNAVAILABLE = with(503, "服务暂不可用");
    }
}
