package top.solove.convert2pdf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.solove.convert2pdf.util.OfficeToHtml;

import java.io.File;

/**
 * @author TXC
 * date: 2021/3/11 10:52
 * @version 1.0
 */
@RestController
public class OfficeToHtmlController {
    @GetMapping("htmlString")
    public String demo() {
        String tempImg = OfficeToHtmlController.class.getClassLoader().getResource("").getPath()
                .substring(1);
        return OfficeToHtml.toHtmlString(new File("F:/四川/假日运行系统门禁闸机数据查看接口.doc"),
                "E:/test");
    }
}
