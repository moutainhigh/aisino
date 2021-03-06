package com.aisino.twmUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.OutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.awt.image.BufferedImage;

/**
 * 
 * 二维码的生成需要借助MatrixToImageWriter类，该类是由Google提供的，可以将该类拷贝到源码中，这里我将该类的源码贴上，可以直接使用。
 *
 * @author 阳开国
 * @version 1.0 Created on 2016-12-13 下午3:15:51
 */
public final class MatrixToImageWriter {
    
    private static final int BLACK = 0xFF000000;

    private static final int WHITE = 0xFFFFFFFF;

    private MatrixToImageWriter() {
        
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    public static void writeToFile(BitMatrix matrix, String format, File file)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format "+ format + " to " + file);
        }
    }

    public static void writeToStream(BitMatrix matrix, String format,
            OutputStream stream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format "+ format);
        }
    }

    public static void main(String[] args) {
        //生成二维码的实现代码
        try {

            String content = "120605181003;http://www.cnblogs.com/jtmjx";
            String path = "C:\\Users\\andy\\Desktop\\testImage";

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

            Map hints = new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = multiFormatWriter.encode(content,
                    BarcodeFormat.QR_CODE, 400, 400, hints);
            File file1 = new File(path, "餐巾纸.jpg");
            MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
