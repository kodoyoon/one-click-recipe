package org.springeel.oneclickrecipe.global.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class S3Provider {

    private final AmazonS3 amazonS3;
    private final String SEPARATOR = "/";
    @Value("${cloud.aws.s3.bucket.name}")
    public String bucket;

    private static ObjectMetadata setObjectMetadata(MultipartFile multipartFile) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        return metadata;
    }

    public String saveFile(MultipartFile multipartFile, String fileName) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        ObjectMetadata metadata = setObjectMetadata(multipartFile);
        amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    public String originalFileName(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (fileType.equals(".png") || fileType.equals(".jpeg")) {
            fileName = UUID.randomUUID() + fileType;
            return fileName;
        } else {
            throw new IllegalArgumentException("잘못된 파일 형식입니다");
        }
    }

    public void createFolder(String folderName) {
        if (!amazonS3.doesObjectExist(bucket, folderName)) {
            amazonS3.putObject(
                bucket,
                folderName + SEPARATOR,
                new ByteArrayInputStream(new byte[0]),
                new ObjectMetadata());
        }
    }
}
