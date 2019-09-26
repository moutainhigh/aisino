package com.aisino.domain.einvoice.pdf;

import static com.aisino.domain.einvoice.pdf.ByteUtils.byte2int;
import static com.aisino.domain.einvoice.pdf.ByteUtils.int2byte;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;


public final class Client {

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    public static String ErrorLogConfPath = "/ErrorLog.txt";

    public int port;

    public static byte[] version = new byte[4];// 版本号
    // version = i2b(0);

    public static byte[] protocol = new byte[4];// 协议
    // protocol = i2b(1);

    public static byte[] serverFlag = new byte[4];// 服务端标识
    // serverFlag = i2b(5);

    public static byte[] clientFlag = new byte[4];// 客户端标识
    // clientFlag = i2b(6);

    public static byte[] left = new byte[4]; // 签章位置Left
    // left = i2b(230);

    public static byte[] top = new byte[4]; // 签章位置Top
    // top = i2b(100);

    public static byte[] right = new byte[4]; // 签章位置Right
    // right = i2b(320);

    public static byte[] bottom = new byte[4]; // 签章位置Bottom
    // bottom = i2b(200);

    public static byte[] pageIndex = new byte[4]; // pageIndex
    // pageIndex = i2b(0);
    // public int port = getPort("name1"); //端口号

    public Client() throws IOException {
        port = getPort("client"); // 端口号
    }

    /*
     * 初始化 l:签章位置Left t:签章位置Top r:签章位置Right b:签章位置Bottom p:pageIndex
     */
    public void init(int l, int t, int r, int b, int p) {
        version = int2byte(0);
        protocol = int2byte(1);
        serverFlag = int2byte(5);
        clientFlag = int2byte(6);
        left = int2byte(l);
        top = int2byte(t);
        right = int2byte(r);
        bottom = int2byte(b);
        pageIndex = int2byte(p);
    }


    /*
     * 写日志
     */
    private static void errorLog(String FileName, String location, String Action, String Error) throws IOException {
        FileWriter fw = new FileWriter(URLDecoder.decode(Client.class.getResource(FileName).getPath()), true);
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
     * 解析应答数据包
     */
    private static int ansAnalysis(byte[] a, int location) {
        byte[] temp = new byte[4];
        System.arraycopy(a, location, temp, 0, 4);

        return byte2int(temp);
    }

    /*
     * socket接收数据 直到接收到bs长度的数据为止
     */
    private static void socketread(String socketIP, InputStream is, byte[] bs) throws IOException {
        int pos = 0, len = 0;
        while (pos < bs.length) {
            len = is.read(bs, pos, bs.length - pos);
            if (len <= 0) {
                errorLog(ErrorLogConfPath, socketIP + " socketread()", "socket read", "IO异常");
                throw new IOException("socket read error");
            }

            pos += len;
        }
    }

    /*
      * 
      */
    public AddStampRec addStampInter(String serverIP, int port, String stampID, byte[] pdf, byte[] userName) throws MyException, IOException {
        int wrongPoint = 0;
        AddStampRec recData = new AddStampRec();
        byte[] check = new byte[4];// 校验
        check = int2byte(2);

        byte[] serial = new byte[4];// 流水号
        serial = int2byte(3);

        byte[] handle = new byte[4];// socket连接句柄
        handle = int2byte(4);

        byte[] cmd = new byte[4]; // 命令码
        cmd = int2byte(1);

        byte[] head = new byte[4]; // 命令包长度
        byte[] IDLength = new byte[4]; // ID长度
        byte[] ID = stampID.getBytes();

        // System.out.println(ID.length);
        IDLength = int2byte(ID.length);
        byte[] signatureLength = new byte[4]; // 签章名长度
        signatureLength = int2byte(0);

        byte[] nameLength = new byte[4]; // 用户名长度
        nameLength = int2byte(0);

        nameLength = int2byte(userName.length);
        byte[] pdfLen = new byte[4];

        pdfLen = int2byte(pdf.length);
        int totalLen = 4 * 18 + pdf.length + ID.length + userName.length;
        head = int2byte(totalLen);

        Socket s = null;
        final DateTime socketDateTime = DateTime.now();
        try {
            s = new Socket(serverIP, port);
            wrongPoint = 1;
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

            output.write(left, 0, 4);
            output.flush();

            output.write(top, 0, 4);
            output.flush();

            output.write(right, 0, 4);
            output.flush();

            output.write(bottom, 0, 4);
            output.flush();

            output.write(pageIndex, 0, 4);
            output.flush();

            output.write(IDLength, 0, 4);
            output.flush();

            output.write(ID, 0, ID.length);
            output.flush();

            output.write(signatureLength, 0, 4);
            output.flush();

            output.write(nameLength, 0, 4);
            output.flush();

            output.write(userName, 0, userName.length);
            output.flush();

            output.write(pdfLen, 0, 4);
            output.flush();

            output.write(pdf, 0, pdf.length);
            output.flush();

            wrongPoint = 2;
            DataInputStream in = new DataInputStream(s.getInputStream());

            byte[] ansHead = new byte[4];
            socketread(serverIP, in, ansHead);
            wrongPoint = 3;
            length = byte2int(ansHead);
            byte[] rec = new byte[length - 4];
            if (length < 44) {
                errorLog(ErrorLogConfPath, serverIP + " addStampInter()", "socket receive", "数据包长度异常");
                throw new MyException(1, "数据包长度异常");
            }
            if (length > 1024 * 1024 * 11) {
                errorLog(ErrorLogConfPath, serverIP + " addStampInter()", "socket receive", "数据长度过大,超过10M");
                throw new MyException(2, "数据长度过大,超过10M");
            }
            socketread(serverIP, in, rec);

            /****************
             * 解析返回包
             */
            int resCmd = ansAnalysis(rec, 28);
            int resValue = ansAnalysis(rec, 32);
            int resPdfLen = ansAnalysis(rec, 36);
            recData.resValue = resValue;
            if (length != 44 + resPdfLen) {
                errorLog(ErrorLogConfPath, serverIP + " addStampInter()", "socket receive", "应答数据包长度异常");
                throw new MyException(3, "应答数据包长度异常");
            }
            if (resCmd != 2) {
                errorLog(ErrorLogConfPath, serverIP + " addStampInter()", "socket receive", "应答命令码错误");
                throw new MyException(4, "应答命令码错误");

            }
            if (resValue == 0) {
                recData.pdf = new byte[resPdfLen];
                System.arraycopy(rec, 40, recData.pdf, 0, resPdfLen);

            }
            if (resValue != 0) {
                errorLog(ErrorLogConfPath, serverIP + " addStampInter()", "socket receive", "服务器处理失败，返回值：" + resValue);
            }

        } catch (ConnectException e2) {
            errorLog(ErrorLogConfPath, serverIP + " addStampInter()", "socket communication", "连接被拒绝");
            throw new MyException(5, "连接被拒绝");
        } catch (IOException e) {
            final DateTime currDateTime = DateTime.now();
            errorLog("./ErrorLog.txt", serverIP + "wrongPoint: " + wrongPoint + socketDateTime.toString() + " addStampInter() " + currDateTime, "socket communication", "IO异常");
            throw new MyException(9, "请求无响应");
        } catch (Exception e2) {
            errorLog("./ErrorLog.txt", serverIP + " addStampInter()", "socket ", "未知异常导致通讯失败");
            throw new MyException(99, "未知异常导致通讯失败");
        } finally {
            try {
                if (s != null)
                    s.close();
            } catch (IOException e1) {
                errorLog(ErrorLogConfPath, serverIP + " addStampInter()", "socket receive", "IO异常");
            }
        }

        return recData;
    }

    public int verifyStampInter(String serverIP, int port, byte[] pdf) throws MyException, IOException {

        byte[] check = new byte[4];// 校验
        check = int2byte(2);

        byte[] serial = new byte[4];// 流水号
        serial = int2byte(3);

        byte[] handle = new byte[4];// socket连接句柄
        handle = int2byte(4);

        byte[] cmd = new byte[4]; // 命令码
        cmd = int2byte(3);

        byte[] head = new byte[4]; // 命令包长度
        byte[] pdfLen = new byte[4];

        pdfLen = int2byte(pdf.length);
        int totalLen = 4 * 10 + pdf.length;
        head = int2byte(totalLen);
        int resValue = 0;
        Socket serverSocket = null;

        try {
            serverSocket = new Socket(serverIP, port);
            int length = 0;

            DataOutputStream output = new DataOutputStream(serverSocket.getOutputStream());
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

            output.write(pdfLen, 0, 4);
            output.flush();

            output.write(pdf, 0, pdf.length);
            output.flush();

            final DataInputStream in = new DataInputStream(serverSocket.getInputStream());
            byte[] ansHead = new byte[4];
            socketread(serverIP, in, ansHead);
            length = byte2int(ansHead);

            byte[] rec = new byte[length - 4];

            if (length < 40) {
                errorLog(ErrorLogConfPath, serverIP + " verifyStampInter()", "socket receive", "数据包长度异常");
                throw new MyException(1, "数据包长度异常");
            }

            if (length > 1024 * 1024 * 11) {
                errorLog(ErrorLogConfPath, serverIP + " verifyStampInter()", "socket receive", "数据长度过大,超过10M");
                throw new MyException(2, "数据长度过大,超过10M");
            }

            socketread(serverIP, in, rec);
            /****************
             * 解析返回包
             */
            int resCmd = ansAnalysis(rec, 28);
            resValue = ansAnalysis(rec, 32);

            if (resCmd != 4) {
                errorLog(ErrorLogConfPath, serverIP + " verifyStampInter()", "socket receive", "应答命令码错误");
                throw new MyException(4, "应答命令码错误");
            }

            if (resValue != 0) {
                errorLog(ErrorLogConfPath, serverIP + " verifyStampInter()", "socket receive", "服务器处理失败，返回值：" + resValue);
            }

        } catch (ConnectException e2) {
            errorLog(ErrorLogConfPath, serverIP + " verifyStampInter()", "socket communication", "连接被拒绝");
            throw new MyException(5, "连接被拒绝");
        } catch (IOException e) {
            errorLog(ErrorLogConfPath, serverIP + " verifyStampInter()", "socket communication", "IO异常");
            throw new MyException(9, "请求无响应");
        } finally {
            try {
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException e1) {
                errorLog(ErrorLogConfPath, serverIP + " verifyStampInter()", "socket communication", "IO异常");
            }
        }

        return resValue;
    }

    /*
     * 配置文件中读取端口号
     */
    private int getPort(String portName) throws IOException {
        int s = 0;
        final FileReader fr = new FileReader(URLDecoder.decode(Client.class.getResource("/PortConfig.txt").getPath()));
        final BufferedReader bufferedreader = new BufferedReader(fr);
        String instring = bufferedreader.readLine();// .trim();

        /*	判断字符串为空	2014年7月28日16:12:36			*/
        if (!Strings.isNullOrEmpty(instring))
            instring = instring.trim();
        try {
        /*	判断字符串为空	2014年7月28日16:12:36			*/
            while (!Strings.isNullOrEmpty(instring)) {
                if (0 != instring.length()) {
                    final int loc = instring.indexOf(":");
                    final String name = instring.substring(0, loc);

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
        } finally {
            bufferedreader.close();
            fr.close();
        }

        return s;
    }

    /*
     * PDF文件签章服务 stampID：印章ID pdf：pdf内容 userName：用户名
     * 
     * 返回值： AddStampRec：已签章的PDF对象，包括返回值和签过章的PDF
     */
    public AddStampRec addStamp(String stampID, byte[] pdf, byte[] userName) throws IOException {
        AddStampRec recData = new AddStampRec();

        try {
            final String serverIP = (LoadBalancing.getInstance()).ipAlloca();
            if (serverIP.equals("blank")) {
                recData.resValue = 6;
                recData.pdf = new String("无可用服务器").getBytes();
                errorLog(ErrorLogConfPath, "addStamp()", "addStamp", "无可用服务器");
            } else {
//                System.out.println("访问服务器：" + IP);
                recData = addStampInter(serverIP, port, stampID, pdf, userName);
            }
        } catch (MyException e) {
            recData.resValue = e.getExId();
            recData.pdf = e.getExDes().getBytes();
        }

        return recData;
    }

    /*
     * PDF文件验章服务 pdf：pdf内容 返回值：验章返回值
     */
    private int verifyStamp(byte[] pdf) throws IOException {
        int resValue = -1;
        try {
            final String serverIP = (LoadBalancing.getInstance()).ipAlloca();
            if (serverIP.equals("blank")) {
                resValue = 6;
                errorLog(ErrorLogConfPath, "verifyStamp()", "verifyStamp", "无可用服务器");
            } else {
                resValue = verifyStampInter(serverIP, port, pdf);
            }
        } catch (MyException e) {
            resValue = e.getExId();
        }

        return resValue;

    }

//    public static void main(String[] args) throws IOException, MyException, InterruptedException {
//
//        final LoadBalancing LB = LoadBalancing.getInstance();// 初始化模块中调用
//        // Thread.sleep(40000);
//        //
//        // 实际业务系统开始多线程调用
//        for (int i = 0; i < 100; i++) {
////            System.out.println("每输入一串字符回车,启动一个线程：");
//            Scanner scanner = new Scanner(System.in);
//            String str = scanner.nextLine();
//            new Thread() {
//                public void run() {
//                    // while(true)
//                    // {
//                    try {
//                        AddStampRec ansData = null;
//                        Client C = new Client();
//                        // C.init(300, 360, 400, 420, 0);
//                        C.init(290, 790, 410, 870, 0);
//                        File file = new File("./nita5.pdf");
//                        DataInputStream in = new DataInputStream(new FileInputStream(file));
//                        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
//
//                        // byte[] temp = new byte[9999999];
//                        // int len = input.read(temp);
//                        // System.out.println(len);
//
//                        byte[] temp = new byte[1024];
//                        int size = 0;
//                        while ((size = in.read(temp)) != -1) {
//                            out.write(temp, 0, size);
//                        }
//                        in.close();
//                        byte[] content = out.toByteArray();
//
////                        System.out.println(content.length);
//
//                        // String Id = "6c84b1bded90a14fa787e04ff630cf71";
//                        String Id = "61c92ffa1e11b6b7d41a5e136fb0a315";
//                        byte[] test = new byte[0];
////                        System.out.println(Thread.currentThread());
//                        ansData = C.addStamp(Id, content, test);
////                        System.out.println(Thread.currentThread() + "  返回值：" + ansData.resValue);
////                        System.out.println(Thread.currentThread() + "  数据长度：" + ansData.pdf.length);
//                        File newFile = new File("D:\\re5.pdf");
//                        if (!newFile.exists())
//                            newFile.createNewFile();
//                        FileOutputStream fileOut = new FileOutputStream(newFile);
//                        fileOut.write(ansData.pdf, 0, ansData.pdf.length);
////                        System.out.println(Thread.currentThread() + " finished");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    // }
//                };
//
//            }.start();
//        }
//        // }
//        // LB.threadStop();//系统退出时调用，关闭长连接
//
//    }
}