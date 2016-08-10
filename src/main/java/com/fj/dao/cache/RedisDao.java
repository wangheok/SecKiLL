package com.fj.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.fj.entity.SecKill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by wanghe on 10/08/16.
 */
public class RedisDao {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private JedisPool jedisPool; // Similar to dataSource connection pool

    public RedisDao(String ip, int port) {

        jedisPool = new JedisPool(ip, port);
    }

    private RuntimeSchema<SecKill> secKillSchema = RuntimeSchema.createFrom(SecKill.class);

    public SecKill getSecKill(long secKillId) {

        // Redis execution logic
        try {

            Jedis jedis = jedisPool.getResource();

            try {
                String key = "secKill:" + secKillId;
                // No inner Serialization operation
                // get -> byte[] -> UnSerializalize -> Object
                // Use customized Serialization
                // The object serialized by protostuff need to be POJO object (has getter and setter)
                byte[] bytes = jedis.get(key.getBytes());
                // Exist in cache
                if (bytes != null) {
                    // Empty object
                    SecKill secKill = secKillSchema.newMessage();

                    ProtobufIOUtil.mergeFrom(bytes, secKill, secKillSchema);
                    // SecKill is unserialized
                    return secKill;
                }
            } finally {
                jedis.close();
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * Put SecKill when No cache found
     *
     * @return
     */
    public String putSecKill(SecKill secKill) {

        // set Object(SecKill) -> Serialization -> byte[]
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "secKill:" + secKill.getSecKillId();
                // LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE): Cache machine size is default
                byte[] bytes = ProtobufIOUtil.toByteArray(secKill, secKillSchema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                // overtime cache
                int timeout = 60 * 60;// 1 hour
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
