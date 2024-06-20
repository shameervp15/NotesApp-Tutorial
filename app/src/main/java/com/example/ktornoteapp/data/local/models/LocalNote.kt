package com.example.ktornoteapp.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.UUID

@Entity
data class LocalNote(
    var noteTitle:String? = null,
    var description:String? = null,
    var date:Long = System.currentTimeMillis(),
    var connected:Boolean = false,
    var locallyDeleted:Boolean = false,

    @PrimaryKey(autoGenerate = false)
    var noteId:Int = UUID.randomUUID().toString().toInt()
): Serializable
