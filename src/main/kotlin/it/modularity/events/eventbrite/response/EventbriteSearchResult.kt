package it.modularity.events.eventbrite.response

data class EventbriteSearchResult(
        val location: Location?,
        var events: Array<Event>?,
        val pagination: Pagination?
)