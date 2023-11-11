package com.example.tripKo._core.S3;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.util.SdkHttpUtils;
import org.apache.http.HttpHost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
public class S3Config {
    @Value("cloud.aws.credentials.accessKey")
    private String accessKey;

    @Value("cloud.aws.credentials.secretKey")
    private String secretKey;

    @Value("cloud.aws.s3.bucketName")
    private String bucketName;

    @Value("cloud.aws.region.static")
    private String region;

    @Value("${spring.devtools.remote.proxy.host}")
    private String proxyHost;

    @Value("${spring.devtools.remote.proxy.port}")
    private int proxyPort;

    /*
    @Bean
    public AmazonS3 s3Builder() {
        AWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(region).build();
    }
    */

    @Bean
    public AmazonS3 S3Builder() {
        // Create proxy settings
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        AWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);

        // Configure the client with proxy settings
        try {
            return AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
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
}
