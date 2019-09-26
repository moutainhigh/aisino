package com.aisino.test;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.aisino.protocol.bean.REQUEST_FPKJ;

public class FileDelete_test {

    public static void main(String[] args) {
        REQUEST_FPKJ fpkj = new REQUEST_FPKJ();
        fpkj.setPDF_FILE("f:/13302011050/2015/09/123.txt");
        fpkj.setSWJG_DM("13302011050");
        Long invoiceId = 20000L;
        String pdfFileString = "Y";
     // 如果推送51成功,根据配置文件来判断是否删除pdf
        if ("Y".equals(pdfFileString)) {
            String pdfpathString = fpkj.getPDF_FILE();
            try {
                final File pdfFile = new File(pdfpathString);
                //判断是否是文件并且文件存在
                if(pdfFile.isFile() && pdfFile.exists()){
                    //调用工具类,删除文件
                    FileUtils.deleteQuietly(pdfFile);
                }
                //根据开具id进行判断何时删除文件夹:根据id进行取余计算,如果是10000的倍数就进行删除一次空文件夹
                if(invoiceId%10000 == 0){
                    String swjgsString = fpkj.getSWJG_DM();
                    //读取税务机关代码,然后获取总的文件夹,因为生成pdf之后,是以税务机关代码创建文件夹进行存储的,所以这里读取存储路径之前的地址
                    String fileString = pdfpathString.split(swjgsString)[0]+swjgsString;
                    deleteDirectory(fileString);
                }
                
            } catch (Exception e) {
                System.out.println("删除pdf 出错");
            }
        }
//        try {
//            
//            String fileString = "f:/13302011050/12312/34/";
//            deleteDirectory(fileString);
//        } catch (Exception e) {
//            System.out.println("123123");
//        }
        
    }
    public static boolean deleteDirectory(String sPath) {  
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
        if (!sPath.endsWith(File.separator)) {  
            sPath = sPath + File.separator;  
        }  
        File dirFile = new File(sPath);  
        //如果dir对应的文件不存在，或者不是一个目录，则退出  
        if (!dirFile.exists() || !dirFile.isDirectory()) {  
            return false;  
        }  
        boolean flag = true;  
        //删除文件夹下的所有文件(包括子目录)  
        File[] files = dirFile.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            flag = deleteDirectory(files[i].getAbsolutePath());  
            if (!flag) continue;  
        }  
        if (!flag) return false;  
        //删除当前目录  
        if (dirFile.delete()) {  
            return true;  
        } else {  
            return false;  
        }  
    }
}
