package com.example.data.table

import org.jetbrains.exposed.sql.Table

object NotesTable:Table() {
    val id = integer("id")
    val email = varchar("author", 512)
    val noteTitle = varchar("noteTitle",512 )
    val description = text("description")
    val date = long("date")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}