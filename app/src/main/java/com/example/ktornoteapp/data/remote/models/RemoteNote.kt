package com.example.ktornoteapp.data.remote.models

import androidx.room.PrimaryKey
import java.util.UUID

data class RemoteNote(
    var noteTitle:String?,
    var description:String?,
    var date:Long,
    var noteId:Int
)
