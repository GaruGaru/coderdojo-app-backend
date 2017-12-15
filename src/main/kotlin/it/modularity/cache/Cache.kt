package it.modularity.cache

interface Cache<in K, T> {

    fun put(key: K, value: T)

    fun get(key: K): T?

    fun isPresent(key: K): Boolean

    fun clear()

}