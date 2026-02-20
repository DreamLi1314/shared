package com.geoviswtx;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class BasicTests {

    @ParameterizedTest
    @ValueSource(strings = {
            "jdbc:sqlserver://192.168.2.104:1433;database=aa;",
            "jdbc:sqlserver://192.168.2.104:1433;database=aa",
            "jdbc:sqlserver://192.168.2.104\\aa:1433;p=1;database=aa;c=3",
            "jdbc:sqlserver://192.168.2.104\\aa:1433;p=1"
    })
    void sqlserver(String val) {
//        String pattern = "^jdbc:sqlserver://([^\\\\:]+)(?:\\\\([^:]+))?(?::(\\d+))(?:;((?:.+;)?)(?:database(?:Name)?=([^;]+)((?:;.+)?))?)?$";
//        String pattern = "^jdbc:sqlserver://([^\\\\:]+)(?:\\\\([^:]+))?(?::(\\d+))(?:;((?:.+;?)?)(?:database(?:Name)?=([^;]+)((?:;?.+)?))?)?$";
        String pattern = "jdbc:sqlserver://([^:]+):(\\\\d+).*?;database=([^;]+).*?";

        Pattern PATTERN = Pattern.compile(pattern);
        Matcher matcher = PATTERN.matcher(val);

        Assertions.assertTrue(matcher.matches());

        for (int i = 0; i < 6; i++) {
            log.info("第 {} 个值为：{}", i, matcher.group(i));
        }

        log.info("success");
    }

}
