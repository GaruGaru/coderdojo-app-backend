package it.modularity.events.eventbrite.response

data class Crop_mask(val height: String?, val width: String?, val top_left: Top_left?)

data class Description(val text: String, val html: String?)

data class End(val timezone: String, val utc: String, val  local: String)

data class Location(val longitude: String?, val  within: String?, val latitude: String?)

data class Name(val text: String, val html: String?)

class Original(val height: String?, val width: String?, url: String?)

class Start(val timezone: String, val utc: String, val local: String)

data class Top_left(val y: String?, val x: String?)

data class Pagination(
        val page_size: String,
        val object_count: String,
        val page_number: String ,
        val has_more_items: String,
        val page_count: String
)

data class Logo(
        val id: String,
        val original: Original?,
        val edge_color_set: String?,
        val crop_mask: Crop_mask?,
        val edge_color: String?,
        val url: String,
        val aspect_ratio: String?
)

