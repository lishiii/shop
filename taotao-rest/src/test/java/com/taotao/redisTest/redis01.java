package com.taotao.redisTest;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.JedisCluster;

public class redis01 {

	@Test
	public void RedisTest01() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-jedis.xml");
		JedisCluster jedisCluster = (JedisCluster) ac.getBean("redisClient");
		jedisCluster.set("key1", "value1");
		String string = jedisCluster.get("key1");
		System.out.println(string);
		jedisCluster.close();
	}
}
