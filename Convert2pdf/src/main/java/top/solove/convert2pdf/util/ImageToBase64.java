package top.solove.convert2pdf.util;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 图片处理
 *
 * @author fuchaolei
 * @version 1.0.0
 * @date 2018/12/17
 * @since JDK 1.8
 */
public class ImageToBase64 {
    /**
     * 图片转base64
     *
     * @param imgFile 图片地址
     * @return base64码
     */
    public static String getImageStr(String imgFile) {
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * base64转图片
     *
     * @param base64 64字符串
     * @param path   图片路径
     * @return 图片地址
     */
    public static String generateImage(String base64, String path) {
        if (base64 == null) {
            return "";
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(base64);
            for (int i = 0; i < b.length; i++) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            try {
                System.out.println(path);
                Runtime.getRuntime().exec("chmod -R 755 " + path);
            } catch (Exception e) {
            }
            return path;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 把图片进行压缩
     *
     * @param imgsrc       图片源
     * @param imgdist      s生成后的
     * @param outputWidth  输出宽度
     * @param outputHeight 输出高度
     * @param proportion   是否是等比缩放
     */
    public static void resizePng(String imgsrc, String imgdist, int outputWidth, int outputHeight,
                                 boolean proportion) {
        try {
            File fromFile = new File(imgsrc);
            File toFile = new File(imgdist);
            BufferedImage bi2 = ImageIO.read(fromFile);
            int newWidth;
            int newHeight;
            // 判断是否是等比缩放  
            if (proportion) {
                // 为等比缩放计算输出的图片宽度及高度  
                double rate1 = ((double) bi2.getWidth(null)) / (double) outputWidth + 0.1;
                double rate2 = ((double) bi2.getHeight(null)) / (double) outputHeight + 0.1;
                // 根据缩放比率大的进行缩放控制  
                double rate = rate1 < rate2 ? rate1 : rate2;
                newWidth = (int) (((double) bi2.getWidth(null)) / rate);
                newHeight = (int) (((double) bi2.getHeight(null)) / rate);
            } else {
                newWidth = outputWidth;
                newHeight = outputHeight;
            }
            BufferedImage to = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = to.createGraphics();
            to = g2d.getDeviceConfiguration().createCompatibleImage(newWidth, newHeight,
                    Transparency.TRANSLUCENT);
            g2d.dispose();
            g2d = to.createGraphics();
            @SuppressWarnings("static-access")
            Image from = bi2.getScaledInstance(newWidth, newHeight, Image.SCALE_AREA_AVERAGING);
            g2d.drawImage(from, 0, 0, null);
            g2d.dispose();
            ImageIO.write(to, "png", toFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}