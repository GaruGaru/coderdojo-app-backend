package it.modularity.api

import io.javalin.Context
import io.javalin.Handler

class ProbeHandler: Handler {
    override fun handle(ctx: Context) {
        ctx.status(200).result("OK")
    }
}