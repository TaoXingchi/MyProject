package top.solove.convert2pdf.util;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author TXC
 * date: 2021/3/11 10:34
 * @version 1.0
 */
public class OfficeToHtml {
    public static void main(String[] args) {
        System.out
                .println(toHtmlString(new File("F:/四川/假日运行系统门禁闸机数据查看接口.doc"), "E:/test"));
    }

    /**
     * 将word文档转换成html文档
     *
     * @param docFile  需要转换的word文档
     * @param filepath 转换之后html的存放路径
     * @return 转换之后的html文件
     */
    public static File convert(File docFile, String filepath) {
        // 创建保存html的文件
        File htmlFile = new File(filepath + "/" + System.currentTimeMillis()
                + ".html");
        // 创建Openoffice连接
        OpenOfficeConnection con = new SocketOpenOfficeConnection(8100);
        try {
            // 连接
            con.connect();
        } catch (ConnectException e) {
            System.out.println("获取OpenOffice连接失败...");
            e.printStackTrace();
        }
        // 创建转换器
        DocumentConverter converter = new OpenOfficeDocumentConverter(con);
        // 转换文档问html
        converter.convert(docFile, htmlFile);
        // 关闭openoffice连接
        con.disconnect();
        return htmlFile;
    }

    /**
     * 将word转换成html文件，并且获取html文件代码。
     *
     * @param docFile   需要转换的文档
     * @param imagePath 文档中图片的保存位置
     * @return 转换成功的html代码
     */
    public static String toHtmlString(File docFile, String imagePath) {
        // 转换word文档
        File htmlFile = convert(docFile, imagePath);
        // 获取html文件流
        StringBuffer htmlSb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(htmlFile), "GB2312"));
            while (br.ready()) {
                htmlSb.append(br.readLine());
            }
            br.close();
            // 删除临时文件
            htmlFile.delete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // HTML文件字符串
        String htmlStr = htmlSb.toString();
        // 返回经过清洁的html文本
        return clearFormat(htmlStr, imagePath);
        //return htmlStr;
    }

    /**
     * 清除一些不需要的html标记
     *
     * @param htmlStr    带有复杂html标记的html语句
     * @param docImgPath 图片地址
     * @return 去除了不需要html标记的语句
     */
    protected static String clearFormat(String htmlStr, String docImgPath) {
        // 获取body内容的正则
        String bodyReg = "<BODY .*</BODY>";
        Pattern bodyPattern = Pattern.compile(bodyReg);
        Matcher bodyMatcher = bodyPattern.matcher(htmlStr);
        if (bodyMatcher.find()) {
            // 获取BODY内容，并转化BODY标签为DIV
            htmlStr = bodyMatcher.group().replaceFirst("<BODY", "<DIV")
                    .replaceAll("</BODY>", "</DIV>");
        }
        ArrayList<String> imgList = readAllFile(docImgPath);
        for (int i = 0; i < imgList.size(); i++) {
            try {
                File delFile = new File(docImgPath + "/" + imgList.get(i));
                String suffix = imgList.get(i).substring(imgList.get(i).lastIndexOf(".") + 1);
                InputStream imageInput = new FileInputStream(docImgPath + "/" + imgList.get(i));
                htmlStr = htmlStr.replaceAll(imgList.get(i),
                        "data:image/" + suffix + ";base64," + imageToBase64Str(imageInput));
                delFile.delete();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        // 调整图片地址
        /*htmlStr = htmlStr.replaceAll("<IMG SRC=\"", "<IMG SRC=localhost:8080/" + docImgPath
                + "/");*/
        // 把<P></P>转换成</div></div>保留样式
        htmlStr = htmlStr.replaceAll("(<P)([^>]*>.*?)(<\\/P>)",
                "<div$2</div>");
        // 把<P></P>转换成</div></div>并删除样式
        htmlStr = htmlStr.replaceAll("(<P)([^>]*)(>.*?)(<\\/P>)", "<p$3</p>");
        // 删除不需要的标签
        htmlStr = htmlStr
                .replaceAll(
                        "<[/]?(font|FONT|span|SPAN|xml|XML|del|DEL|ins|INS|meta|META|[ovwxpOVWXP]:\\w+)[^>]*?>",
                        "");
        // 删除不需要的属性
        htmlStr = htmlStr
                .replaceAll(
                        "<([^>]*)(?:lang|LANG|class|CLASS|style|STYLE|size|SIZE|face|FACE|[ovwxpOVWXP]:\\w+)=(?:'[^']*'|\"\"[^\"\"]*\"\"|[^>]+)([^>]*)>",
                        "<$1$2>");
        return htmlStr;
    }

    private static ArrayList<String> listName = new ArrayList<String>();

    private static ArrayList<String> readAllFile(String filepath) {
        File file = new File(filepath);
        if (!file.isDirectory()) {
            listName.add(file.getName());
        } else {
            file.isDirectory();
            System.out.println("文件");
            String[] fileList = file.list();
            for (int i = 0; i < fileList.length; i++) {
                File readFile = new File(filepath);
                if (!readFile.isDirectory()) {
                    listName.add(readFile.getName());
                } else {
                    readFile.isDirectory();
                    // 递归
                    readAllFile(filepath + "/" + fileList[i]);
                }
            }
        }
        return listName;
    }


    /**
     * 图片转为base64字符串
     *
     * @param inputStream 文件流
     * @return 结果
     */
    public static String imageToBase64Str(InputStream inputStream) {
        byte[] data = null;
        try {
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // base64编码
        return Base64.encodeBase64String(data);
    }

    /**
     * 通过url得到file
     *
     * @param fileUrl 文件url地址
     * @param downPath 文件暂存地址
     * @return 文件
     */
    public static File downUrlTxt( String fileUrl, String downPath) {
        File savePath = new File(downPath);
        if (!savePath.exists()) {
            savePath.mkdir();
        }
        String[] urlName = fileUrl.split("/");
        int len = urlName.length - 1;
        // 获取文件名
        String uName = urlName[len];
        try {
            // 创建新文件
            File file = new File(savePath + "/" + uName);
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream outputstream = new FileOutputStream(file);
            URL url = new URL(fileUrl);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            // 设置是否要从 URL 连接读取数据,默认为true
            uc.setDoInput(true);
            uc.connect();
            InputStream inputstream = uc.getInputStream();
            // 打印文件长度
            System.out.println("file size is:" + uc.getContentLength());
            byte[] buffer = new byte[4 * 1024];
            int byteRead = -1;
            while ((byteRead = (inputstream.read(buffer))) != -1) {
                outputstream.write(buffer, 0, byteRead);
            }
            outputstream.flush();
            inputstream.close();
            outputstream.close();
            return file;
        } catch (Exception e) {
            System.out.println("读取失败！");
            e.printStackTrace();
            return null;
        }
    }
}
