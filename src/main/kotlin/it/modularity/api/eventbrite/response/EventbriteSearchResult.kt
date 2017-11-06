package it.modularity.api.eventbrite.response

data class EventbriteSearchResult(
        val location: Location?,
        var events: Array<Event>?,
        val pagination: Pagination?
)