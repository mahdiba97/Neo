package com.mahdiba97.notes

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mahdiba97.notes.data.AppDatabase
import com.mahdiba97.notes.data.NoteEntity
import com.mahdiba97.notes.data.ServerNoteEntity
import com.mahdiba97.notes.services.NoteService
import com.mahdiba97.notes.services.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class MainViewModel(val app: Application) : AndroidViewModel(app) {
    private val appDatabase = AppDatabase.getInstance(app)
    val notesList = appDatabase?.noteDao()?.getAll()
    private val noteService = ServiceBuilder.buildService(NoteService::class.java)

    //    Room
    fun deleteNotes(notes: List<NoteEntity>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                appDatabase?.noteDao()?.deleteNotes(notes)
            }
        }
    }

    fun getDataFromWebservice() {
        if (networkAvailable()) {
            val notesRequest = noteService.getNotes()
            notesRequest.clone().enqueue(object : retrofit2.Callback<List<ServerNoteEntity>> {
                override fun onResponse(
                    call: Call<List<ServerNoteEntity>>,
                    response: Response<List<ServerNoteEntity>>
                ) {
                    response.body()?.let {
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                appDatabase?.noteDao()
                                    ?.insertAll(converterToNoteEntity(response.body()))
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<ServerNoteEntity>>, t: Throwable) {
                    Toast.makeText(getApplication(), "Connect to Internet", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }

    fun sendDataToWebservice() {
        var newNotes: List<ServerNoteEntity> = emptyList()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                newNotes = converterToServerNoteEntity(appDatabase?.noteDao()?.getAllNotes())
                if (networkAvailable()) {
                    for (i in newNotes) {
                        val notesRequest = noteService.putNotes(i)
                        notesRequest.clone().enqueue(object : retrofit2.Callback<ServerNoteEntity> {
                            override fun onResponse(
                                call: Call<ServerNoteEntity>,
                                response: Response<ServerNoteEntity>
                            ) {
                            }

                            override fun onFailure(call: Call<ServerNoteEntity>, t: Throwable) {}
                        })
                    }
                }
            }
        }

    }

    fun deleteDataFromServer(notes: List<NoteEntity>) {
        if (networkAvailable()) {
            for (i in notes) {
                val noteRequest = noteService.deleteNotes(i.id)
                noteRequest.clone().enqueue(object : retrofit2.Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {

                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    }

                })
            }
        }
    }

    @Suppress("DEPRECATION")
    fun networkAvailable(): Boolean {
        val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }


}