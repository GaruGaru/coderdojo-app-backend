package it.modularity.events.common.model

data class DojoLocation(
        val address: String,
        val city: String?,
        val country: String?,
        val name: String? = null,
        val postalCode: String? = null,
        val latitude: Double? = null,
        val longitude: Double? = null
)
