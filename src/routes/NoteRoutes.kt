package com.example.routes

import com.example.data.model.Note
import com.example.data.model.SimpleResponse
import com.example.data.model.User
import com.example.repository.Repo
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

const val NOTESROUTE = "$API_VERSION/notes"
const val CREATENOTESROUTE = "$NOTESROUTE/create"
const val UPDATENOTESROUTE = "$NOTESROUTE/update"
const val DELETENOTESROUTE = "$NOTESROUTE/delete"

@Location(NOTESROUTE)
class NotesAllRoute

@Location(CREATENOTESROUTE)
class NotesCreateRoute

@Location(UPDATENOTESROUTE)
class NotesUpdateRoute

@Location(DELETENOTESROUTE)
class NotesDeleteRoute


fun Route.noteRoutes(
    db: Repo,
    hashFunction: (String)->String
) {
    authenticate("jwt") {

        get<NotesAllRoute> {
            try {
                val email = call.principal<User>()!!.email
                val notes = db.getAllNotes(email)
                call.respond(HttpStatusCode.OK, notes)
            } catch (e:Exception) {
                call.respond(HttpStatusCode.Conflict, emptyList<Note>())
            }
        }

        post<NotesCreateRoute> {
            val noteRequest = try {
                call.receive<Note>()
            } catch (e:Exception) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Invalid Request"))
                return@post
            }
            try {
                val email = call.principal<User>()!!.email
                db.addNote(noteRequest, email)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Note added successfully!"))
            } catch (e:Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, "Error occurred on saving note: ${e.message}"))
            }
        }

        post<NotesUpdateRoute> {
            val noteRequest = try {
                call.receive<Note>()
            } catch (e:Exception) {
                call.respond(HttpStatusCode.BadRequest, SimpleResponse(false, "Invalid Request"))
                return@post
            }
            try {
                val email = call.principal<User>()!!.email
                db.updateNote(noteRequest, email)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Note updated successfully!"))
            } catch (e:Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, "Error occurred on saving note: ${e.message}"))
            }
        }

        delete<NotesDeleteRoute> {
            val noteId = try {
                call.request.queryParameters["id"]!!.toInt()
            } catch (e:Exception) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            try {
                val email = call.principal<User>()!!.email
                db.deleteNote(noteId, email)
                call.respond(HttpStatusCode.OK, SimpleResponse(true, "Note deleted successfully!"))
            } catch (e:Exception) {
                call.respond(HttpStatusCode.Conflict, SimpleResponse(false, "Error occurred on deleting note: ${e.message}"))
            }
        }


    }
}