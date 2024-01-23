package com.geoviswtx.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class SignatureUtils {
    public static String generateSignature(Map<String, String> header, String appSecret) {
        List<String> keyList = new ArrayList<>(header.keySet());
        Collections.sort(keyList);

        List<String> kvList = new ArrayList<>(keyList.size());
        for (String key : keyList) {
            kvList.add(key + ":" + header.get(key));
        }
        String headerStr = String.join("\n", kvList);

        Mac hmacSha256;
        try {
            hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(appSecret.getBytes(), "HmacSHA256");
            hmacSha256.init(secretKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }

        byte[] signatureBytes = hmacSha256.doFinal(headerStr.getBytes());

        return bytesToHex(signatureBytes);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

}
