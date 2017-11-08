package it.modularity.api

import io.javalin.Javalin


object HelloWorld {
    @JvmStatic
    fun main(args: Array<String>) {
        val app = Javalin.create()
                .enableStandardRequestLogging()
                .port(8080)
                .start()

        app.get("/probe", ProbeHandler())

    }
}