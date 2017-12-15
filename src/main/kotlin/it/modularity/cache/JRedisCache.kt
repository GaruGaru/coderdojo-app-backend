package it.modularity.cache

import com.google.gson.Gson
import redis.clients.jedis.Jedis

open class JRedisCache<V>(redisHost: String, redisPort: Int = 6379, private val classT: Class<V>, val gson: Gson = Gson()) : Cache<String, V> {

    private val client: Jedis = Jedis(redisHost, redisPort)

    override fun put(key: String, value: V) {
        this.client.set(key, value.toJson())
    }

    override fun get(key: String): V? {
        val value: String? = this.client.get(key)
        return value?.fromJson(classT)
    }

    override fun isPresent(key: String): Boolean = get(key) != null

    override fun clear() {
    }

    private fun <T> String.fromJson(cls: Class<T>): T? = gson.fromJson(this, cls)

    private fun V?.toJson() = gson.toJson(this)

}