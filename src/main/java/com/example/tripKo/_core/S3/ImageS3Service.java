package com.example.tripKo._core.S3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.SdkHttpUtils;
import com.example.tripKo._core.errors.ErrorCode;
import com.example.tripKo._core.errors.exception.BusinessException;
import com.example.tripKo._core.errors.exception.Exception400;
import com.example.tripKo._core.errors.exception.Exception404;
import com.example.tripKo._core.errors.exception.Exception500;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageS3Service{
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName; //버킷 이름

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${spring.devtools.remote.proxy.host}")
    private String proxyHost;

    @Value("${spring.devtools.remote.proxy.port}")
    private int proxyPort;

    @Value("cloud.aws.credentials.accessKey")
    private String accessKey;

    @Value("cloud.aws.credentials.secretKey")
    private String secretKey;

    private String changedImageName(String originName) { //이미지 이름 중복 방지를 위해 랜덤으로 생성
        String random = UUID.randomUUID().toString();
        return random+originName;
    }

    public AmazonS3 createAmazonS3ClientWithProxy() {
        // Create proxy settings
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        AWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);

        // Configure the client with proxy settings
        try {
            return AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(region, region))
                    .withPathStyleAccessEnabled(true)
                    .withClientConfiguration(getClientConfigurationWithProxy(proxy))
                    .build();
        } catch (SdkClientException e) {
            // Handle exception
            throw new RuntimeException("Error creating S3 client with proxy.", e);
        }
    }

    private com.amazonaws.ClientConfiguration getClientConfigurationWithProxy(Proxy proxy) {
        com.amazonaws.ClientConfiguration clientConfiguration = new com.amazonaws.ClientConfiguration();
        if (proxy.type() == Proxy.Type.HTTP) {
            InetSocketAddress address = (InetSocketAddress) proxy.address();
            HttpHost httpHost = new HttpHost(address.getHostName(), address.getPort());
            clientConfiguration.setProxyHost(httpHost.getHostName());
            clientConfiguration.setProxyPort(httpHost.getPort());
        }
        return clientConfiguration;
    }

    public String uploadImageToS3(MultipartFile image) { //이미지를 S3에 업로드하고 이미지의 url을 반환
        AmazonS3 s3clientWithProxy = createAmazonS3ClientWithProxy();

        String originName = image.getOriginalFilename(); //원본 이미지 이름
        String contentType = image.getContentType(); //확장자
        String fileExtension;

        //jpeg, jpg, png만 허용
        if (Objects.isNull(contentType)) {
            throw new BusinessException(contentType, "type", ErrorCode.IMAGE_TYPE_NOT_CORRECT);
        }
        else if (contentType.contains("image/jpeg") ||
                contentType.contains("image/jpg"))
            fileExtension = "jpg";
        else if (contentType.contains("image/png"))
            fileExtension = "png";
        else throw new BusinessException(contentType, "type", ErrorCode.IMAGE_TYPE_NOT_CORRECT);

        String changedName = changedImageName(originName); //새로 생성된 이미지 이름
        ObjectMetadata metadata = new ObjectMetadata(); //메타데이터
        metadata.setContentType("image/"+fileExtension);
        try {
            PutObjectResult putObjectResult = s3clientWithProxy.putObject(new PutObjectRequest(
                    bucketName, "images/" + changedName, image.getInputStream(), metadata
            ).withCannedAcl(CannedAccessControlList.PublicRead));

        } catch (IOException e) {
            throw new BusinessException(originName, "file name", ErrorCode.IMAGE_CANNOT_SAVE);
        }
        return s3clientWithProxy.getUrl(bucketName, "images/" + changedName).toString(); //데이터베이스에 저장할 이미지가 저장된 주소

    }


    public com.example.tripKo.domain.file.entity.File uploadImage(MultipartFile image){
        String originName = image.getOriginalFilename();
        String contentType = image.getContentType(); //확장자
        String storedImagePath = uploadImageToS3(image);

        com.example.tripKo.domain.file.entity.File fileEntity = com.example.tripKo.domain.file.entity.File.builder()
                    .type(contentType)
                    .name(originName)
                    .url(storedImagePath)
                    .build();
        return fileEntity;
    }

    public void deleteImageFromS3(String fileUrl) {
        if (!fileUrl.contains("tripko-be6.s3.ap-northeast-2.amazonaws.com")) {
            return;
        }
        try{
            String fileKey = fileUrl.substring(51); // 폴더/파일.확장자
            String key = URLDecoder.decode(fileKey, StandardCharsets.UTF_8);

            boolean isObjectExist = amazonS3.doesObjectExist(bucketName, key);

            if (isObjectExist) {
                try {
                    amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
                } catch (AmazonServiceException e) {
                    new BusinessException(key, "file name", ErrorCode.IMAGE_CANNOT_DELETE);
                }
            } else {
                new BusinessException(key, "file name", ErrorCode.IMAGE_CANNOT_FOUND_AND_DELETE);
            }

            System.out.println(String.format("[%s] deletion complete", key));

        } catch (Exception exception) {
            new BusinessException(fileUrl, "url", ErrorCode.IMAGE_CANNOT_FOUND);
        }
    }

}