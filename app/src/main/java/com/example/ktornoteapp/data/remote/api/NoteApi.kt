package com.example.ktornoteapp.data.remote.api

import com.example.ktornoteapp.data.remote.models.RemoteNote
import com.example.ktornoteapp.data.remote.models.SimpleResponse
import com.example.ktornoteapp.data.remote.models.User
import com.example.ktornoteapp.utils.Constants.API_VER
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface NoteApi {

    @Headers("Content-Type: application/json")
    @POST("$API_VER/users/register")
    suspend fun createAccount(
        @Body user: User

    ): SimpleResponse

    @Headers("Content-Type: application/json")
    @POST("$API_VER/users/login")
    suspend fun login(
        @Body user: User

    ): SimpleResponse

    //Notes

    @Headers("Content-Type: application/json")
    @POST("$API_VER/notes/create")
    suspend fun createNote(
        @Header("Authorization") token: String,
        @Body note: RemoteNote
    ): SimpleResponse

    @Headers("Content-Type: application/json")
    @GET("$API_VER/notes")
    suspend fun getNotes(
        @Header("Authorization") token: String
    ): List<RemoteNote>

    @Headers("Content-Type: application/json")
    @POST("$API_VER/notes/update")
    suspend fun updateNote(
        @Header("Authorization") token: String,
        @Body note: RemoteNote
    ): SimpleResponse

    @Headers("Content-Type: application/json")
    @DELETE("$API_VER/notes/delete")
    suspend fun deleteNote(
        @Header("Authorization") token: String,
        @Query("id") noteId: Int
    ): SimpleResponse
}