package com.mahdiba97.notes

import android.app.ActionBar
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

const val NEW_NOTE_ID = 0
const val NOTE_TEXT_KEY = "noteTextKey"
const val CURSOR_POSITION_KEY = "cursorPositionKey"
const val SELECTED_NOTE_KEY = "selectedNoteKey"
const val URL = "http://10.0.2.2:9000/"

@Suppress("DEPRECATION")
fun networkAvailable(app: Context): Boolean {
    val connectivityManager = app.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo?.isConnectedOrConnecting ?: false
}

fun actionbar(activity:FragmentActivity?): androidx.appcompat.app.ActionBar? {
    return (activity as AppCompatActivity).supportActionBar
}