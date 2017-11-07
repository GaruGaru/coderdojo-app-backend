package it.modularity.events.common.provider

import io.reactivex.Observable
import it.modularity.events.common.model.DojoEvent

abstract class DojoEventProvider {

    abstract fun provide(
            query: String,
            latitude: Double,
            longitude: Double,
            range: Double
    ): Observable<DojoEvent>


}
