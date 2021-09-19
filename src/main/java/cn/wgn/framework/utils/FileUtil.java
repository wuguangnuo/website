package cn.wgn.framework.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
    public static String getFileString(String pathname, String split) {
        String result = null;
        try {
            File file = new File(pathname);
            FileInputStream fin = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));

            StringBuilder message = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                message.append(line);
                message.append(split);
            }
            result = message.toString();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.substring(0, result.length() - split.length());
    }

    /**
     * base64 转 MultipartFile
     *
     * @param base64
     * @return
     */
    public static MultipartFile base64ToMultipart(String base64) {
        String[] baseStr = base64.split(",");
        byte[] b = Base64Util.decode(baseStr[1]);

        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }

        return new Base64DecodeMultipartFile(b, baseStr[0]);
    }
}

class Base64DecodeMultipartFile implements MultipartFile {

    private final byte[] imgContent;
    private final String header;

    public Base64DecodeMultipartFile(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header.split(";")[0];
    }

    @Override
    public String getName() {
        return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
    }

    @Override
    public String getOriginalFilename() {
        return System.currentTimeMillis() + (int) (Math.random() * 10000) + "." + header.split("/")[1];
    }

    @Override
    public String getContentType() {
        return header.split(":")[1];
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imgContent);
    }
}