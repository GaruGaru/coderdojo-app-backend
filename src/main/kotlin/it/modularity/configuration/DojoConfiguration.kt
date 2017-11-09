package it.modularity.configuration

import it.modularity.confy.annotations.Param

interface DojoConfiguration {

    @Param.Integer(defaultValue = 8080)
    fun port(): Int

    @Param.String(key = "event.brite.token")
    fun eventBriteToken(): String

}
