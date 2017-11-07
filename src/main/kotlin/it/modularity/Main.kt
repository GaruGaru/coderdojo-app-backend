package it.modularity

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import it.modularity.events.DojoEventsProvider
import it.modularity.events.eventbrite.EventbriteProvider
import it.modularity.events.eventbrite.api.EventbriteService


fun main(args: Array<String>) {

    val port = 8000

    val vertx = Vertx.vertx()
    val httpServer = vertx.createHttpServer()
    val router = Router.router(vertx)

    val api = EventbriteService("https://eventbriteapi.com").api
    val provider: DojoEventsProvider = DojoEventsProvider(providers = listOf(EventbriteProvider("", api)))

    router.get("/").handler {
            val lat: Double = it.queryParam("lat")[0].toDouble()
            val lon: Double = it.queryParam("lat")[0].toDouble()
            val range: Double = it.queryParam("range")[0].toDouble()
            provider.provide("coderdojo", lat, lon, range).subscribe(
                    {},
                    {}
            )
    }

    httpServer.requestHandler { router.accept(it) }.listen(port) {
        if (it.succeeded()) println("Server listening at $port") else println(it.cause())
    }


}


