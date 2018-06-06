package com.fh.dao;

import javax.annotation.Resource;

import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis Dao
 * 
 * @author FH Q313596790 修改时间：2016、5、2
 */
public abstract class AbstractBaseRedisDao<K, V> {

	@Resource(name = "redisTemplate")
	protected RedisTemplate<K, V> redisTemplate;

	@Resource(name = "jedisPool")
	protected JedisPool jedisPool;

	@SuppressWarnings("finally")
	public Jedis getJedis() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
		} catch (Exception e) {
			returnBrokenResource(jedis);
			e.printStackTrace();
		} finally {
			return jedis;
		}
	}

	@SuppressWarnings("deprecation")
	public void killJedis(Jedis jedis) {
		jedisPool.returnResource(jedis);
	}

	@SuppressWarnings("deprecation")
	public void returnBrokenResource(Jedis jedis) {
		jedisPool.returnBrokenResource(jedis);
	}

	/**
	 * 设置redisTemplate
	 * 
	 * @param redisTemplate
	 *            the redisTemplate to set
	 */
	public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 获取 RedisSerializer
	 */
	protected RedisSerializer<String> getRedisSerializer() {
		return redisTemplate.getStringSerializer();
	}

	/**
	 * 获取 RedisSerializer
	 */
	protected RedisSerializer<?> getHashKeySerializer() {
		return redisTemplate.getHashKeySerializer();
	}

	/**
	 * 获取 ValueOperations
	 */
	protected ValueOperations<K, V> opsForValue() {
		return redisTemplate.opsForValue();
	}

	/**
	 * 获取 ListOperations
	 */
	protected ListOperations<K, V> opsForList() {
		return redisTemplate.opsForList();
	}

	/**
	 * 获取 HashOperations
	 */
	protected HashOperations<K, K, V> opsForHash() {
		return redisTemplate.opsForHash();
	}

	/**
	 * 获取 SetOperations
	 */
	protected SetOperations<K, V> opsForSet() {
		return redisTemplate.opsForSet();
	}

	/**
	 * 获取 BoundSetOperations
	 */
	@SuppressWarnings("unchecked")
	protected <T> BoundSetOperations<K, V> boundSetOps(String Key) {
		return redisTemplate.boundSetOps((K) Key);
	}

	/**
	 * 获取 BoundSetOperations
	 */
	@SuppressWarnings("unchecked")
	protected <T> BoundZSetOperations<K, V> boundZSetOps(String Key) {
		return redisTemplate.boundZSetOps((K) Key);
	}

	/**
	 * 获取 SetOperations
	 */
	protected ZSetOperations<K, V> opsForZSet() {
		return redisTemplate.opsForZSet();
	}
}
