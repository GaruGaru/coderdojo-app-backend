package it.modularity.events

import io.reactivex.Observable
import io.reactivex.Single
import it.modularity.events.common.model.DojoEvent
import it.modularity.events.common.provider.DojoEventProvider

class DojoEventsProvider(private val providers: List<DojoEventProvider>) {
    fun provide(query: String, latitude: Double, longitude: Double, range: Double): Single<List<DojoEvent>> =
            Observable.merge(providers.map { it.provide(query, latitude, longitude, range) }).toList()
}
