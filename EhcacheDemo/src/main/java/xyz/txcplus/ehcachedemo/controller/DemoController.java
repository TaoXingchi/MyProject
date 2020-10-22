package xyz.txcplus.ehcachedemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.txcplus.ehcachedemo.sercive.DemoService;

/**
 * ClassName: DemoController
 * Description:
 * date: 2020/10/22 14:03
 *
 * @author TXC
 */
@RestController
@RequestMapping("/")
public class DemoController {
    @Autowired
    DemoService demoService;
    @GetMapping("code")
    public String sendCode(String telephone){
        return demoService.sendCode(telephone);
    }

    @GetMapping("getCode")
    public String getCode(String telephone){
        return demoService.getCode(telephone);
    }
}
