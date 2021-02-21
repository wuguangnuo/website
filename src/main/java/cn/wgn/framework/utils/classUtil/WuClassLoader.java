package cn.wgn.framework.utils.classUtil;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 自定义 类加载器
 */
public class WuClassLoader extends ClassLoader {

    public Class<?> findClass(URI uri) {
        byte[] cLassBytes = null;
        Path path;
        try {
            path = Paths.get(uri);
            cLassBytes = Files.readAllBytes(path);

            cLassBytes = ClassEncryptUtil.decrypt(cLassBytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(null, cLassBytes, 0, cLassBytes.length);
    }
}