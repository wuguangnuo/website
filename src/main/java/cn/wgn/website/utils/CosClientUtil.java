package cn.wgn.website.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
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
}
