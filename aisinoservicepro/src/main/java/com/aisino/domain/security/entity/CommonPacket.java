package com.aisino.domain.security.entity;

/**
 * Created by Martin.Ou on 2014/9/4.
 */

public final class CommonPacket {
    private UserRequestPacket userRequest;
    private UserResponsePacket userResponse;
    private byte[] packetHeader;
    private byte commandClass;
    private int dataLength;
    private byte[] data;
    private byte[] crc;
    private byte[] dataPacket;

    public CommonPacket() {
    }


    public UserRequestPacket getUserRequest() {
        return this.userRequest;
    }

    public void setUserRequest(UserRequestPacket userRequest) {
        this.userRequest = userRequest;
    }

    public UserResponsePacket getUserResponse() {
        return this.userResponse;
    }

    public void setUserResponse(UserResponsePacket userResponse) {
        this.userResponse = userResponse;
    }

    public byte[] getDataPacket() {
        return this.dataPacket;
    }

    public void setDataPacket(byte[] dataPacket) {
        this.dataPacket = dataPacket;
    }


    public byte[] getPacketHeader() {
        return this.packetHeader;
    }

    public void setPacketHeader(byte[] packetHeader) {
        this.packetHeader = packetHeader;
    }

    public byte getCommandClass() {
        return this.commandClass;
    }

    public void setCommandClass(byte commandClass) {
        this.commandClass = commandClass;
    }

    public int getDataLength() {
        return this.dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getCrc() {
        return this.crc;
    }

    public void setCrc(byte[] crc) {
        this.crc = crc;
    }

    public byte[] getRequestDataPacket() {
        return this.dataPacket;
    }
}
