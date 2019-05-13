package com.dreamli.client;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class MyHazelcastClient {
	public static void main(String[] args) {
		ClientConfig clientConfig = new ClientConfig();
		
		// 开启客户端统计, 如果不开启在 Hazelcast-Management Center 中无法检测到当前客户端的连接
		clientConfig.setProperty("hazelcast.client.statistics.enabled", "true");
		
		HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
		IMap map = client.getMap("customers");
		System.out.println("Map Size:" + map.size());
		System.out.println("Map get 1: "+ map.get(1));
		System.out.println("Map get 2: "+ map.get(2));
		System.out.println("Map get 3: "+ map.get(3));
		
		map.set(3, "Jack");
		
		System.out.println("Map get 3: "+ map.get(3));
	}
}
