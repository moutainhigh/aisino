package com.aisino.domain.einvoice.pdf;

/*
 * 自定义异常类
 * 1：数据包长度异常
 * 2：数据长度过大,超过10M
 * 3：应答数据包长度异常
 * 4：应答命令码错误
 * 5：连接被拒绝
 * 6:无可用服务器
 * 8:参数为空
 * 9:请求无响应
 */
public final class MyException extends Exception {

    private static final long serialVersionUID = 9061889935750293066L;
    private int exId;
    private String exDes;

    public MyException(int id, String des) {
        this.exId = id;
        this.exDes = des;
    }

    public int getExId() {
        return exId;
    }

    public void setExId(int exId) {
        this.exId = exId;
    }

    public String getExDes() {
        return exDes;
    }

    public void setExDes(String exDes) {
        this.exDes = exDes;
    }

}