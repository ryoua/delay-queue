package com.dq.model;

import lombok.Data;
import lombok.ToString;

/**
 * * @Author: RyouA
 * * @Date: 2020/8/26
 **/
@Data
@ToString
public class Job {

    // Job唯一Id
    private String id;

    // 业务名称
    private String topic;

    // 延迟时间
    private Long delay;

    // 执行超时时间
    private Long ttr;

    // 消息体, JSON格式
    private String body;

    // 执行动作
    private String command;

    // 状态
    private JobStatus status;
}