package top.solove.convert2pdf.util;

import java.io.File;


/**
 * 文档转换器接口
 *
 * @author TXC
 */
public interface PdfConverter {

    static String DEFAULT_FONT_DIR = "C:\\WINDOWS\\Fonts";

    /**
     * 转换文档，将传递过来的文档转换成PDF
     *
     * @param sourceFile word文件，excel文件，ppt文件
     * @param targetFile new出来到一个File对象，pdf内容会输出到这个文件
     * @version 1.0
     */
    void convert(File sourceFile, File targetFile);
}
