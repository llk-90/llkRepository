package com.fh.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import com.fh.util.PageData;

import redis.clients.jedis.Jedis;

@Repository("redisDaoImpl")
public class RedisDaoImpl extends AbstractBaseRedisDao<String, String> implements RedisDao {

	/**
	 * 新增(存储字符串)
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public boolean addString(final String key, final String value) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] jkey = serializer.serialize(key);
				byte[] jvalue = serializer.serialize(value);
				return connection.setNX(jkey, jvalue);
			}
		});
		return result;
	}

	/**
	 * 新增(拼接字符串)
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public boolean appendString(final String key, final String value) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] jkey = serializer.serialize(key);
				byte[] jvalue = serializer.serialize(value);
				if (connection.exists(jkey)) {
					connection.append(jkey, jvalue);
					return true;
				} else {
					return false;
				}
			}
		});
		return result;
	}

	/**
	 * 通过key获取String (non-Javadoc)
	 * 
	 * @see com.sensor.dao.redis.RedisDao#get(java.lang.String)
	 */
	@Override
	public String getString(final String keyId) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] jkey = serializer.serialize(keyId);
				byte[] jvalue = connection.get(jkey);
				if (jvalue == null) {
					return null;
				}
				return serializer.deserialize(jvalue);
			}
		});
		return result;
	}

	/**
	 * 新增(存储Map)
	 * 
	 * @param key
	 * @param map
	 * @return
	 */
	@Override
	public <T> Object addMap(String key, Map<String, T> map) {

		Object result = redisTemplate.execute(new RedisCallback<Boolean>() {
			@SuppressWarnings("unchecked")
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				HashOperations hashOperations = opsForHash();
				if (null != map) {
					for (Map.Entry<String, T> entry : map.entrySet()) {
						hashOperations.put(key, entry.getKey(), entry.getValue());
					}
				}
				return null;
			}
		});
		return result;
	}

	/**
	 * 获取map
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public Map<String, PageData> getMap(String key) {
		return null;
	}

	/**
	 * 新增(存储List)
	 * 
	 * @param key
	 * @param list
	 * @return
	 */
	@Override
	public <T> Object addList(String key, List<T> list) {

		Object result = redisTemplate.execute(new RedisCallback<Boolean>() {
			@SuppressWarnings("unchecked")
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				ListOperations listOperation = opsForList();
				if (null != list) {
					int size = list.size();
					for (int i = 0; i < size; i++) {
						listOperation.rightPush(key, list.get(i));
					}
				}
				return null;
			}
		});
		return result;
	}

	/**
	 * 获取List
	 * 
	 * @param key
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String key) {

		List<T> result = new ArrayList<T>();
		ListOperations listOperation = opsForList();
		Long size = listOperation.size(key);
		result.add((T) listOperation.range(key, 0, size));
		return result;
	}

	/**
	 * 新增(存储set)
	 * 
	 * @param key
	 * @param set
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> Object addSet(String key, Set<T> set) {
		BoundSetOperations setOperation = boundSetOps(key);
		Iterator<T> it = set.iterator();
		while (it.hasNext()) {
			setOperation.add(it.next());
		}
		return setOperation;
	}

	/**
	 * 获取Set
	 * 
	 * @param key
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> Set<T> getSet(String key) {

		Set<T> result = new HashSet<T>();
		SetOperations setOperation = opsForSet();
		result = setOperation.members(key);
		return result;
	}

	/**
	 * 新增(存储Zset)
	 * 
	 * @param key
	 * @param set
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> Object addZset(String key, Set<T> set) {
		BoundZSetOperations zsetOperation = boundZSetOps(key);
		Iterator<T> it = set.iterator();
		while (it.hasNext()) {
			zsetOperation.add((Set) it.next());
		}
		return zsetOperation;
	}

	/**
	 * 获取ZSet
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> Set<T> getZset(String key) {

		Set<T> result = new HashSet<T>();
		BoundZSetOperations operation = boundZSetOps(key);
		Long size = operation.size();
		for (int i = 0; i < size; i++) {
			result.addAll(operation.range(0, size));
		}
		return result;

	}

	/**
	 * 删除 (non-Javadoc)
	 * 
	 * @see com.sensor.dao.redis.RedisDao#delete(java.lang.String)
	 */
	@Override
	public boolean delete(final String key) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] jkey = serializer.serialize(key);
				if (connection.exists(jkey)) {
					connection.del(jkey);
					return true;
				} else {
					return false;
				}
			}
		});
		return result;
	}

	/**
	 * 删除多个 (non-Javadoc)
	 * 
	 * @see com.sensor.dao.redis.RedisDao#delete(java.util.List)
	 */
	@Override
	public void delete(List<String> keys) {
		redisTemplate.delete(keys);
	}

	/**
	 * 修改 (non-Javadoc)
	 * 
	 * @see com.sensor.dao.redis.RedisDao#eidt(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean edit(String key, String value) {
		if (delete(key)) {
			addString(key, value);
			return true;
		}
		return false;
	}

	/**
	 * 更新 (non-Javadoc)
	 * 
	 * @see com.sensor.dao.redis.RedisDao#eidt(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean update(final String key, final String value) {
		if (getString(key) == null) {
			throw new NullPointerException("数据行不存在, key = " + key);
		}
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] jkey = serializer.serialize(key);
				byte[] jname = serializer.serialize(value);
				connection.set(jkey, jname);
				return true;
			}
		});
		return result;
	}

	/**
	 * 清空redis 所有数据
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String flushDB() {
		return redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return "ok";
			}
		});
	}

	/**
	 * 查看redis里有多少数据
	 */
	@SuppressWarnings("unchecked")
	public long dbSize() {
		return redisTemplate.execute(new RedisCallback<Long>() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.dbSize();
			}
		});
	}

	@Override
	public List<String> getTypicalData(String str, String pattern) {
		Jedis jedis = getJedis();
		Set<String> keys = null;
		keys = jedis.keys(str + pattern);
		if (keys.isEmpty()) {
			return Collections.emptyList();
		}
		List<String> list = jedis.mget(keys.toArray(new String[keys.size()]));
		killJedis(jedis);
		return list;
	}
}
