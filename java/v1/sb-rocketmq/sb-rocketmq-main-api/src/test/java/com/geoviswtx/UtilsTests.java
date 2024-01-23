package com.geoviswtx;

import com.geoviswtx.utils.SignatureUtils;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class UtilsTests {

   @Test
   void testInit() {
        long unixTimestamp = Instant.now().getEpochSecond();
        System.out.println(unixTimestamp);

        Map<String, String> header = new HashMap<>();
        header.put("x-tme-appid", "1047");
        header.put("x-tme-timestamp", String.valueOf(unixTimestamp));
        header.put("x-tme-signature-method", "HMAC-SHA256");
        header.put("x-tme-signature-version", "1.0");

        String appSecret = "T6pFfwU47CZk";

        String signature = SignatureUtils.generateSignature(header, appSecret);
        System.out.println(signature);
    }

    @Test
    void testRequest() {
        long unixTimestamp = Instant.now().getEpochSecond();
//        long unixTimestamp = 1705843677;

        System.out.println(unixTimestamp);

        Map<String, String> header = new HashMap<>();
        header.put("x-tme-appid", "1047");
        header.put("x-tme-timestamp", String.valueOf(unixTimestamp));
        header.put("x-tme-signature-method", "HMAC-SHA256");
        header.put("x-tme-signature-version", "1.0");
        header.put("x-tme-token", "mlive_JSR5czRTJSp6JSR5czRTJSdPJMbTJMBPc-b");

        String appSecret = "T6pFfwU47CZk";

        String signature = SignatureUtils.generateSignature(header, appSecret);

        System.out.println(signature);
    }

}
