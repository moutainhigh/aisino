package com.aisino.domain.einvoice.pdf;

public final class ServerList {

    public String IP;
    public int state;

    public ServerList() {
        this.IP = "127.0.0.1";
        this.state = 0;
    }

    public ServerList(String ip, int state) {
        this.IP = ip;
        this.state = state;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String ip) {
        IP = ip;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}