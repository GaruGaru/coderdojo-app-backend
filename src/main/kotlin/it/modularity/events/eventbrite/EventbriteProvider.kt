package it.modularity.events.eventbrite


import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import it.modularity.cache.Cache
import it.modularity.events.common.model.DojoEvent
import it.modularity.events.common.model.DojoLocation
import it.modularity.events.common.model.DojoOrganizer
import it.modularity.events.common.provider.DojoEventProvider
import it.modularity.events.eventbrite.api.EventbriteApi
import it.modularity.events.eventbrite.response.Event
import it.modularity.events.eventbrite.response.Venue
import java.time.Instant

class EventbriteProvider(private val token: String, private val api: EventbriteApi, private val cache: Cache<String, Venue>? = null) : DojoEventProvider() {

    companion object {
        val PLATFORM: String = "EVENTBRITE"
        private val DEFAULT_UNIT: String = "km"
    }

    override fun provide(query: String, latitude: Double, longitude: Double, range: Double): Observable<DojoEvent> {
        return api.search(token, query, latitude, longitude, range.toInt().toString().plus(DEFAULT_UNIT))
                .map { it.events?.toMutableList() }
                .flatMap { Observable.fromIterable(it) }
                .flatMap {
                    Observable.zip(Observable.just(it), getVenue(it.venue_id).subscribeOn(Schedulers.io()),
                            BiFunction<Event, Venue, DojoEvent> { t1, t2 -> createDojoEvent(t1, t2) })
                }
    }

    private fun createDojoEvent(event: Event, venue: Venue): DojoEvent {
        return DojoEvent(
                title = event.name.text,
                description = event.description.text,
                logo = event.logo?.url, icon = null,
                ticketUrl = event.url,
                startTime = epoch(event.start.utc), endTime = epoch(event.end.utc),
                capacity = event.capacity?.toInt(), participants = null,
                location = venue.toLocation(),
                organizer = organizerFrom(event.organizer_id),
                free = event.is_free
        )
    }

    private fun getVenue(id: String): Observable<Venue> {
        return api.venue(id, token)
    }

    private fun organizerFrom(id: String): DojoOrganizer = DojoOrganizer(id = id, platform = PLATFORM)

    private fun epoch(formatted: String): Long = Instant.parse(formatted).toEpochMilli()

    private fun Venue.toLocation(): DojoLocation = DojoLocation(
            address = this.address.address_1,
            city = this.address.city.orEmpty(),
            name = this.name.orEmpty(),
            country = this.address.country.orEmpty(),
            postalCode = this.address.postal_code,
            latitude = this.address.latitude?.toDouble(),
            longitude = this.address.longitude?.toDouble()
    )

}



