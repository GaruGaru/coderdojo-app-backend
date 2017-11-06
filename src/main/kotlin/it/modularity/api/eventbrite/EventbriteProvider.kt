package it.modularity.api.eventbrite


import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import it.modularity.api.common.model.DojoEvent
import it.modularity.api.common.model.DojoLocation
import it.modularity.api.common.model.DojoOrganizer
import it.modularity.api.common.provider.DojoEventProvider
import it.modularity.api.eventbrite.api.EventbriteApi
import it.modularity.api.eventbrite.response.Event
import it.modularity.api.eventbrite.response.Venue
import java.time.Instant

class EventbriteProvider(private val token: String, private val api: EventbriteApi) : DojoEventProvider() {

    companion object {
        val PLATFORM: String = "EVENTBRITE"
        private val DEFAULT_UNIT: String = "KM"
    }

    override fun provide(query: String, latitude: Double, longitude: Double, range: Double): Observable<DojoEvent> {
        return api.search(token, query, latitude, longitude, range.toString().plus(DEFAULT_UNIT))
                .map { it.events?.toMutableList() }
                .flatMap { Observable.fromIterable(it) }
                .flatMap {
                    Observable.zip(Observable.just(it), api.venue(it.venue_id, token),
                            BiFunction<Event, Venue, DojoEvent> { t1, t2 -> createDojoEvent(t1, t2) })
                }
    }

    private fun createDojoEvent(event: Event, venue: Venue): DojoEvent {
        return DojoEvent(
                title = event.name.text,
                description = event.description.text,
                logo = event.logo.url,
                icon = null,
                ticketUrl = event.url,
                startTime = epoch(event.start.utc),
                endTime = epoch(event.end.utc),
                capacity = event.capacity?.toInt(),
                participants = null,
                location = venue.toLocation(),
                organizer = organizerFrom(event.organizer_id),
                free = event.is_free,
                price = null
        )
    }

    private fun organizerFrom(id: String): DojoOrganizer = DojoOrganizer(id = id, platform = PLATFORM)

    private fun epoch(formatted: String): Long = Instant.parse(formatted).toEpochMilli()

    private fun Venue.toLocation(): DojoLocation = DojoLocation(this.name, this.address)

}



