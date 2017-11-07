package it.modularity.events.common.model

data class DojoEvent(
        val title: String,
        val description: String,
        val logo: String?,
        val icon: String?,
        val ticketUrl: String,
        val startTime: Long,
        val endTime: Long,
        val capacity: Int?,
        val participants: Int?,
        val location: DojoLocation,
        val organizer: DojoOrganizer,
        val free: Boolean = true,
        val price: Float? = null
)