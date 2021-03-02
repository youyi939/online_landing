package cn.lnfvc.ken.util;

import cn.lnfvc.ken.pojo.CommonResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    /**
     * 当认证出错时返回错误信息
     * @param code 状态码
     * @param message 错误信息
     * @return json
     * @throws JsonProcessingException
     */
    public static String createResponseBody(int code,String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(new CommonResult<String>(code,message));
    }

}
