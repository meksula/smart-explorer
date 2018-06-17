package com.smartexplorer.core.domain.files;

import com.smartexplorer.core.exception.FileDownloadException;
import com.smartexplorer.core.exception.FileUploadException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @Author
 * Karol Meksuła
 * 16-06-2018
 * */

@Service
public class DefaultFileExchange implements FileExchange {

    @Value("${upload.path}")
    public String PATH;

    @Override
    public String uploadPicture(MultipartFile multipartFile, String fileName) {
        try {
            byte[] bytes = multipartFile.getBytes();
            FileUtils.writeByteArrayToFile(new File(PATH + fileName), bytes);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new FileUploadException("Cannot upload file : " + multipartFile.getOriginalFilename());
        }

        return "File uploaded correctly.";
    }

    @Override
    public byte[] getPicture(String spotId) {
        try {
            InputStream stream = new FileInputStream(new File(PATH + spotId));
            return IOUtils.toByteArray(stream);
        } catch (IOException e) {
            throw new FileDownloadException("Cannot find file [" + spotId + "]");
        }

    }
}
