package com.aisino.domain;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-11-22
 * Time: 10:05:30
 */
public abstract class AbstractBaseDomain implements java.io.Serializable {
    public AbstractBaseDomain() {
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


