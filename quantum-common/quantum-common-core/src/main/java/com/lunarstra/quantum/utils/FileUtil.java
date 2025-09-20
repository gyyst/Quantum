package com.lunarstra.quantum.utils;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传工具类
 */
public class FileUtil {

    public static long IMAGE_FILE_SIZE = 1024 * 1024 * 2;

    // 图片允许的后缀扩展名
    public static String[] IMAGE_FILE_EXTD = new String[] {"jpg", "jpeg", "bmp", "gif", "png"};

    //获取文件名后缀
    public static String getFileSuffix(String filename) {

        String[] split = StrUtil.split(filename, "\\.").toArray(new String[0]);
        return split[split.length - 1];
    }

    public static boolean isFileAllowed(String fileName) {
        String fileSuffix = getFileSuffix(fileName);
        for (String ext : IMAGE_FILE_EXTD) {
            if (ext.equals(fileSuffix)) {
                return true;
            }
        }
        return false;
    }

    //判断文件大小不超过2M
    public static boolean isFileSizeOK(MultipartFile multipartFile) {
        long size = multipartFile.getSize();
        return size <= IMAGE_FILE_SIZE;
    }

}
