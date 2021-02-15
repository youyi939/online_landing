package cn.lnfvc.ken.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/1/29 上午11:09
 */
@RestController
public class LandController {

//    受保护资源
    @GetMapping(value = "/test")
    public String test(){
        return "hello";
    }

    @GetMapping(value = "/unauth")
    public String unauth(){
        return "unauth!!!";
    }

//    登陆成功后默认返回此接口
    @GetMapping(value = "/suc")
    public String suc(){
        return "land scu";
    }

    @GetMapping(value = "/vip")
    public String vip(){
        return "this is vip page";
    }

    @GetMapping(value = "/vip2")
    public String vip2(){
        return "this is vip2 page";
    }

}
