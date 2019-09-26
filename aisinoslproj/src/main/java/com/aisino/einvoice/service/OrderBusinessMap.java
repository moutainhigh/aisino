package com.aisino.einvoice.service;


import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * 2017-07-19 宋雪冬
 *
 * 添加双rabbitMQ队列配置，用于把发票订单信息上传至大象平台查询发票
 */
public class OrderBusinessMap{


    private RabbitTemplate rabbitTemplate;


    public RabbitTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }

    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}
