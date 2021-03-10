package top.solove.convert2pdf.components;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.aspose.words.FontSettings;
import top.solove.convert2pdf.exception.FileTypeNotMatchException;
import top.solove.convert2pdf.exception.LicenseNotMatchException;
import top.solove.convert2pdf.util.OSinfoUtils;
import top.solove.convert2pdf.util.PdfConverter;


/**
 * 文档转换的抽象父类
 *
 * @author 曹传红
 * @version 1.0
 * @date 2018年9月21日 下午2:44:53
 */
public abstract class AbstractPdfConverter implements PdfConverter {

    private static Logger logger = Logger.getLogger(AbstractPdfConverter.class);

    /**
     * 字体目录
     */
    private String fontDir;

    protected List<String> fileTypes = new ArrayList<>();

    public AbstractPdfConverter() {
        initFileType();
    }

    public AbstractPdfConverter(String fontDir) {
        this.fontDir = fontDir;
        initFileType();
    }

    /**
     * 执行文档转换的方法
     */
    @Override
    public void convert(File sourceFile, File targetFile) {
        logger.info("开始转换:" + sourceFile.getAbsolutePath() + "--->" + targetFile.getAbsolutePath());

        //验证要转换的文件的格式
        String fileName = sourceFile.getName();
        if (!matchFileType(fileName)) {
            throw new FileTypeNotMatchException("文件类型不匹配，请检查文件类型！");
        }

        /*if (!matchLicense()) {
            //估计不会发生这种情况，除非有人故意换掉了授权文件
            throw new LicenseNotMatchException(fileName + "对应的授权文件未通过验证，请联系开发人员!");
        }*/

        //设置字体目录
        setDefaultFontDir();

        //开会转换
        convertToPdf(sourceFile, targetFile);
        logger.info("完成转换:" + sourceFile.getAbsolutePath() + "--->" + targetFile.getAbsolutePath());

    }

    /**
     * 设置默认字体目录
     *
     * @return void
     * @author 曹传红
     * @date 2018年9月21日 下午2:46:04
     * @version 1.0
     */
    private void setDefaultFontDir() {
        if (fontDir == null || "".equals(fontDir)) {
            logger.warn("检测到当前系统是:" + OSinfoUtils.getOsName() + "，未设置字体目录.......");
        } else {
            FontSettings.getDefaultInstance().setFontsFolder(fontDir, true);
        }
    }

    /**
     * 匹配文件类型
     *
     * @param fileName
     * @return boolean
     * @author 曹传红
     * @date 2018年9月21日 下午2:46:11
     * @version 1.0
     */
    private boolean matchFileType(String fileName) {
        int index = fileName.indexOf(".");
        String suffix = fileName.substring(index + 1, fileName.length());
        return fileTypes.contains(suffix);
    }

    /**
     * 让子类来实现初始化文件格式
     */
    protected abstract void initFileType();

    /**
     * 让子类实现授权文件的验证
     *
     * @return 结果
     */
    protected abstract boolean matchLicense();

    /**
     * 子类实现具体的转换操作
     *
     * @param sourceFile 输入文件
     * @param targetFile 输出文件
     */
    protected abstract void convertToPdf(File sourceFile, File targetFile);

}
