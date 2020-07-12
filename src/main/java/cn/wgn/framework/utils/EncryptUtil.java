package cn.wgn.framework.utils;

import com.google.common.base.Strings;
import com.google.common.io.BaseEncoding;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密
 *
 * @author WuGuangNuo
 */
public class EncryptUtil {
    /**
     * 用户账号密码加密
     *
     * @param str
     * @return
     */
    public static String encryptString(String str) {
        String str2 = getMD5Str("WgnKey");
        int num2 = 0;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            num2 = (num2 == str2.length()) ? 0 : num2;
            char c1 = str2.charAt(num2);
            String c1s = String.valueOf(c1);
            char c2 = str.charAt(i);
            String c2s = String.valueOf(c2);
            char c3 = str2.charAt(num2++);
            int t1 = c2 ^ c3;
            String c3s = String.valueOf((char) t1);
            builder.append(c1s).append(c3s);
        }
        String str1 = builder.toString();

        String result = encryptKey(builder.toString());
        BaseEncoding baseEncoding = BaseEncoding.base64();
        result = baseEncoding.encode(result.getBytes());
        return result;
    }

    /**
     * 用户账户密码解密
     *
     * @param str
     * @return
     */
    public static String decryptString(String str) {
        if (Strings.isNullOrEmpty(str)) {
            return "";
        }
        try {
            str = encryptKey(new String(new BASE64Decoder().decodeBuffer(str), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            builder.append(((char) (str.charAt(i) ^ str.charAt(++i))));
        }
        return builder.toString();
    }

    /**
     * 加密Key
     */
    private static String encryptKey(String str) {
        String str2 = getMD5Str("WgnKey");
        int num2 = 0;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            num2 = (num2 == str2.length()) ? 0 : num2;
            char c2 = str.charAt(i);
            char c3 = str2.charAt(num2++);
            int t1 = c2 ^ c3;

            String c3s = String.valueOf((char) t1);
            builder.append(c3s);
        }
        return builder.toString();
    }

    /**
     * MD5 加密
     *
     * @param str str
     * @return md5encode
     */
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (byte b : byteArray) {
            if (Integer.toHexString(0xFF & b).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & b));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & b));
            }
        }

        return md5StrBuff.toString();
    }
}
