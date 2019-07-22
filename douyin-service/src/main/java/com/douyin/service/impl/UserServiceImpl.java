package com.douyin.service.impl;

import com.douyin.common.Const;
import com.douyin.common.ServerResponse;
import com.douyin.mapper.UsersMapper;
import com.douyin.pojo.Users;
import com.douyin.service.UserService;
import com.douyin.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.PAData;
import tk.mybatis.mapper.entity.Example;

import java.util.UUID;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin.service.impl
 * @date 2019/7/22 0:23
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public ServerResponse register(String username,String password){
        //如果用户已经存在,则返回该用户已经存在
        ServerResponse checkUser = checkUser(username, password);
        if(checkUser.success()){
            return ServerResponse.createBySuccessMessage("该用户已存在");
        }
        //3 保存用户,密码为MD5加密
        Users currentUser = new Users();
        currentUser.setPassword(MD5Util.MD5EncodeUtf8(password));
        currentUser.setUsername(username);
        currentUser.setId(UUID.randomUUID().toString());
        currentUser.setNickname(username);
        int resultCount = usersMapper.insertSelective(currentUser);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("注册失败");
        }
        currentUser.setPassword(null);
        return ServerResponse.createBySuccessMessage("注册成功",currentUser);
    }

    private ServerResponse checkUser(String username,String password){
        //1 判断用户名和密码是否为空
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return ServerResponse.createByErrorMessage("用户名和密码不为空");
        }
        //2 判断用户名是否存在
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username",username);
        Users user = usersMapper.selectOneByExample(userExample);
        if( user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByError();
    }

    @Override
    public ServerResponse login(String username,String password){
        //如果用户已存在则登陆
        ServerResponse checkUser = checkUser(username, password);
        if(checkUser.success()){
            Users currentUser = (Users) checkUser.getData();
            if(currentUser.getPassword().equals(MD5Util.MD5EncodeUtf8(password))) {
                return ServerResponse.createBySuccessMessage("登陆成功");
            }
       }
       return ServerResponse.createByErrorMessage("该用户名或者密码不正确");
    }

}
