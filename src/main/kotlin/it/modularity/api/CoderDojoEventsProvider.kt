package it.modularity.api

import io.reactivex.Observable
import it.modularity.api.common.model.DojoEvent
import it.modularity.api.common.provider.DojoEventProvider

class CoderDojoEventsProvider(private val providers: List<DojoEventProvider>) : DojoEventProvider() {

    override fun provide(query: String, latitude: Double, longitude: Double, range: Double): Observable<DojoEvent> {
        return Observable.merge(providers.map { it.provide(query, latitude, longitude, range) })
    }

}
