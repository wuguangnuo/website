package cn.wgn.framework.utils;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author WuGuangNuo
 * @date Created in 2020/3/4 10:08
 */
@Component
public class WordUtil {
    /**
     * Html 转 Word
     *
     * @param body
     * @return
     */
    public static String html2Word(String body) {
        //拼一个标准的HTML格式文档
        String content = "<!DOCTYPE html><html><body>" + body + "</body></html>";

        InputStream is = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        try {
            File localFile = File.createTempFile("temp", ".doc");
            OutputStream os = new FileOutputStream(localFile);
            inputStreamToWord(is, os);
            System.out.println("生成临时文件:" + localFile.getPath());
            return localFile.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 把is写入到对应的word输出流os中
     * 不考虑异常的捕获，直接抛出
     *
     * @param is InputStream
     * @param os OutputStream
     * @throws IOException
     */
    private static void inputStreamToWord(InputStream is, OutputStream os) throws IOException {
        POIFSFileSystem fs = new POIFSFileSystem();
        //对应于org.apache.poi.hdf.extractor.WordDocument
        fs.createDocument(is, "WordDocument");
        fs.writeFilesystem(os);
        os.close();
        is.close();
        return;
    }
}
