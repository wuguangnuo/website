package cn.wgn.website.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * 文件工具类
 *
 * @author WuGuangNuo
 * @date Created in 2020/2/29 22:46
 */
@Component
public class FileUtil {
    @Value("${private-config.menu-json}")
    private String menuJson;

    /**
     * 获取菜单列表
     * {
     * "code":"所需权限",
     * "icon":"图标",
     * "url":"相对链接",
     * "name":"菜单名称"
     * }
     *
     * @return
     */
    @Cacheable(value = "MenuJson",key = "")
    public String getMenuJson() {
        return getFileString(menuJson);
    }

    /**
     * 读取文件为String
     *
     * @param pathname
     * @return
     */
    public String getFileString(String pathname) {
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
}
