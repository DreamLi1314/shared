package com.jack.monitor.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MyAppHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        // 自定义健康判断逻辑

//         return Health.up().build(); // 服务器正常

        // 返回服务器异常
        return Health.down().withDetail("stat", "服务器异常...").build();
    }
}
