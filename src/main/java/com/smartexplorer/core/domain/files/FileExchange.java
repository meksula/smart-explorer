package com.smartexplorer.core.domain.files;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author
 * Karol Meksuła
 * 16-06-2018
 * */

public interface FileExchange {
    String uploadPicture(MultipartFile multipartFile, String fileName);

    byte[] getPicture(String spotId);
}
