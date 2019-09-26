package com.aisino.domain.rabbit.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * MQ消息队列基类
 * <p/>
 * Created by Bourne.Lv on 2014/09/25.
 */
public abstract class AbstractQueueDomain implements java.io.Serializable {

    public AbstractQueueDomain() {
        id = null;
    }

    private Long id;

    public final Long getId() {
        return id;
    }

    public final void setId(Long id) {
        this.id = id;
    }

    public abstract Boolean isNullObject();

    protected Timestamp obtainValidTimestamp(final Timestamp srcDate) {
        return srcDate == null ? null : new Timestamp(srcDate.getTime());
    }

    protected Date obtainValidDate(final Date srcDate) {
        return srcDate == null ? null : new Date(srcDate.getTime());
    }
}
