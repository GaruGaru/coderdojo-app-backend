package it.modularity.events.eventbrite.response


data class Address(
        val address_1: String,
        val address_2: String?,
        val city: String,
        val country: String,
        val region: String,
        val postal_code: String,
        val latitude: String?,
        val longitude: String?,
        val localized_address_display: String?,
        val localized_area_display: String?,
        val localized_multi_line_address_display: List<String>?
)

data class Venue(
        val address: Address,
        val resource_uri: String,
        val id: String,
        val name: String,
        val latitude: String,
        val longitude: String
)