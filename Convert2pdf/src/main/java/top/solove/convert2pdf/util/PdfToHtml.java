package top.solove.convert2pdf.util;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @author TXC
 * date: 2021/3/11 17:18
 * @version 1.0
 */
public class PdfToHtml {

    /**
     * 日志对象
     */
    private static Logger logger = LoggerFactory.getLogger(PdfToHtml.class);

    /**
     * PDF文档流转Png
     *
     * @param pdfFileInputStream
     * @return BufferedImage
     */
    public static BufferedImage pdfStreamToPng(InputStream pdfFileInputStream) {
        PDDocument doc = null;
        PDFRenderer renderer = null;
        try {
            doc = PDDocument.load(pdfFileInputStream);
            renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            BufferedImage image = null;
            for (int i = 0; i < pageCount; i++) {
                if (image != null) {
                    image = combineBufferedImages(image, renderer.renderImageWithDPI(i, 144));
                }

                if (i == 0) {
                    // Windows native DPI
                    image = renderer.renderImageWithDPI(i, 144);
                }
                // 产生缩略图
                // BufferedImage srcImage = resize(image, 240, 240);

            }
            return combineBufferedImages(image);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (doc != null) {
                    doc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * BufferedImage拼接处理，添加分割线
     *
     * @param images
     * @return BufferedImage
     */
    public static BufferedImage combineBufferedImages(BufferedImage... images) {
        int height = 0;
        int width = 0;
        for (BufferedImage image : images) {
            //height += Math.max(height, image.getHeight());
            height += image.getHeight();
            width = image.getWidth();
        }
        BufferedImage combo = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = combo.createGraphics();
        int x = 0;
        int y = 0;
        for (BufferedImage image : images) {
            //int y = (height - image.getHeight()) / 2;
            // 线条粗细
            g2.setStroke(new BasicStroke(2.0f));
            // 线条颜色
            g2.setColor(new Color(193, 193, 193));
            // 线条起点及终点位置
            g2.drawLine(x, y, width, y);

            g2.drawImage(image, x, y, null);
            //x += image.getWidth();
            y += image.getHeight();

        }
        return combo;
    }

    /**
     * 通过Base64创建HTML文件并输出html文件
     *
     * @param base64
     * @param htmlPath html保存路径
     */
    public static void createHtmlByBase64(String base64, String htmlPath) {
        StringBuilder stringHtml = new StringBuilder();
        PrintStream printStream = null;
        try {
            // 打开文件
            printStream = new PrintStream(new FileOutputStream(htmlPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 输入HTML文件内容
        stringHtml.append("<html><head>");
        stringHtml.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        stringHtml.append("<title></title>");
        stringHtml.append("</head>");
        stringHtml.append(
                "<body style=\"\r\n" + "    text-align: center;\r\n" + "    background-color: #C1C1C1;\r\n" + "\">");
        stringHtml.append("<img src=\"data:image/png;base64," + base64 + "\" />");
        stringHtml.append("<a name=\"head\" style=\"position:absolute;top:0px;\"></a>");
        //添加锚点用于返回首页
        stringHtml.append("<a style=\"position:fixed;bottom:10px;right:10px\" href=\"#head\">回到首页</a>");
        stringHtml.append("</body></html>");
        try {
            // 将HTML文件内容写入文件中
            printStream.println(stringHtml.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (printStream != null) {
                printStream.close();
            }
        }

    }

    /**
     * bufferedImage 转为 base64编码
     *
     * @param bufferedImage
     * @return
     */
    public static String bufferedImageToBase64(BufferedImage bufferedImage) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String png_base64 = "";
        try {
            // 写入流中
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            // 转换成字节
            byte[] bytes = byteArrayOutputStream.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            // 转换成base64串 删除 \r\n
            png_base64 = encoder.encodeBuffer(bytes).trim()
                    .replaceAll("\n", "")
                    .replaceAll("\r", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return png_base64;
    }
}
