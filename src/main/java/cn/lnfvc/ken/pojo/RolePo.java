package cn.lnfvc.ken.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Author: KenChen
 * @Description: 权限类
 * @Date: Create in  2021/2/15 下午3:35
 */
@Data
@AllArgsConstructor
public class RolePo implements GrantedAuthority{

    private static final long serialVersionUID = 1L;

    private String username;
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
