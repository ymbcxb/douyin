package com.douyin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ymbcxb
 * @title
 * @Package com.douyin.utils
 * @date 2019/7/25 19:22
 */
public class FFMpegUtil {

    private static final Logger logger = LoggerFactory.getLogger(FFMpegUtil.class);

    private String ffmpegEXE = "E:/ffmpeg/bin/ffmpeg.exe";

    public boolean convertor(String videoInputPath,String mp3InputPath,String videoOutputPath){
        //ffmpeg.exe -i a.mp4 -i cccc.mp3 -map 0:v:0 -map 1:a:0 -t 6 -y output.mp4

        FileUtil.isFile(videoOutputPath);

        List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);
        command.add("-i");
        command.add(videoInputPath);
        command.add("-i");
        command.add(mp3InputPath);
        command.add("-map");
        command.add("0:v:0");
        command.add("-map");
        command.add("1:a:0");
        command.add("-t");
        command.add("6");
        command.add("-y");
        command.add(videoOutputPath);
        logger.info("开始合并");
        if(!process(command)){
            return false;
        }
        logger.info("合并成功");
        return true;
    }

    public boolean process(List<String> command){
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            Process process = null;
            process = builder.start();
            InputStream errorStream = process.getErrorStream();
            InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
            BufferedReader br = new BufferedReader(inputStreamReader);

            String line = "";
            while((line = br.readLine()) != null){}

            if(br != null){
                br.close();
            }
            if(inputStreamReader != null){
                inputStreamReader.close();
            }
            if(errorStream != null){
                errorStream.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean getCover(String videoInputPath, String coverOutputPath){
        FileUtil.isFile(coverOutputPath);
//		ffmpeg.exe -ss 00:00:01 -i spring.mp4 -vframes 1 bb.jpg
        List<String> command = new java.util.ArrayList<String>();
        command.add(ffmpegEXE);

        // 指定截取第1秒
        command.add("-ss");
        command.add("00:00:01");

        command.add("-y");
        command.add("-i");
        command.add(videoInputPath);

        command.add("-vframes");
        command.add("1");

        command.add(coverOutputPath);

        logger.info("开始截图");
        if(!process(command)){
            return false;
        }
        logger.info("截图成功");
        return true;
    }
}
