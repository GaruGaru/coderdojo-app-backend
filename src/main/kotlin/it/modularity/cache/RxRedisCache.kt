package it.modularity.cache

import com.google.gson.Gson
import io.reactivex.Observable
import redis.clients.jedis.Jedis
import java.util.*
import java.util.concurrent.Callable

class RxRedisCache<V>(redisHost: String, redisPort: Int = 6379, classT: Class<V>) : JRedisCache<V>(redisHost, redisPort, classT) {

    fun getObservable(key: String): Observable<Optional<V>> = Observable.fromCallable({ Optional.ofNullable(get(key)) })

}