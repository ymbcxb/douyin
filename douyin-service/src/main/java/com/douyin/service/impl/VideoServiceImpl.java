package com.douyin.service.impl;

import com.douyin.common.Const;
import com.douyin.common.ServerResponse;
import com.douyin.mapper.*;
import com.douyin.pojo.*;
import com.douyin.service.VideoService;
import com.douyin.utils.FFMpegUtil;
import com.douyin.utils.FileUtil;
import com.douyin.utils.IdWorker;
import com.douyin.vo.VideosVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin.service.impl
 * @date 2019/7/25 13:33
 */
@Service
public class VideoServiceImpl implements VideoService {

    private  static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);

    @Autowired
    private VideosMapper videosMapper;
    @Autowired
    private RedisTemplate<String, Users> redisTemplate;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private BgmMapper bgmMapper;
    @Autowired
    private SearchRecordsMapper searchRecordsMapper;
    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Value("${serverUrl}")
    private String serverUrl;

    @Value("${uploadPath}")
    private String uploadPath;

    @Override
    public ServerResponse uploadVideo(String bgmId,String userId, MultipartFile multipartFile, String videoDesc, Double videoSeconds,
                                      Integer videoWidth, Integer videoHeight){
        if( userId == null){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        Boolean flag = redisTemplate.hasKey(Const.USER_PREFIX+userId);
        if(!flag){
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        //上传到临时文件
        String fileName = multipartFile.getOriginalFilename();
        String videoTempName = "/videoTemp/"+fileName;
        String fileTempPath =  uploadPath + videoTempName;
        boolean upload = FileUtil.upload(fileTempPath, multipartFile);
        if(!upload){
            return ServerResponse.createByErrorMessage("视频上传失败");
        }
        String videoName = videoTempName;
        String videoOutputPath = "";
        //合并bgm
        FFMpegUtil ffMpegUtil = new FFMpegUtil();
        if(StringUtils.isBlank(bgmId)){
            //获取Bgm
            Bgm bgm = bgmMapper.selectByPrimaryKey(bgmId);
            String bgmPath = uploadPath+"/bgm"+bgm.getPath();
            String videoPath = uploadPath+videoTempName;
            videoName = "/"+userId+"/video/"+fileName;
            videoOutputPath = uploadPath+videoName;
            ffMpegUtil = new FFMpegUtil();
            boolean convertor = ffMpegUtil.convertor(videoPath, bgmPath, videoOutputPath);
            if(convertor){
                logger.info("合并视频成功");
            }
            FileUtil.deleteAllFile(uploadPath+"/videoTemp");
        }

        //存储封面
        String fileNamePrefix = fileName.split("\\.")[0];
        String coverName = "/"+userId+"/videoCover/"+fileNamePrefix+".jpg";
        String coverPath = uploadPath+coverName;
        boolean cover = ffMpegUtil.getCover(videoOutputPath, coverPath);
        if(!cover){
            return ServerResponse.createByErrorMessage("上传封面失败");
        }

        //存储进数据库
        Videos videos = storeVideo(userId, videoDesc, videoName, videoSeconds, videoWidth, videoHeight,coverName);
        return ServerResponse.createBySuccessMessage("视频上传成功",videos.getId());
    }

    private Videos storeVideo(String userId,String videoDesc,String videoName,Double videoSeconds,
                              Integer videoWidth,Integer videoHeight,String coverPath){
        Videos videos = new Videos();
        videos.setId(String.valueOf(idWorker.nextId()));
        videos.setUserId(userId);
        videos.setVideoDesc(videoDesc);
        videos.setVideoPath(videoName);
        videos.setVideoSeconds(videoSeconds.floatValue());
        videos.setVideoWidth(videoWidth);
        videos.setVideoHeight(videoHeight);
        videos.setCoverPath(coverPath);
        videos.setLikeCounts(Long.valueOf(0));
        videos.setStatus(1);
        videos.setCreateTime(new Date());
        videosMapper.insert(videos);
        return videos;
    }

    @Override
    public ServerResponse<PageInfo> videoList(Integer pageNum,Integer pageSize,String value){
        PageHelper.startPage(pageNum,pageSize);
        List<VideosVo> videoList = videosMapper.selectAllVideosByValue(value);
        PageInfo pageInfo = new PageInfo(videoList);
        return ServerResponse.createBySuccess(pageInfo);
    }


    @Override
    public ServerResponse search(String value){
        //1.记录搜索记录
        SearchRecords record = new SearchRecords();
        record.setId(String.valueOf(idWorker.nextId()));
        record.setContent(value);
        searchRecordsMapper.insert(record);
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse getHot(Integer num){
        List<String> hotSearchRecord = searchRecordsMapper.getHotSearchRecord(num);
        return ServerResponse.createBySuccess(hotSearchRecord);
    }

    @Override
    public ServerResponse likeVideoOrNot(String userId, String videoId, String videoCreaterId,Integer likeFlag){
        //添加UserLikeVideos表
        UsersLikeVideos record = new UsersLikeVideos();
        record.setId(String.valueOf(idWorker.nextId()));
        record.setUserId(userId);
        record.setVideoId(videoId);
        if(likeFlag == -1){
           usersLikeVideosMapper.deleteByUserIdAndVideoId(userId,videoId);
        }else{
            usersLikeVideosMapper.insert(record);
        }
        //修改视频表
        Videos videos = videosMapper.selectByPrimaryKey(videoId);
        videos.setLikeCounts(videos.getLikeCounts()+likeFlag);
        videosMapper.updateByPrimaryKeySelective(videos);
        //修改视频创建者的用户表
        Users users = usersMapper.selectByPrimaryKey(videoCreaterId);
        users.setReceiveLikeCounts(users.getReceiveLikeCounts()+likeFlag);
        usersMapper.updateByPrimaryKeySelective(users);
        if(redisTemplate.opsForValue().get(Const.USER_PREFIX+videoCreaterId) != null){
            redisTemplate.delete(Const.USER_PREFIX+videoCreaterId);
        }
        //删除redis
        redisTemplate.delete(Const.USER_PREFIX+videoCreaterId);
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse likeVideo(String userId,String videoId){
        Example example = new Example(UsersLikeVideos.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("videoId",videoId);
        List<UsersLikeVideos> one = usersLikeVideosMapper.selectByExample(example);
        if(one == null || CollectionUtils.isEmpty(one)){
            return ServerResponse.createByError(false);
        }
        return ServerResponse.createBySuccess(true);
    }

    @Override
    public ServerResponse videoList(String userId){
        Example example = new Example(Videos.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        List<Videos> videos = videosMapper.selectByExample(example);
        return ServerResponse.createBySuccess(videos);
    }

    @Override
    public ServerResponse videoLikeList(String userId){
        Example example = new Example(UsersLikeVideos.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        List<UsersLikeVideos> likeVideos = usersLikeVideosMapper.selectByExample(example);
        return ServerResponse.createBySuccess(likeVideos);
    }
}
