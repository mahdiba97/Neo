package com.mahdiba97.notes.services

import com.mahdiba97.notes.data.ServerNoteEntity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface NoteService {
    @GET("notes")
    fun getNotes(): Call<List<ServerNoteEntity>>

    @POST("notes")
    fun putNotes(@Body note: ServerNoteEntity): Call<ServerNoteEntity>

    @FormUrlEncoded
    @PUT("notes/{id}")
    fun updateNote(
        @Field("id") id: Int,
        @Field("date") date: Long,
        @Field("text") text: String
    ): Call<ServerNoteEntity>

    @DELETE("notes/{id}")
    fun deleteNotes(@Path("id") id: Int):Call<ResponseBody>

}