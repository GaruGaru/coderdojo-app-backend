package it.modularity

import io.javalin.Javalin
import it.modularity.api.EventsHandlerV1
import it.modularity.api.ProbeHandler
import it.modularity.configuration.DojoConfiguration
import it.modularity.confy.Confy

class CoderDojoBackend {

    private val server: Javalin = Javalin.create()

    fun run(configuration: DojoConfiguration) {

        this.server.get("/", ProbeHandler())
        this.server.get("/api/v1/events", EventsHandlerV1(configuration))

        this.server
                .port(configuration.port())
                .enableStandardRequestLogging()
                .start()

    }

}
