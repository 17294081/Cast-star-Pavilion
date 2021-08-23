package com.example.paper.util;

import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class FIleUpload {

    private static String ioPath= ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static";

    public static String upload(MultipartFile file,String path) {
        File folder  = new File(ioPath+File.separator+path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (file.isEmpty()) {
            return null;
        }

        String name = file.getOriginalFilename();
        name = name.substring(name.lastIndexOf("."));
        String fileName= LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)+"";
        File dest = new File(ioPath+File.separator+path +File.separator+ fileName+name);
        try {
            file.transferTo(dest);
            return fileName+name;
        } catch (IOException e) {
            return null;
        }
    }
    public static boolean delFile(String path){
        String filePath = ioPath+File.separator+path;
        File file = new File(filePath);
        try {
            boolean flag = file.delete(); // 删除文件
            if (flag) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
