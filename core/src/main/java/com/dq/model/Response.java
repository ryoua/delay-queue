package com.dq.model;

import lombok.Data;
import lombok.ToString;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/26
 **/
@Data
@ToString
public class Response {
    // 是否成功
    private boolean success;

    // 错误消息
    private String error;

    // Job Id
    private String id;

    // 消息体
    private String value;
}
