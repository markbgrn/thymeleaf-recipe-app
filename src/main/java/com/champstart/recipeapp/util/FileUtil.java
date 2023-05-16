package com.champstart.recipeapp.util;

import com.champstart.recipeapp.user.util.FileUploadUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileUtil {
    public static final String PROFILE_PHOTOS_PATH = "src/main/resources/static/images/profile";
    public static boolean saveFile(String uploadDir, String fileName, MultipartFile file) {
        boolean result = false;
        try {
            FileUploadUtil.saveFile(uploadDir, fileName, file);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean isMultipartFileEmpty(MultipartFile file) {
        return file.getOriginalFilename() == "";
    }

    public static String getMultipartFileExtention(MultipartFile file) {
        String extension = "";
        try {
            extension = file.getOriginalFilename().split("\\.")[1];
        } catch (Exception e) {
            e.printStackTrace();
        }

        return extension;
    }
}
