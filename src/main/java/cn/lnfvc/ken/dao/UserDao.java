package cn.lnfvc.ken.dao;

import cn.lnfvc.ken.pojo.UserPo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/1/29 上午11:11
 */
@Mapper
public interface UserDao {
    UserPo loadUser(String username);

    int insert_user(String username,String password);

    int insert_auth(String username,String role);

    String find_by_name(String username);

}

