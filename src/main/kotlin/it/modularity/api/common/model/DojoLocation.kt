package it.modularity.api.common.model

data class DojoLocation(
        val address: String,
        val name: String? = address
)
