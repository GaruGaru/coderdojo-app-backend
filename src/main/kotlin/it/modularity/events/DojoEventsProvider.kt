package it.modularity.events

import io.reactivex.Observable
import it.modularity.events.common.model.DojoEvent
import it.modularity.events.common.provider.DojoEventProvider

class DojoEventsProvider(private val providers: List<DojoEventProvider>) : DojoEventProvider() {
    override fun provide(query: String, latitude: Double, longitude: Double, range: Double): Observable<DojoEvent> =
            Observable.merge(providers.map { it.provide(query, latitude, longitude, range) })
}
