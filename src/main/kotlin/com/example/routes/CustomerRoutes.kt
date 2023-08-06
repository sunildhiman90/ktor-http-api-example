package com.example.routes

import com.example.models.customerStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.models.Customer
import io.ktor.server.request.*

/**
 * grouping all routes under a single route function with different HTTP methods
 */
fun Route.customerRouting() {
    route("/customer") {
        get {
            if (customerStorage.isNotEmpty()) {
                /** respond method take a Kotlin object and return it serialized in a specified format, here it will return serialized list of [Customer]
                 * in order for this to work, we need the ContentNegotiation plugin, which is already installed with the json serializer in plugins/Serialization.kt.
                 */
                call.respond(customerStorage)
            } else {
                call.respondText("No customers found", status = HttpStatusCode.OK)
            }
        }
        get("{id?}") {

            // If it does not exist, we respond with a 400 Bad Request status code and an error message
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )

            // find the corresponding record in our customerStorage, else respond with a 404 NotFound status code and an error message
            val customer =
                customerStorage.find { it.id == id } ?: return@get call.respondText(
                    "No customer with id $id",
                    status = HttpStatusCode.NotFound
                )

            // if found record, send it
            call.respond(customer)
        }
        post {
            // Receives content for this request as type Customer
            val customer = call.receive<Customer>()
            customerStorage.add(customer)
            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (customerStorage.removeIf { it.id == id }) {
                call.respondText("Customer removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}