package it.modularity.api.eventbrite.response


data class Event(
        val is_reserved_seating: String?,

        val online_event: String?,

        val locale: String?,

        val organizer_id: String,

        val is_locked: String?,

        val version: String?,

        val currency: String?,

        val changed: String?,

        val id: String?,

        val is_series_parent: String?,

        val created: String?,

        val description: Description,

        val name: Name,

        val venue_id: String,

        val capacity: String?,

        val end: End,

        val is_series: String?,

        val subcategory_id: String?,

        val listed: String?,

        val logo: Logo,

        val hide_end_date: String?,

        val shareable: String?,

        val status: String?,

        val category_id: String?,

        val capacity_is_custom: String?,

        val logo_id: String?,

        val url: String,

        val source: String?,

        val start: Start,

        val resource_uri: String?,

        val format_id: String?,

        val tx_time_limit: String?,

        val hide_start_date: String?,

        val is_free: Boolean,

        val privacy_setting: String?
)

			