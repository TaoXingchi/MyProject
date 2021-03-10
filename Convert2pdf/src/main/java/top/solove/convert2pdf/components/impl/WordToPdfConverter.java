package top.solove.convert2pdf.components.impl;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import org.apache.log4j.Logger;
import top.solove.convert2pdf.components.AbstractPdfConverter;

import java.io.*;

/**
 * 将word转换为PDF
 *
 * @author txc
 * @version 1.0
 * @date 2018年9月21日 下午2:54:55
 */
public class WordToPdfConverter extends AbstractPdfConverter {


    private static Logger logger = Logger.getLogger(WordToPdfConverter.class);


    public WordToPdfConverter() {
        super();
    }

    public WordToPdfConverter(String fontDir) {
        super(fontDir);
    }

    @Override
    protected void initFileType() {
        fileTypes.add("txt");
        fileTypes.add("doc");
        fileTypes.add("docx");
        fileTypes.add("wps");
    }

    @Override
    protected boolean matchLicense() {
        boolean result = false;
        InputStream is = WordToPdfConverter.class.getClassLoader().getResourceAsStream("/static/license.xml");
        License wordLicense = new License();
        try {
            wordLicense.setLicense(is);
            result = true;
        } catch (Exception e) {
            logger.warn("载入word授权文件失败");
        }
        return result;
    }

    @Override
    protected void convertToPdf(File sourceFile, File targetFile) {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(targetFile);
            Document doc = new Document(sourceFile.getAbsolutePath());
            doc.save(os, SaveFormat.PDF);
        } catch (FileNotFoundException e) {
            logger.error("输出到" + targetFile.getAbsolutePath() + "错误:" + e);
        } catch (Exception e) {
            logger.error("转换word出错:" + e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("关闭输出流出错:" + e);
                }
            }
        }
    }


}
