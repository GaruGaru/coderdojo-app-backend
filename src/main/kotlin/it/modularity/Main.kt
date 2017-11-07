package it.modularity

import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.rx.java.RxHelper
import it.modularity.events.DojoEventsProvider
import it.modularity.events.eventbrite.EventbriteProvider
import it.modularity.events.eventbrite.api.EventbriteService


fun main(args: Array<String>) {

    val port = 8000

    val gson = Gson()
    val vertx = Vertx.vertx()
    val httpServer = vertx.createHttpServer()
    val router = Router.router(vertx)

    val api = EventbriteService("https://www.eventbriteapi.com").api
    val provider = DojoEventsProvider(providers = listOf(EventbriteProvider("test-token", api)))

    router.get("/api").handler { request ->
        val lat: Double = request.queryParam("lat")[0].toDouble()
        val lon: Double = request.queryParam("lon")[0].toDouble()
        val range: Double = request.queryParam("range")[0].toDouble()
        provider.provide("coderdojo", lat, lon, range)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { request.response().end(it.toString()) },
                        { request.response().setStatusCode(500).end(it.message) })
    }

    httpServer.requestHandler { router.accept(it) }.listen(port) {
        if (it.succeeded()) println("Server listening at $port") else println(it.cause())
    }


}


