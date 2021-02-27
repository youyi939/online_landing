package cn.lnfvc.ken.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/2/15 下午5:24
 */
public class Password {
    public static void main(String[] args) {
        System.out.println(findPassword("321"));
    }

    public static String findPassword(String msg){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(msg);
        return password;
    }
}
