package it.modularity.cache

import io.reactivex.Observable


fun main(args: Array<String>) {

    val obs = Observable.fromCallable { null }

    obs.subscribe({ println(it) })


}