package it.modularity.api

import com.google.gson.Gson
import io.javalin.Context
import io.javalin.Handler
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import it.modularity.configuration.DojoConfiguration
import it.modularity.events.DojoEventsProvider
import it.modularity.events.eventbrite.EventbriteProvider
import it.modularity.events.eventbrite.api.EventbriteService

class EventsHandlerV1(val configuration: DojoConfiguration) : Handler {

    private val eventBriteProvider = EventbriteProvider(configuration.eventBriteToken(), EventbriteService().api)
    private val dojoEventsProvider = DojoEventsProvider(providers = listOf(eventBriteProvider))

    private val mandatoryValues = arrayOf("lat", "lon", "range")

    override fun handle(ctx: Context) {
        val query = "coderdojo"

        val hasParams = mandatoryValues.all { ctx.queryParamMap().containsKey(it) }

        if (!hasParams) {
            ctx.status(400).result("Missing parameter, required params: " + mandatoryValues.toString())
        } else {

            val lat = ctx.queryParam("lat")!!.toDouble()
            val lon = ctx.queryParam("lon")!!.toDouble()
            val range = ctx.queryParam("range")!!.toDouble()
            val events = dojoEventsProvider.provide(query, lat, lon, range).blockingGet()

            ctx.result(Gson().toJson(events))

        }
    }
}