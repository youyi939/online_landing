package cn.lnfvc.ken.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author: KenChen
 * @Description: 登陆过程工具类
 * @Date: Create in  2021/2/28 下午6:25
 */
public class LandUtil {

    /**
     * 将明文密码加密
     * @param password
     * @return 加密后的密码
     */
    public static String getPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 检查用户名是否包含敏感字
     * @param username 用户名
     * @return true：有，false：无
     */
    public static boolean checkSensitiveWords(String username){
        return true;
    }

}
