package com.aisino.domain.security.entity;

/**
 * Created by Martin.Ou on 2014/9/4.
 */
public final class UserRequestPacket {
    private byte commandClass;
    private byte[] data;

    public byte getCommandClass() {
        return this.commandClass;
    }

    public void setCommandClass(byte comandClass) {
        this.commandClass = comandClass;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}

