package cn.lnfvc.ken.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/1/29 上午11:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPo {
    private String username;
    private String password;
}