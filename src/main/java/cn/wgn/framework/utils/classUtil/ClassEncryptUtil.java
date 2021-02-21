package cn.wgn.framework.utils.classUtil;

import cn.wgn.framework.utils.EncryptUtil;

import java.util.Base64;

/**
 * 类加密工具
 */
public class ClassEncryptUtil {
    /**
     * 类加密工具 加密
     * <p>
     * 1.将待加密的比特串进行 {@link Base64.Encoder} 加密
     * <br />
     * 2.将上一步的结果使用 {@link EncryptUtil} 加密
     * <br />
     * 3.将结果再进行一次 {@link Base64.Encoder} 加密后返回
     * </p>
     *
     * @param str 待加密的比特串
     * @return 加密后的比特串
     */
    public static byte[] encrypt(byte[] str) {

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] bytes = encoder.encode(str);

        String str2 = EncryptUtil.encryptString(new String(bytes));

        return encoder.encode(str2.getBytes());
    }

    /**
     * 类加密工具 解密
     * <p>
     * 1.将待解密的比特串进行 {@link Base64.Decoder} 解码
     * <br />
     * 2.将上一步的结果使用 {@link EncryptUtil} 解密
     * <br />
     * 3.将结果再进行一次 {@link Base64.Decoder} 解码后返回
     * </p>
     *
     * @param str 待解密的比特串
     * @return 解密后的比特串
     */
    public static byte[] decrypt(byte[] str) {

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(str);

        String str2 = EncryptUtil.decryptString(new String(bytes));

        return decoder.decode(str2.getBytes());
    }
}
