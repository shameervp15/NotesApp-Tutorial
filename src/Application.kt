package com.example

import com.example.auth.JWTService
import com.example.auth.hash
import com.example.data.model.User
import com.example.repository.DatabaseFactory
import com.example.repository.Repo
import com.example.routes.noteRoutes
import com.example.routes.userRoutes
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.sessions.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.gson.*
import io.ktor.features.*
import io.ktor.locations.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    DatabaseFactory.init()
    val db = Repo()
    val jwtService = JWTService()
    val hashFunction = { s:String -> hash(s) }

    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(Authentication) {
        jwt("jwt") {
            verifier(jwtService.verifier)
            realm = "noteServer"
            validate { credentials ->
                val payload = credentials.payload
                val email = payload.getClaim("email").asString()
                if (email != null) {
                    val user = db.findUser(email)
                    if (user != null) {
                        return@validate user
                    }
                }
                null
            }
        }
    }

    install(ContentNegotiation) {
        gson {
        }
    }

    install (Locations) {

    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        userRoutes(db, jwtService, hashFunction)
        noteRoutes(db, hashFunction)

    }
}

data class MySession(val count: Int = 0)

