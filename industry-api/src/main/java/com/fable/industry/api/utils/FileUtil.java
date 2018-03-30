package com.fable.industry.api.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *@author jiangmingjun
 *@date 2018/2/7
 *@description 文件上传工具
 */
public class FileUtil {

    /**
     *@author jiangmingjun
     *@date 2018/2/7
     *@description 文件上传
     */
    public static Map<String, Object> upload(MultipartFile file, HttpServletRequest request) {

        Map<String, Object> map = new HashMap<>();
        String fileName = "";
        String fileUrl = "";
        if (file.isEmpty()) {
            map.put("fileName", fileName);
            map.put("fileUrl", fileUrl);
            return map;
        }
        String path = request.getSession().getServletContext().getRealPath(
                "/")
                + File.separator + "upload" + File.separator;

        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        String uuid = UUID.randomUUID().toString();
        fileName = file.getOriginalFilename();
        fileUrl = uuid ;
        File tempFile = new File(path, fileUrl);
        try {
            file.transferTo(tempFile);
            map.put("image", fileName);
            map.put("imagePath", "upload/"+fileUrl);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("image", fileName);
            map.put("imagePath", "upload/"+fileUrl  );
            return map;
        }
    }
}
