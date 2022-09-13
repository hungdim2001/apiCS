package com.example.apiCS.commons.Utils;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.apiCS.exceptions.InvalidFilenameException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public final class UploadFile {

    @Autowired
    private static AmazonS3 s3Client;  //TODO: fix null
    @Value("${amazonProperties.bucketName}")
    private static String bucketName;

    private static File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

    public static String uploadFile(MultipartFile file, String filename, String folder) {
        // check valid filename extension
        String filenameExtension = file.getOriginalFilename().
                substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        if (!BaseUtils.CheckFilenameExtension(filenameExtension)) {
            throw new InvalidFilenameException(HttpStatus.BAD_REQUEST, "filename must be jpg, jpeg, png");
        }

        File fileObj = convertMultiPartFileToFile(file);
        long startTime = System.currentTimeMillis();
        s3Client.putObject(new PutObjectRequest
                (bucketName, folder + "/" + filename + "." + filenameExtension, fileObj)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        long stopTime = System.currentTimeMillis();
        long time = stopTime - startTime;
        System.out.println("time excute upload: "+time);
        fileObj.delete();

        return "https://csgoapi.s3.ap-southeast-1.amazonaws.com/" + folder + "/" + filename.replace(" ", "+") + "." + filenameExtension;
    }

}

