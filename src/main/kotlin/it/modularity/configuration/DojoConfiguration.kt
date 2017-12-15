package it.modularity.configuration

import it.modularity.confy.annotations.Param

interface DojoConfiguration {

    @Param.Integer(defaultValue = 80)
    fun port(): Int

    @Param.String(key = "event.brite.token")
    fun eventBriteToken(): String

    @Param.String()
    fun redisHost(): String

    @Param.Integer
    fun redisPort(): Int

}
