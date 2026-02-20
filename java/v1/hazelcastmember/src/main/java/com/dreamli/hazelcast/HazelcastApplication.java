package com.dreamli.hazelcast;


import com.hazelcast.config.Config;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Member;

import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class HazelcastApplication {

    public static void main(String[] args) {
        Config cfg = new Config();

        cfg.setInstanceName("inetsoft-node1");

        // 配置 Hazelcast Management Center 的连接信息, 让 Management Center 监控当前 Member
        ManagementCenterConfig managementCenterConfig = new ManagementCenterConfig();
        managementCenterConfig.setEnabled(true); // 开启监控
        managementCenterConfig.setUrl("http://localhost:8080/hazelcast-mancenter/"); // 配置监控中心的地址
        cfg.setManagementCenterConfig(managementCenterConfig);

        Hazelcast.newHazelcastInstance(null);
        HazelcastInstance instance = Hazelcast.getHazelcastInstanceByName("inetsoft-node1");
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

        Set<Member> members = instance.getCluster().getMembers();
        members.forEach(m -> System.out.println(m.getAddress()));
    }

}
