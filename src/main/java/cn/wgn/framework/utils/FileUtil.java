package cn.wgn.framework.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;

/**
 * 文件工具类
 *
 * @author WuGuangNuo
 * @date Created in 2020/2/29 22:46
 */
@Component
public class FileUtil {
    /**
     * 读取文件为String
     *
     * @param pathname
     * @return
     */
    public static String getFileString(String pathname) {
        String result = null;
        try {
            File file = new File(pathname);
            FileInputStream fin = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));

            StringBuilder message = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                message.append(line);
            }
            br.close();
            result = message.toString().replaceAll("\t", "").replaceAll(" ", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * base64 转 MultipartFile
     *
     * @param base64
     * @return
     */
    public static MultipartFile base64ToMultipart(String base64) {
        try {
            String[] baseStr = base64.split(",");

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = decoder.decodeBuffer(baseStr[1]);

            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }

            return new Base64DecodeMultipartFile(b, baseStr[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
