package it.modularity

import it.modularity.configuration.DojoConfiguration
import it.modularity.confy.Confy


object Main {
    @JvmStatic
    fun main(args: Array<String>) {

        val server = CoderDojoBackend()

        val configuration = Confy.implement(DojoConfiguration::class.java)

        server.run(configuration)

    }
}