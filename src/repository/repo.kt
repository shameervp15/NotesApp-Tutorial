package com.example.repository

import com.example.data.model.Note
import com.example.data.model.User
import com.example.data.table.UserTable
import com.example.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import com.example.data.table.NotesTable
import org.jetbrains.exposed.sql.*

class Repo{

    suspend fun addUser(user: User) {
        dbQuery {
            UserTable.insert { ut ->
                ut[UserTable.email] = user.email
                ut[UserTable.name] = user.userName
                ut[UserTable.hashPassword] = user.hashPassword
            }
        }
    }

    suspend fun findUser(email: String): User? = dbQuery {
        UserTable.select { UserTable.email.eq(email) }
            .mapNotNull { rowToUser(it) }
            .singleOrNull()
    }

    private fun rowToUser(row:ResultRow?): User? {
        if (row == null) {
            return null
        }
        return User(
            email = row[UserTable.email],
            hashPassword = row[UserTable.hashPassword],
            userName = row[UserTable.name]
        )
    }

    // Notes Section
    suspend fun addNote(note: Note, email: String) {
        dbQuery {
            NotesTable.insert { nt ->
                nt[NotesTable.id] = note.id
                nt[NotesTable.email] = email
                nt[NotesTable.noteTitle] = note.noteTitle
                nt[NotesTable.description] = note.description
                nt[NotesTable.date] = note.date
            }
        }
    }

    suspend fun getAllNotes(email: String):List<Note> = dbQuery {
        NotesTable.select {
            NotesTable.email.eq(email)
        }.mapNotNull { rowToNote(it) }
    }

    private fun rowToNote(row:ResultRow?): Note? {
        if (row == null) {
            return null
        }
        return Note(
            id=row[NotesTable.id],
            noteTitle = row[NotesTable.noteTitle],
            description = row[NotesTable.description],
            date = row[NotesTable.date]
        )
    }

    suspend fun updateNote(note: Note, email: String) {
        dbQuery {
            NotesTable.update(
                where = {
                    NotesTable.email.eq(email) and NotesTable.id.eq(note.id)
                }
            ){ nt->
                nt[NotesTable.noteTitle] = note.noteTitle
                nt[NotesTable.description] = note.description
                nt[NotesTable.date] = note.date
            }
        }
    }

    suspend fun deleteNote(id: Int, email: String) {
        dbQuery {
            NotesTable.deleteWhere { NotesTable.id.eq(id) and NotesTable.email.eq(email) }
        }
    }
}