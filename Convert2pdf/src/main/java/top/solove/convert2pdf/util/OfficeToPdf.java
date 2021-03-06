package top.solove.convert2pdf.util;



import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.*;
import org.apache.commons.lang3.StringUtils;
import org.jodconverter.JodConverter;
import org.jodconverter.document.DefaultDocumentFormatRegistry;
import org.jodconverter.document.DocumentFormat;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Pattern;

/***
 *
 * @author LUOZHIJUN
 * @date Create in 14:21 2020/12/14
 * @version 1.0.0
 * @since JDK 1.8
 *
 */
public class OfficeToPdf {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfficeToPdf.class);
    private static final Integer NUM_26 = 26;
    private static final String LINUX = "Linux.*";
    private static final String WINDOWS = "Windows.*";
    private static final String MAC = "Mac.*";
    private static final String DOC = "doc";
    private static final String DOCX = "docx";
    private static final Integer NEGATIVE_5 = -5;
    private static final Integer NEGATIVE_30 = -30;
    private static final Integer NUM_206 = 206;
    private static final float FLOAT_2 = 0.2f;
    private static final float FLOAT_4 = 0.4f;
    private static final Integer NUM_2002 = 2002;
    private static final Integer NUM_2003 = 2003;
    private static final Integer NUM_2004 = 2004;
    private static final Integer NUM_2005 = 2005;
    private static final Integer NUM_2006 = 2006;
    private static final Integer NUM_60 = 60;
    private static final Integer NUM_2 = 2;
    private static final Integer NEGATIVE_1 = -1;
    private static final Integer NUM_8 = 8;
    private static final Integer NUM_40 = 40;
    private static final Integer NUM_1 = 1;
    private static final Integer NUM_9 = 9;
    private static final Integer NUM_3 = 3;
    private static final Integer NUM_200 = 200;
    private static final Integer NUM_10 = 10;
    private static final Integer NUM_30 = 30;
    private static final Integer NUM_100 = 100;
    private static final Integer NUM_1000 = 1000;
    private static final String XLSX = "xlsx";
    private static final String XLS = "xls";

    public static void main(String[] args) {
        String outputFilePath = System.getProperty("user.dir") + "\\officeToPdf\\";
        String addWarter = System.getProperty("user.dir") + "\\addWarter\\";
        String path = "http://file.daqsoft.com/uploadfile/server/zytf-pack/test/???????????????????????????_1607930651175.xlsx";
        URL url = null;
        try {
            url = new URL(path);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(NUM_60 * NUM_100);
            urlConnection.setUseCaches(true);
            urlConnection.setAllowUserInteraction(true);
            InputStream in = urlConnection.getInputStream();
            //Office?????????Pdf
            outputFilePath = outputFilePath.concat(StringUtils.substring(path,
                    path.lastIndexOf("/") + 1, path.lastIndexOf("."))).concat(".pdf");
            OfficeToPdf.office2pdf(in, outputFilePath, null);
            if (!new File(addWarter).exists()) {
                new File(addWarter).mkdir();
            }
            //??????????????????????????????
            // ????????????pdf??????
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(
                    new File(addWarter.concat(StringUtils.substring(path,
                            path.lastIndexOf("/") + 1, path.lastIndexOf("."))).concat("_Watermark")
                            .concat(".pdf"))));
            // ???pdf??????????????????????????????
            setWatermark(bos, outputFilePath, "??????user");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ???Office???????????????PDF. ????????????OpenOffice
     *
     * @param in             ?????????,?????????. ?????????Office2003-2007?????????????????????, ??????.doc, .docx, .xls, .xlsx, .ppt, .pptx???.
     * @param outputFilePath ????????????.????????????.
     * @param fileName       ????????????
     */
    public static void office2pdf(InputStream in, String outputFilePath, String fileName) {
        OfficeManager officeManager = null;
        try {
            //??????OpenOffice.org????????????
            String officeHome = getOfficeHome();
            // ??????OpenOffice
            officeManager = startUpManager(officeHome);
            JodConverter.convert(in).as(getSuffix(fileName)).to(new File(outputFilePath))
                    .as(DefaultDocumentFormatRegistry.PDF).execute();
        } catch (OfficeException e) {
            e.printStackTrace();
            LOGGER.error("office????????????????????????!???????????????" + e.getMessage());
        } finally {
            try {
                if (null != officeManager) {
                    officeManager.stop();
                    LOGGER.info("office?????????????????????");
                }
            } catch (OfficeException e) {
                LOGGER.error("office????????????????????????????????????" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * ??????????????????
     *
     * @param officeHome OpenOffice.org????????????
     * @return ???????????????????????????
     */
    public static OfficeManager startUpManager(String officeHome) {
        OfficeManager officeManager = null;
        try {
            LOGGER.info("office????????????????????????!");
            officeManager = LocalOfficeManager.builder().officeHome(officeHome)
                    .portNumbers(NUM_2002)
                    .maxTasksPerProcess(NUM_1000)
                    .taskQueueTimeout(NUM_60 * NUM_1000 * NUM_26)
                    .taskExecutionTimeout(NUM_60 * NUM_1000 * NUM_10).install().build();
            // ??????????????????
            officeManager.start();
            LOGGER.info("office????????????????????????!");
            return officeManager;
        } catch (OfficeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DocumentFormat getSuffix(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return null;
        }
        if (fileName.endsWith(XLSX)) {
            return DefaultDocumentFormatRegistry.XLSX;
        } else if (fileName.endsWith(XLS)) {
            return DefaultDocumentFormatRegistry.XLS;
        } else if (fileName.endsWith(DOC)) {
            return DefaultDocumentFormatRegistry.DOC;
        } else if (fileName.endsWith(DOCX)) {
            return DefaultDocumentFormatRegistry.DOCX;
        } else {
            return null;
        }
    }

    /**
     * ???????????????????????????PDF????????????
     *
     * @param inputFilePath ??????????????????
     * @return ????????????
     */
    public static String getOutputFilePath(String inputFilePath) {
        String outputFilePath = "";
        String temp = inputFilePath.substring(inputFilePath.lastIndexOf("."));
        outputFilePath = inputFilePath.replaceAll(temp, ".pdf");
        return outputFilePath;
    }

    /**
     * ??????OpenOffice????????????
     *
     * @return ??????
     */
    public static String getOfficeHome() {
        String osName = System.getProperty("os.name");
        if (Pattern.matches(LINUX, osName)) {
            return "/opt/libreoffice6.1";
        } else if (Pattern.matches(WINDOWS, osName)) {
            return "E:/LibreOffice";
        } else if (Pattern.matches(MAC, osName)) {
            return "/Application/OpenOffice.org.app/Contents";
        }
        return null;
    }

    /**
     * @param bos           ?????????????????????
     * @param input         ???PDF??????
     * @param waterMarkName ??????????????????
     * @throws DocumentException
     * @throws IOException
     */
    public static void setWatermark(BufferedOutputStream bos, String input, String waterMarkName) {

        PdfReader reader = null;
        try {
            reader = new PdfReader(input);
            PdfStamper stamper = new PdfStamper(reader, bos);
            // ??????????????? +1, ?????????1????????????
            int total = reader.getNumberOfPages() + 1;
            // ??????classpath??????????????????
            BaseFont base = null;
            base = BaseFont.createFont("public/fonts/simfang.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            // ??????
            int interval = NEGATIVE_5;
            // ????????????????????????????????????
            int textH = 0, textW = 0;
            JLabel label = new JLabel();
            label.setText(waterMarkName);
            FontMetrics metrics = label.getFontMetrics(label.getFont());
            textH = metrics.getHeight();
            textW = metrics.stringWidth(label.getText());
            System.out.println("textH: " + textH);
            System.out.println("textW: " + textW);
            // ?????????????????????
            PdfGState gs = new PdfGState();
            gs.setFillOpacity(FLOAT_2);
            gs.setStrokeOpacity(FLOAT_2);

            com.lowagie.text.Rectangle pageSizeWithRotation = null;
            PdfContentByte content = null;
            for (int i = 1; i < total; i++) {
                // ????????????????????????
                content = stamper.getUnderContent(i);
                content.saveState();
                content.setGState(gs);
                // ???????????????????????????
                content.beginText();
                content.setFontAndSize(base, NUM_10 * NUM_2);
                // ?????????????????????????????????
                pageSizeWithRotation = reader.getPageSizeWithRotation(i);
                float pageHeight = pageSizeWithRotation.getHeight();
                float pageWidth = pageSizeWithRotation.getWidth();
                // ????????????????????????????????? ???????????????30????????????
                for (int height = interval + textH; height < pageHeight; height = height + textH * NUM_3) {
                    for (int width = interval + textW; width < pageWidth + textW; width = width
                            + textW * NUM_2) {
                        content.showTextAligned(Element.ALIGN_LEFT, waterMarkName, width - textW,
                                height - textH, NUM_30);
                    }
                }
                content.endText();
            }
            // ??????
            stamper.close();
            reader.close();
        } catch (IOException e) {
            LOGGER.error("??????????????????????????????" + e.getMessage());
            e.printStackTrace();
        } catch (DocumentException e) {
            LOGGER.error("???????????????DocumentException???????????????" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param bos           ?????????????????????
     * @param input         ???PDF??????
     * @param waterMarkImag ????????????
     * @throws DocumentException
     * @throws IOException
     */
    public static void setWatermarkImag(BufferedOutputStream bos, String input, String waterMarkImag) {
        PdfReader reader = null;
        LOGGER.info("??????????????????", waterMarkImag);
        LOGGER.info("???????????????", input);
        try {
            reader = new PdfReader(input);
            PdfStamper stamper = new PdfStamper(reader, bos);
            // ??????????????? +1, ?????????1????????????
            int total = reader.getNumberOfPages() + 1;
            // ??????classpath??????????????????
            BaseFont base = null;
            base = BaseFont.createFont("public/fonts/simfang.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            // ?????????????????????
            PdfGState gs = new PdfGState();
            gs.setFillOpacity(FLOAT_2);
            gs.setStrokeOpacity(FLOAT_2);
            PdfContentByte content = null;
            for (int i = 1; i < total; i++) {
                // ????????????????????????
                content = stamper.getUnderContent(i);
                content.saveState();
                content.setGState(gs);
                // ???????????????????????????
                content.beginText();
                content.setFontAndSize(base, NUM_2 * NUM_10);
                Image image = Image.getInstance(waterMarkImag);
                image.setAlignment(Image.LEFT | Image.TEXTWRAP);
                image.setBorder(Image.BOX);
                image.setBorderWidth(NUM_10);
                //??????
                image.scaleToFit(NUM_1000, NUM_8 * NUM_9);
                //??????
                image.setRotationDegrees(NEGATIVE_30);
                // set the first background image of the absolute
                image.setAbsolutePosition(NUM_200, NUM_206);
                image.scaleToFit(NUM_200, NUM_200);
                content.addImage(image);
                content.setColorFill(Color.BLACK);

                content.endText();
            }
            // ??????
            stamper.close();
            reader.close();
        } catch (IOException e) {
            LOGGER.error("?????????????????????IOException???????????????" + e.getMessage());
            e.printStackTrace();
        } catch (DocumentException e) {
            LOGGER.error("?????????????????????DocumentException???????????????" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * ??????????????????????????????
     *
     * @param fileName      ???????????????
     * @param savepath      ??????????????????
     * @param waterMarkName ????????????
     * @return ??????
     */
    public static int addFooterAndWater(String fileName, String savepath, String waterMarkName) {

        // ???????????????
        int num = 0;
        Document document = new Document();
        try {
            PdfReader reader = new PdfReader(fileName);
            BaseFont base = BaseFont.createFont("public/fonts/simfang.ttf",
                    "BaseFont.IDENTITY_H", BaseFont.NOT_EMBEDDED);
            num = reader.getNumberOfPages();
            PdfCopy copy = new PdfCopy(document, new FileOutputStream(savepath));
            document.open();
            // ?????????????????????
            PdfGState gs = new PdfGState();
            gs.setFillOpacity(FLOAT_4);
            gs.setStrokeOpacity(FLOAT_4);
            for (int i = 0; i < num; ) {
                PdfImportedPage page = copy.getImportedPage(reader, ++i);
                PdfCopy.PageStamp stamp = copy.createPageStamp(page);
                // ????????????
                PdfContentByte under = stamp.getUnderContent();
                under.beginText();
                under.setColorFill(Color.LIGHT_GRAY);
                // ??????????????????????????????????????????
                int fontSize = getFontSize(waterMarkName);
                under.setFontAndSize(base, fontSize);
                // ?????????????????????????????? ??????
                float pageWidth = reader.getPageSize(i).getWidth();
                float pageHeight = reader.getPageSize(i).getHeight();
                // ???????????????60????????????,?????????????????????
                under.showTextAligned(Element.ALIGN_CENTER, waterMarkName,
                        pageWidth / NUM_2, pageHeight / NUM_2, NUM_60);

                // ??????????????????
                under.endText();
                stamp.alterContents();
                copy.addPage(page);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return NEGATIVE_1;
        } finally {
            if (null != document) {
                document.close();
            }
        }
        System.out.println("pdf totalpages:" + num);
        return num;

    }

    /**
     * ????????????????????????????????????????????????
     *
     * @param waterMarkName ??????
     * @return ??????
     */
    private static int getFontSize(String waterMarkName) {
        int fontSize = NUM_8 * NUM_10;
        if (null != waterMarkName && !"".equals(waterMarkName)) {
            int length = waterMarkName.length();
            if (length <= NUM_26 && length >= NUM_9 * NUM_2) {
                fontSize = NUM_26;
            } else if (length < NUM_9 * NUM_2 && length >= NUM_8) {
                fontSize = NUM_40;
            } else if (length < NUM_8 && length >= NUM_1) {
                fontSize = NUM_8 * NUM_10;
            } else {
                fontSize = NUM_8 * NUM_2;
            }
        }
        return fontSize;
    }

}
