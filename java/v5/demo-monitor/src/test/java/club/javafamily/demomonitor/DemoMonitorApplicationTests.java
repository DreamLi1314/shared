package club.javafamily.demomonitor;

import club.javafamily.nf.request.FeiShuCardNotifyRequest;
import club.javafamily.nf.request.FeiShuTextNotifyRequest;
import club.javafamily.nf.service.FeiShuNotifyHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoMonitorApplicationTests {

    @Autowired
    private FeiShuNotifyHandler feiShuNotifyHandler;

    @Test
    void contextLoads() {
        feiShuNotifyHandler.notify(FeiShuTextNotifyRequest.of("报警了!!!"));
    }

    @Test
    void test01() {
        final String response = feiShuNotifyHandler.notify(FeiShuTextNotifyRequest.of("测试报警"));

        System.out.println(response);
    }

    @Test
    void testNotifyCard() {
        String dataTime = "2022-06-05 23:00:00";
        int shouldCount = 20, actualCount = 20;
        String status = actualCount < shouldCount ? "异常" : "正常";

        String content = "数据时次: " + dataTime
           + "\n应收收据个数: " + shouldCount
           + "\n实收数据个数: " + actualCount
           + "\n监控状态: **" + status + "**";

        final FeiShuCardNotifyRequest request
           = FeiShuCardNotifyRequest.of("测试xxx数据监控", content,
           "立即前往系统查看 :玫瑰:️ ✅ \uD83D\uDDA5️",
           "https://github.com/orgs/JavaFamilyClub/projects/3");

        final String response = feiShuNotifyHandler.notify(request);

    }
}
