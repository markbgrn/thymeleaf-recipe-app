package com.champstart.recipeapp.util;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtil {
    public static final String PROFILE_PHOTOS_PATH = "src/main/resources/static/images/profile";
    public static final String RECIPE_PHOTOS_PATH = "src/main/resources/static/images/recipe/";

    public static boolean saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile)  {
        Path uploadPath = Paths.get(uploadDir);
        boolean result = false;

        try (InputStream inputStream = multipartFile.getInputStream()) {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            result = true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
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

    public static byte[] getFileFromFileSystem(String dirPath, String filename) {
        Path resolvedPath = Paths.get(dirPath);
        byte[] image = new byte[0];
        try {
            image = FileUtils.readFileToByteArray(new File(resolvedPath + "/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
