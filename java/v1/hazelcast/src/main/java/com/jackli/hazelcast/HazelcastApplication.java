package com.jackli.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;
import java.util.Queue;

@SpringBootApplication
public class HazelcastApplication {

    public static void main(String[] args) {
        Config cfg = new Config();

        // 配置 Hazelcast Management Center 的连接信息, 让 Management Center 监控当前 Member
        ManagementCenterConfig managementCenterConfig = new ManagementCenterConfig();
        managementCenterConfig.setEnabled(true); // 开启监控
        managementCenterConfig.setUrl("http://localhost:8080/hazelcast-mancenter/"); // 配置监控中心的地址
        cfg.setManagementCenterConfig(managementCenterConfig);

        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
        Map<Integer, String> mapCustomers = instance.getMap("customers");
        mapCustomers.put(1, "Joe");
        mapCustomers.put(2, "Ali");
        mapCustomers.put(3, "Avi");

        System.out.println("Customer with key 1: "+ mapCustomers.get(1));
        System.out.println("Map Size:" + mapCustomers.size());

        Queue<String> queueCustomers = instance.getQueue("customers");
        queueCustomers.offer("Tom");
        queueCustomers.offer("Mary");
        queueCustomers.offer("Jane");
        System.out.println("First customer: " + queueCustomers.poll());
        System.out.println("Second customer: "+ queueCustomers.peek());
        System.out.println("Queue size: " + queueCustomers.size());
    }

}
