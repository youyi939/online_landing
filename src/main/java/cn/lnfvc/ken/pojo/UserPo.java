package cn.lnfvc.ken.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/1/29 上午11:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPo implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private List<RolePo> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //注此处需为true，否则显示用户被锁定
    @Override
    public boolean isEnabled() {
        return true;
    }
}