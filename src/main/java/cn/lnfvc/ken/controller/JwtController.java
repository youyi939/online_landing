package cn.lnfvc.ken.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/2/18 下午2:14
 */
@RestController
public class JwtController {

    @GetMapping(value = "/api/guest")
    public String guest(){
        return "this is a guest api msg";
    }

    @GetMapping(value = "/api/admin")
    public String admin(){
        return "this is a admin api msg";
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
