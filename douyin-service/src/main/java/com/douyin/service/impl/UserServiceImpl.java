package com.douyin.service.impl;

import com.douyin.common.Const;
import com.douyin.common.ServerResponse;
import com.douyin.mapper.UsersFansMapper;
import com.douyin.mapper.UsersMapper;
import com.douyin.pojo.Users;
import com.douyin.pojo.UsersFans;
import com.douyin.service.UserService;
import com.douyin.utils.FileUtil;
import com.douyin.utils.IdWorker;
import com.douyin.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin.service.impl
 * @date 2019/7/22 0:23
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersFansMapper usersFansMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private RedisTemplate<String, Users> redisTemplate;
    @Value("${uploadPath}")
    private String uploadPath;

    @Override
    public ServerResponse register(String username, String password) {
        //如果用户已经存在,则返回该用户已经存在
        ServerResponse checkUser = checkUser(username, password);
        if (checkUser.success()) {
            return ServerResponse.createByErrorMessage("该用户已存在");
        }
        //3 保存用户,密码为MD5加密
        Users currentUser = new Users();
        currentUser.setPassword(MD5Util.MD5EncodeUtf8(password));
        currentUser.setUsername(username);
        currentUser.setId(String.valueOf(idWorker.nextId()));
        currentUser.setNickname(username);
        currentUser.setFansCounts(0);
        currentUser.setFollowCounts(0);
        currentUser.setReceiveLikeCounts(0);
        int resultCount = usersMapper.insertSelective(currentUser);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("注册失败");
        }
        currentUser.setPassword(null);
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    private ServerResponse checkUser(String username, String password) {
        //1 判断用户名和密码是否为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ServerResponse.createByErrorMessage("用户名和密码不为空");
        }
        //2 判断用户名是否存在
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);
        Users user = usersMapper.selectOneByExample(userExample);
        if (user != null) {
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByError();
    }

    @Override
    public ServerResponse login(String username, String password) {
        //如果用户已存在则登陆
        ServerResponse checkUser = checkUser(username, password);
        if (checkUser.success()) {
            Users currentUser = (Users) checkUser.getData();
            if (currentUser.getPassword().equals(MD5Util.MD5EncodeUtf8(password))) {
                currentUser.setPassword(null);
                redisTemplate.opsForValue().set(Const.USER_PREFIX + currentUser.getId(), currentUser);
                return ServerResponse.createBySuccessMessage("登陆成功", currentUser);
            }
        }
        return ServerResponse.createByErrorMessage("该用户名或者密码不正确");
    }

    @Override
    public ServerResponse logut(String userId) {
        Users user = redisTemplate.opsForValue().get(Const.USER_PREFIX + userId);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        redisTemplate.delete(Const.USER_PREFIX + userId);
        return ServerResponse.createBySuccessMessage("注销成功");
    }

    @Override
    public ServerResponse uploadFace(String userId, MultipartFile multipartFile) {
        if( userId == null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Boolean flag = redisTemplate.hasKey(Const.USER_PREFIX+userId);
        if(!flag){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        //上传文件
        String fileName = multipartFile.getOriginalFilename();
        String face_image = "/"+ userId + "/face/"+fileName;
        String filePath =  uploadPath + face_image;
        boolean upload = FileUtil.upload(filePath, multipartFile);
        if(!upload){
            return ServerResponse.createByErrorMessage("头像上传失败");
        }
        //保存进数据库
        Users u = new Users();
        u.setFaceImage(face_image);
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id",userId);
        usersMapper.updateByExampleSelective(u,userExample);
        //删除redis
        redisTemplate.delete(Const.USER_PREFIX+userId);
        return ServerResponse.createBySuccessMessage("头像上传成功",face_image);
    }

    @Override
    public ServerResponse getUserInfo(String userId){
        Users users = redisTemplate.opsForValue().get(Const.USER_PREFIX+userId);
        if (users == null){
            Users newUser = usersMapper.selectByPrimaryKey(userId);
            users = newUser;
            if(newUser == null){
                return ServerResponse.createByErrorMessage("用户未登录");
            }
            else{
                redisTemplate.opsForValue().set(Const.USER_PREFIX+userId,newUser);
            }
        }
        return ServerResponse.createBySuccess(users);
    }

    @Override
    public ServerResponse follow(String userId,String fanId){
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        UsersFans usersFans = new UsersFans();
        usersFans.setFanId(fanId);
        usersFans.setUserId(userId);
        int count = usersFansMapper.insert(usersFans);
        if(count <= 0){
            return ServerResponse.createByErrorMessage("关注失败");
        }
        return ServerResponse.createBySuccessMessage("关注成功");
    }
}
