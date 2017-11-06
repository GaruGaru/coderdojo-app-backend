package it.modularity.api.common.model

data class DojoOrganizer(
        val id: String,
        val name: String? = id,
        val platform: String
)