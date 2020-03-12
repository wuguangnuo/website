package cn.wgn.website.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author WuGuangNuo
 * @date Created in 2020/2/27 16:45
 */
@Component
public class CosClientUtil {
    @Value("${private-config.cos.secret-id}")
    private String secretId;
    @Value("${private-config.cos.secret-key}")
    private String secretKey;
    @Value("${private-config.cos.bucket-name}")
    private String bucketName;
    @Value("${private-config.cos.region-name}")
    private String regionName;

    /**
     * 上传文件到COS
     *
     * @param file Multipart File
     * @param path Path in COS
     * @return URI on WEB
     */
    public String uploadFile2Cos(MultipartFile file, String path) {
        COSClient cosClient = getClient();
        path = addTm(path);

        String oldFileName = file.getOriginalFilename();
        String suffix = "";
        if (oldFileName != null && oldFileName.contains(".")) {
            suffix = oldFileName.substring(oldFileName.lastIndexOf("."));
        }
        String newFileName = UUID.randomUUID() + suffix;

        File localFile;
        try {
            localFile = File.createTempFile("temp", null);
            file.transferTo(localFile);
            // 指定要上传到 COS 上的路径
            String key = path + newFileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
            cosClient.putObject(putObjectRequest);

            return "https://" + bucketName + ".cos." + regionName + ".myqcloud.com" + path + newFileName;
        } catch (IOException e) {
            return "上传失败";
        } finally {
            cosClient.shutdown();
        }
    }

    /**
     * 上传文件到COS
     *
     * @param file File
     * @param path Path in COS
     * @return URI on WEB
     */
    public String uploadFile2Cos(File file, String path) {
        COSClient cosClient = getClient();
        path = addTm(path);

        String oldFileName = file.getName();
        String suffix = "";
        if (oldFileName.contains(".")) {
            suffix = oldFileName.substring(oldFileName.lastIndexOf("."));
        }
        String newFileName = UUID.randomUUID() + suffix;

        // 指定要上传到 COS 上的路径
        String key = path + newFileName;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        cosClient.putObject(putObjectRequest);

        cosClient.shutdown();
        return "https://" + bucketName + ".cos." + regionName + ".myqcloud.com" + path + newFileName;
    }

    /**
     * 创建 Client
     *
     * @return
     */
    private COSClient getClient() {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(regionName);
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }

    /**
     * 路径添加时间
     *
     * @param path
     * @return
     */
    private String addTm(String path) {
        Calendar rightNow = Calendar.getInstance();
        int year = rightNow.get(Calendar.YEAR);
        int month = rightNow.get(Calendar.MONTH);
        String tm = year + (month < 9 ? "0" : "") + (month + 1);
        return "/website/" + path + "/" + tm + "/";
    }

    /**
     * 搜索文件列表
     *
     * @return
     */
    public ObjectListing listObj() {
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        // 设置bucket名称
        listObjectsRequest.setBucketName(bucketName);
        // prefix表示列出的object的key以prefix开始
        listObjectsRequest.setPrefix("");
        // deliter表示分隔符, 设置为/表示列出当前目录下的object, 设置为空表示列出所有的object
        listObjectsRequest.setDelimiter("");
        // 设置最大遍历出多少个对象, 一次listobject最大支持1000
        listObjectsRequest.setMaxKeys(1000);
        ObjectListing objectListing = null;
        do {
            try {
                objectListing = getClient().listObjects(listObjectsRequest);
            } catch (CosServiceException e) {
                e.printStackTrace();
                return null;
            } catch (CosClientException e) {
                e.printStackTrace();
                return null;
            }
            // common prefix表示表示被delimiter截断的路径, 如delimter设置为/, common prefix则表示所有子目录的路径
            List<String> commonPrefixs = objectListing.getCommonPrefixes();

            // object summary表示所有列出的object列表
            List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
            for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
                // 文件的路径key
                String key = cosObjectSummary.getKey();
                // 文件的etag
                String etag = cosObjectSummary.getETag();
                // 文件的长度
                long fileSize = cosObjectSummary.getSize();
                // 文件的存储类型
                String storageClasses = cosObjectSummary.getStorageClass();
            }

            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);
        } while (objectListing.isTruncated());
        return objectListing;
    }

    /**
     * 判断文件是否存在
     *
     * @param key
     * @return
     */
    public boolean isExis(String key) {
        try {
            getClient().getObjectMetadata(bucketName, key);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * 获取临时链接
     *
     * @param key
     * @return
     */
    public String getTempUrl(String key) {
        GeneratePresignedUrlRequest req =
                new GeneratePresignedUrlRequest(bucketName, key, HttpMethodName.GET);
        // 设置签名过期时间(可选)
        Date expirationDate = new Date(System.currentTimeMillis() + 10L * 60L * 1000L);
        req.setExpiration(expirationDate);
        URL url = getClient().generatePresignedUrl(req);
        return url.toString();
    }
}
