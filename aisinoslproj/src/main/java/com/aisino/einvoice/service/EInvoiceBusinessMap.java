package com.aisino.einvoice.service;


import com.aisino.domain.einvoice.repository.EInvoiceSubRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Created by Martin.Ou on 2014/9/22.
 *
 * 构建EInvoiceService的电商Map，保证一个电商一个DAO和消息队列
 */
public class EInvoiceBusinessMap {

    private EInvoiceSubRepository repository;

    private RabbitTemplate rabbitTemplate;

    public EInvoiceSubRepository getRepository() {
        return repository;
    }

    public void setRepository(EInvoiceSubRepository repository) {
        this.repository = repository;
    }

    public RabbitTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }

    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}
