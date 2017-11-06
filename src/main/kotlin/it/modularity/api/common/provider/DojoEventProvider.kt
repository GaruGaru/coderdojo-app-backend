package it.modularity.api.common.provider

import io.reactivex.Observable
import it.modularity.api.common.model.DojoEvent

abstract class DojoEventProvider {

    abstract fun provide(
            query: String,
            latitude: Double,
            longitude: Double,
            range: Double
    ): Observable<DojoEvent>


}
