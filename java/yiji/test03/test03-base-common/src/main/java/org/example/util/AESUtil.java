package org.example.util;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Component
public class AESUtil {

    //使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同，也可以不同!
    private static String KEY;
    private static String IV;

    private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/NoPadding";

    /**
     * 因为静态变量不能直接从配置文件通过@value获取，所以采用此方法
     * @param key
     */
    @Value("${AES.KEY:aaDJL2d9DfhLZO0z}")
    public void AESkey(String key){
        KEY = key;
    }
    @Value("${AES.IV:412ADDSSFA342442}")
    public void AESIv(String iv){
        IV = iv;
    }

    /**
     * 加密方法 返回base64加密字符串
     * 和前端保持一致
     * @param data  要加密的数据
     * @param key 加密key
     * @param iv 加密iv
     * @return 加密的结果
     * @throws Exception
     */
    public static String encrypt(String data, String key, String iv) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);//"算法/模式/补码方式"NoPadding PKCS5Padding
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return new Base64().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密方法
     * @param data 要解密的数据
     * @param key  解密key
     * @param iv 解密iv
     * @return 解密的结果
     * @throws Exception
     */
    public static String decrypt(String data, String key, String iv) throws Exception {
        try {
//        byte[] encrypted1 = new Base64().decode(data);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(new Base64().decode(data));
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用默认的key和iv加密
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        return encrypt(data, KEY, IV);
    }

    /**
     * 使用默认的key和iv解密
     * @param data
     * @return
     * @throws Exception
     */
    public static String decrypt(String data) throws Exception {
        return decrypt(data, KEY, IV);
    }

    public static void main(String[] args) throws Exception {
        String setr = "\"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\"";
        String aaDJL2d9DfhLZO0z = decrypt(setr, "aaDJL2d9DfhLZO0z", "412ADDSSFA342442");
        System.out.println(aaDJL2d9DfhLZO0z);

    }




}
