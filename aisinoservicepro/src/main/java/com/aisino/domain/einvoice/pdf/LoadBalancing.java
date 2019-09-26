package com.aisino.domain.einvoice.pdf;

import static com.aisino.domain.einvoice.pdf.ByteUtils.byte2int;
import static com.aisino.domain.einvoice.pdf.ByteUtils.int2byte;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;


public final class LoadBalancing {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadBalancing.class);

    public static final String ErrorLogConfPath = "/ErrorLog.txt";
    public static String ipConfPath = "/IPConfig.txt";
    public static String portConfPath = "/PortConfig.txt";
    public int port = getPort("heartbeat"); // 端口号
    public Vector<ServerList> v = new Vector<ServerList>();// 临时存放服务器列表
    public List<ServerList> vinfo = new ArrayList<ServerList>();// 当前全部服务器列表，带状态
    public static List<String> serverList = new ArrayList<String>();// 可用服务器列表

    private final ReentrantLock lock = new ReentrantLock();

    public static int ipFlag = 0; // IP分配标志

    public static int updateFlag = 0; // 服务器列表更新标志

    public static int flag = 0; // 线程关闭标志
    public static int replyFlag = 1;// 应答标志
    public static byte[] version = new byte[4];// 版本号

    public static byte[] protocol = new byte[4];// 协议

    public static byte[] serverFlag = new byte[4];// 服务端标识

    public static byte[] clientFlag = new byte[4];// 客户端标识

    static {
        ipConfPath = URLDecoder.decode(LoadBalancing.class.getResource(ipConfPath).getPath());
        portConfPath = URLDecoder.decode(LoadBalancing.class.getResource(portConfPath).getPath());
    }

    /*
     * 设置线程关闭标志 flag = 1，线程关闭
     */
    private void threadStop() {
        flag = 1;
    }

    /*
     * 写日志
     */
    /*
     * 写日志
     */
    private static void errorLog(String FileName, String location, String Action, String Error) throws IOException {
        FileWriter fw = new FileWriter(URLDecoder.decode(LoadBalancing.class.getResource(FileName).getPath()), true);
        BufferedWriter bw = new BufferedWriter(fw);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        String str = df.format(new Date()) + "  " + location + "  " + Action + "  " + Error;
        bw.write(str);
        bw.newLine();
        bw.flush();
        bw.close();
        fw.close();
    }

    /*
     * 轮询可用IP，分配给client
     */
    protected String ipAlloca() throws IOException {
        lock.lock();
        String ans = "blank";
        int size = serverList.size();
        if (size > 0) {
            if (ipFlag < serverList.size()) {
                ans = serverList.get(ipFlag);
                ipFlag++;
            } else {
                ipFlag = 0;
                ans = serverList.get(ipFlag);
                ipFlag++;
            }
        }
        lock.unlock();
        return ans;
    }

    /*
     * 配置文件初始化
     */
    private void confInit(String IP) throws IOException {
        File f = new File(ipConfPath);
        if (f.exists()) {
            // System.out.print("文件存在");
        } else {
            // System.out.print("文件不存在");
            f.createNewFile();// 不存在则创建
        }
        BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(f));
        String fileCon = IP;
        bufferedwriter.write(fileCon);

        bufferedwriter.close();
    }

    /*
     * 读配置文件
     */
    private void confRead(String filePath) throws IOException {
        v.clear();
        FileReader fr = new FileReader(filePath);
        BufferedReader bufferedreader = new BufferedReader(fr);
        String instring = bufferedreader.readLine();// .trim();
        /*	判断字符串为空	2014年7月28日16:12:36			*/
        if (!Strings.isNullOrEmpty(instring))
            instring = instring.trim();
        
        /*	判断字符串为空	2014年7月28日16:12:36			*/
        while (!Strings.isNullOrEmpty(instring)) {
            if (0 != instring.length()) {

                // System.out.println(instring);
                ServerList SL = new ServerList(instring, 0);
                v.addElement(SL);
            }
            instring = bufferedreader.readLine();
            if (instring != null)
                instring = instring.trim();
        }
        fr.close();
    }

    // /*
    // * 从配置文件中获取ServerList对象
    // */
    // public ServerList getServer(String str){
    // int loc = str.indexOf(":");
    // String ip = str.substring(0,loc);
    // String state = str.substring(loc+1);
    // int s = Integer.parseInt(state);
    //
    // ServerList SL = new ServerList(ip,s);
    // return SL;
    // }

    /*
     * 写配置文件
     */
    private void confWrite(List<ServerList> serverList) throws IOException {
        File f = new File(ipConfPath);
        if (f.exists()) {
            // System.out.print("文件存在");
        } else {
            // System.out.print("文件不存在");
            f.createNewFile();// 不存在则创建
        }
        BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(f));
        for (int i = 0; i < serverList.size(); i++) {
            String fileCon = serverList.get(i).IP;
            // + ":"
            // + serverInfo.get(i).otherName + ":" + serverInfo.get(i).version +
            // ":"
            // + serverInfo.get(i).ipInfoVersion;
            bufferedwriter.write(fileCon);
            bufferedwriter.newLine();
        }
        bufferedwriter.close();
    }

    /*
     * 配置文件中读取端口号
     */
    private int getPort(String portName) throws IOException {
        int s = 0;
        FileReader fr = new FileReader(portConfPath);
        BufferedReader bufferedreader = new BufferedReader(fr);
        String instring = bufferedreader.readLine();// .trim();
        /*	判断字符串为空	2014年7月28日16:12:36			*/
        if (!Strings.isNullOrEmpty(instring))
            instring = instring.trim();
        /*	判断字符串为空	2014年7月28日16:12:36			*/
        while (!Strings.isNullOrEmpty(instring)) {
            if (0 != instring.length()) {

                // System.out.println(instring);
                int loc = instring.indexOf(":");
                String name = instring.substring(0, loc);
                if (name.equals(portName)) {
                    String port = instring.substring(loc + 1, instring.length());
                    s = Integer.parseInt(port);
                    break;
                }

            }
            instring = bufferedreader.readLine();
            if (instring != null)
                instring = instring.trim();
        }
        fr.close();
        return s;
    }

    /*
     * 构造函数，长连接建立，心跳连接
     */
    private LoadBalancing() throws MyException, IOException {
        serverList.clear();
        vinfo.clear();
        try {
            confRead(ipConfPath);
        } catch (Exception ee) {
        }
        for (int i = 0; i < v.size(); i++) {
            vinfo.add(v.get(i));
        }
        new Thread() {
            public void run() {

                while (flag == 0) {
                    try {

                        boolean tag = false;
                        Socket s = null;
                        // port = getPort("name2");
                        for (int i = 0; i < vinfo.size(); i++) {
                            if (vinfo.get(i).state == 1) {
                                try {
                                    s = new Socket(vinfo.get(i).IP, port);
                                } catch (ConnectException e) {
                                    Thread.sleep(1000);
                                }
                                if (s != null && s.isConnected()) {
                                    tag = true;
                                    break;
                                } else {
                                    ServerList server = new ServerList(vinfo.get(i).IP, 0);
                                    vinfo.set(i, server);
                                    for (int j = 0; j < serverList.size(); j++) {
                                        if ((serverList.get(j)).equals(vinfo.get(i).IP)) {
                                            lock.lock();
                                            serverList.remove(j);
                                            lock.unlock();
                                            break;
                                        }
                                    }
                                }

                            }

                        }
                        if (tag == false) {
                            for (int i = 0; i < vinfo.size(); i++) {
                                try {
                                    System.out.println("okk:" + vinfo.get(i).IP + ";port:" + port);
                                    s = new Socket(vinfo.get(i).IP, port);

                                } catch (ConnectException e) {
                                    Thread.sleep(1000);
                                }
                                if (s != null && s.isConnected()) {
                                    replyFlag = 1;
                                    System.out.println("heartbeat program connected to " +
                                            vinfo.get(i).IP);
                                    ServerList server = new ServerList(vinfo.get(i).IP, 1);
                                    vinfo.set(i, server);
                                    break;
                                }

                            }
                        }
                        if (s != null && s.isConnected()) {
                            // System.out.println("connected");
                            int connectFlag = 0;
                            while (flag == 0) {
                                connectFlag = heartbeatPack(s);
                                // System.out.println(connectFlag);
                                if (connectFlag != 0)
                                    break;
                                Thread.sleep(30000);
                                // System.out.println("heartbeat!");
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            ;
        }.start();
    }

    /*
     * 初始化
     */
    private void init() {
        version = int2byte(0);
        protocol = int2byte(1);
        serverFlag = int2byte(2);
        clientFlag = int2byte(3);
    }

    private static LoadBalancing ins = null;

    public static synchronized LoadBalancing getInstance() throws MyException, IOException {
        if (ins == null) {
            ins = new LoadBalancing();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                LOGGER.error("签章服务器等待建立连接错误");
            }
        }
        return ins;
    }

   /* *//*
     * 将int转化成字节
     *//*
    public static byte[] i2b(int i) {
        return new byte[] { (byte) (i & 0xFF), (byte) ((i >> 8) & 0xFF), (byte) ((i >> 16) & 0xFF), (byte) ((i >> 24) & 0xFF) };
    }

    *//*
     * 将字节转成 int
     *//*
    public static int b2i(byte[] intByte) {
        int addr = intByte[0] & 0xFF;

        addr |= ((intByte[1] << 8) & 0xFF00);

        addr |= ((intByte[2] << 16) & 0xFF0000);

        addr |= ((intByte[3] << 24) & 0xFF000000);

        return addr;
    }*/

    /*
     * 解析应答数据包
     */
    public static int ansAnalysis(byte[] a, int location) {
        byte[] temp = new byte[4];
        System.arraycopy(a, location, temp, 0, 4);

        return byte2int(temp);
    }

    /*
     * socket接收数据 直到接收到bs长度的数据为止
     */
    private static void socketread(InputStream is, byte[] bs) throws IOException {
        int pos = 0, len = 0;
        while (pos < bs.length) {
            len = is.read(bs, pos, bs.length - pos);
            if (len <= 0) {
                errorLog(ErrorLogConfPath, "socketread()", "socket read", "IO异常");
                throw new IOException("socket read error");
            }

            pos += len;
        }
    }

    /*
     * 心跳数据传送接收
     */
    private int heartbeatPack(Socket s) throws MyException {

        version = int2byte(0);
        protocol = int2byte(1);
        serverFlag = int2byte(2);
        clientFlag = int2byte(3);

        byte[] check = new byte[4];// 校验
        check = int2byte(2);
        byte[] serial = new byte[4];// 流水号
        serial = int2byte(3);
        byte[] handle = new byte[4];// socket连接句柄
        handle = int2byte(4);
        byte[] cmd = new byte[4]; // 命令码
        cmd = int2byte(4099);
        byte[] head = new byte[4]; // 命令包长度
        int totalLen = 4 * 10;
        ;
        head = int2byte(totalLen);
        byte[] requestFlag = new byte[4];
        requestFlag = int2byte(replyFlag);
        replyFlag = 0;
        // Socket s = null;
        try {
            // 发送包分两种，一种普通心跳数据包，一种是带服务器状态的数据包
            int length = 0;
            DataOutputStream output = new DataOutputStream(s.getOutputStream());
            output.write(head, 0, 4);
            output.flush();
            output.write(version, 0, 4);
            output.flush();
            output.write(protocol, 0, 4);
            output.flush();
            output.write(check, 0, 4);
            output.flush();
            output.write(serial, 0, 4);
            output.flush();
            output.write(handle, 0, 4);
            output.flush();
            output.write(serverFlag, 0, 4);
            output.flush();
            output.write(clientFlag, 0, 4);
            output.flush();
            output.write(cmd, 0, 4);
            output.flush();
            output.write(requestFlag, 0, 4);
            output.flush();

            // System.out.println("客户端数据包发送成功");
            DataInputStream in = new DataInputStream(s.getInputStream());

            byte[] ansHead = new byte[4];
            socketread(in, ansHead);
            length = byte2int(ansHead);
            byte[] rec = new byte[length - 4];
            if (length < 40) {
                errorLog(ErrorLogConfPath, "heartbeatPack()", "socket receive", "数据包长度异常");
                return 1;
                // throw new MyException(1,"数据包长度异常");
            }
            if (length > 1024 * 1024 * 11) {
                errorLog(ErrorLogConfPath, "heartbeatPack()", "socket receive", "数据长度过大,超过10M");
                return 2;
                // throw new MyException(2,"数据长度过大,超过10M");
            }
            socketread(in, rec);

            // System.out.println("从服务器端接收数据包成功");

            /****************
             * 解析返回包
             */
            int resCmd = ansAnalysis(rec, 28);

            if (resCmd != 4100) {
                errorLog(ErrorLogConfPath, "heartbeatPack()", "socket receive", "应答命令码错误");
                return 3;
                // throw new MyException(4,"应答命令码错误");
            }
            int resValue = ansAnalysis(rec, 32);

            if (resValue != 0) {
                errorLog(ErrorLogConfPath, "heartbeatPack()", "socket receive", "返回值错误");
                return 4;
                // throw new MyException(5,"返回值错误");
            }
            int resSerCount = ansAnalysis(rec, 36);

            int resLen = ansAnalysis(rec, 40);
            if (resLen != 0) {
                int serverCount = ansAnalysis(rec, 44);
                if (serverCount != 0) {
                    lock.lock();
                    vinfo.clear();
                    serverList.clear();
                    int tempLen = 0;
                    for (int i = 0; i < serverCount; i++) {
                        // byte[] ip = new byte[32];
                        // System.arraycopy(rec,44+76*i,ip,0,32);
                        // byte[] otherName = new byte[32];
                        // System.arraycopy(rec,76+76*i,otherName,0,32);
                        // byte[] status = new byte[4];
                        // System.arraycopy(rec,108+76*i,status,0,4);
                        // byte[] version = new byte[4];
                        // System.arraycopy(rec,112+76*i,version,0,4);
                        // byte[] ipInfoVersion = new byte[4];
                        // System.arraycopy(rec,116+76*i,ipInfoVersion,0,4);
                        //
                        // ServerInfo SI = new ServerInfo(new String(ip),new
                        // String(otherName),new String(status),new
                        // String(version),new String(ipInfoVersion));
                        byte[] status = new byte[4];
                        // System.out.println(b2i(status));

                        System.arraycopy(rec, 48 + tempLen, status, 0, 4);
                        int iplen = ansAnalysis(rec, 52 + tempLen);
                        byte[] ip = new byte[iplen];
                        System.arraycopy(rec, 56 + tempLen, ip, 0, iplen);
                        tempLen = tempLen + 4 + 4 + iplen;

                        String tempIP = (new String(ip)).substring(0, iplen);
                        int tempStatus = byte2int(status);
                        ServerList SL = new ServerList(tempIP, tempStatus);

                        vinfo.add(SL);
                        if (tempStatus == 1)
                            serverList.add(tempIP);
                        // System.out.println("收到服务器状态包");

                    }
                    lock.unlock();
                    confWrite(vinfo);
                    replyFlag = 0;
                    confRead(ipConfPath);
                    if (vinfo.size() == v.size()) {
                        for (int i = 0; i < v.size(); i++) {
                            if (!((v.get(i).IP).equals(vinfo.get(i).IP))) {
                                replyFlag = 1;
                            }
                        }
                    } else {
                        replyFlag = 1;
                    }
                }
                // 存入本地配置文件中
            } else {
                replyFlag = 0;
            }
            if (resSerCount != updateFlag) {
                replyFlag = 1;
                updateFlag = resSerCount;
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage());
//            System.out.println(e);
            // s=null;
            return -1;
        }
        return 0;
    }

    public static void main(String[] args) throws MyException, IOException {
        LoadBalancing LB = LoadBalancing.getInstance();
    }
}