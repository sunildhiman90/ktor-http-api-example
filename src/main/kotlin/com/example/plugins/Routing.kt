package com.example.plugins

import com.example.routes.customerRouting
import com.example.routes.getOrderRoute
import com.example.routes.listOrdersRoute
import com.example.routes.totalizeOrderRoute
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    //it will register our routes with ktor
    routing {
        customerRouting()
        listOrdersRoute()
        getOrderRoute()
        totalizeOrderRoute()
    }
}
