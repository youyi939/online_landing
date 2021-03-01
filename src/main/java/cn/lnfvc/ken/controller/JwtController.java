package cn.lnfvc.ken.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/2/27 下午11:36
 */
@RestController
public class JwtController {

//    无需任何权限都可访问
    @GetMapping(value = "/api/guest")
    public String guest(){
        return "this is a guest api msg";
    }

//    有角色就能访问
    @GetMapping(value = "/api/admin")
    public String admin(){
        return "this is a admin api msg";
    }

//    vip1
    @GetMapping(value = "/vip")
    public String vip(){
        return "this is vip page";
    }

//    vip2
    @GetMapping(value = "/vip2")
    public String vip2(){
        return "this is vip2 page";
    }
}

