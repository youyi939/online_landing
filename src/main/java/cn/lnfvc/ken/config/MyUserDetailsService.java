package cn.lnfvc.ken.config;

import cn.lnfvc.ken.dao.UserDao;
import cn.lnfvc.ken.pojo.UserPo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/1/29 上午11:25
 */

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPo userPo =userDao.loadUser(username);
//        return new User(userPo.getUsername(),new BCryptPasswordEncoder().encode(userPo.getPassword()),AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_vip2"));
        return userPo;
    }
}
