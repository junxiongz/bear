package me.chris.bear.error;

import lombok.Data;

/**
 * @author Christopher
 * @date 2023/11/8
 **/
@Data
public class GeneralMessage {

    private String id;
    private String message;
    private Boolean result;
    private Integer status = 200;
}
