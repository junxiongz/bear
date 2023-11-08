package me.chris.bear.error;

/**
 * @author Christopher
 * @date 2023/11/8
 **/
public interface Success {

    /**
     * 操作成功
     */
    static GeneralMessage ok() {
        return new GeneralMessage();
    }

    /**
     * 操作成功 - 提示信息
     */
    static GeneralMessage ok(String message) {
        return new GeneralMessage().setMessage(message);
    }

    /**
     * 操作成功 - bool
     */
    static GeneralMessage ok(boolean result) {
        return new GeneralMessage().setResult(result);
    }

    /**
     * 创建成功 - 带提示
     */
    static GeneralMessage created(Object id, String message) {
        return new GeneralMessage().setId(String.valueOf(id)).setMessage(message).setStatus(201);
    }

    /**
     * 创建成功
     */
    static GeneralMessage created(Object id) {
        return created(id, null);
    }

    /**
     * 更新成功 - id:提示
     */
    static GeneralMessage updated(Object id, String message) {
        return new GeneralMessage().setId(String.valueOf(id)).setMessage(message).setStatus(202);
    }

    /**
     * 更新成功
     */
    static GeneralMessage updated(Object id) {
        return updated(id, null);
    }

    /**
     * 删除成功 - id:提示
     */
    static GeneralMessage deleted(Object id, String message) {
        return new GeneralMessage().setId(String.valueOf(id)).setMessage(message).setStatus(200);
    }

    /**
     * 删除成功
     */
    static GeneralMessage deleted(Object id) {
        return deleted(id, null);
    }

}
