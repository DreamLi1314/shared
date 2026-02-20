package com.geoviswtx.utils;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

public class SignatureUtils {
    public static String generateSignature(Map<String, String> signParamsMap, String appSecret) {
        // 去掉 value 为空的
        Map<String, Object> trimmedParamMap = signParamsMap.entrySet()
                .stream()
                .filter(item -> !Strings.isNullOrEmpty(item.getValue().toString()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        // 按照字母排序
        Map<String, Object> sortedParamMap = trimmedParamMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        // 组装成待签名字符串。(注，引用了guava工具)
        String paramStr = Joiner.on("&").withKeyValueSeparator("=").join(sortedParamMap.entrySet());
        String signStr = paramStr + appSecret;

        // 生成签名返回。(注，引用了commons-codec工具)
        return DigestUtils.md5Hex(signStr);
    }

}
