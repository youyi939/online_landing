package cn.lnfvc.ken.controller;

import cn.lnfvc.ken.dao.UserDao;
import cn.lnfvc.ken.pojo.CommonResult;
import cn.lnfvc.ken.pojo.RolePo;
import cn.lnfvc.ken.pojo.UserPo;
import cn.lnfvc.ken.util.LandUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/1/29 上午11:09
 */

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/auth")
public class LandController {

    @Resource
    private UserDao userDao;

    /**
     * 注册接口，排空，检查用户名重复
     * @param name 用户名
     * @param password 密码（明文）
     * @return
     */
    @PostMapping("/regUser")
    public CommonResult<UserPo> registered(String name, String password){

        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(password)){
            return new CommonResult<>(401,"用户名密码不能为空！");
        }

        String msg = userDao.find_by_name(name);
        if (name.equals(msg)){
            return new CommonResult<>(402,"当前用户名已被注册");
        }

        List<RolePo> rolePoList= new ArrayList<>();
        rolePoList.add(new RolePo(name,"ROLE_ADMIN"));
        UserPo userPo = new UserPo(name, password,rolePoList);
        log.info("用户注册信息："+userPo);

        userDao.insert_user(userPo.getUsername(), LandUtil.getPassword(userPo.getPassword()));
        for (RolePo rolePo : rolePoList) {
            userDao.insert_auth(userPo.getUsername(), rolePo.getAuthority());
        }

        return new CommonResult<>(200,"注册成功",userPo);
    }


}
