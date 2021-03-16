package test;


import org.apache.commons.lang3.StringUtils;

/**
 * @author TXC
 * date: 2021/3/10 14:47
 * @version 1.0
 */
public class Demo {
    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        /*;
        PdfConverter wordConverter = new WordToPdfConverter();
        wordConverter.convert(new File("F:\\四川\\停车场数据获取接口.doc"),
                new File("F:\\四川\\停车场数据获取接口.pdf"));*/
        System.out.println("file.getName()".substring("file.getName()".lastIndexOf(".")));
        System.out.println(StringUtils.substring("ppp/hhh.word",
                "ppp/hhh.word".lastIndexOf("/") + 1,
                "ppp/hhh.word".lastIndexOf(".")).concat(".pdf"));
        System.out.println("jjjjj");
    }
}
