package com.douyin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin.utils
 * @date 2019/7/25 13:52
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    //文件上传
    public static boolean upload(String filePath, MultipartFile multipartFile){
        File f = new File(filePath);
        isFile(filePath);
        try {
            multipartFile.transferTo(f);
        } catch (IOException e) {
            logger.error("上传文件失败",e.getMessage());
            return false;
        }
        logger.info("上传文件成功");
        return true;
    }

    //如果文件不存在则创建文件
    public static void isFile(String filePath){
        File f = new File(filePath);
        if(!f.exists()){
            File parent = new File(f.getParent());
            parent.mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //删除该目录下所有的文件,包括该目录
    public static void deleteAllFile(String path){
        File file = new File(path);
        deleteAll(file);
    }

    private static void deleteAll(File file){
        if (file.isFile() || file.list().length == 0) {
            file.delete();
        } else {
            for (File f : file.listFiles()) {
                deleteAll(f); // 递归删除每一个文件
            }
            file.delete(); // 删除文件夹
        }
    }
}
