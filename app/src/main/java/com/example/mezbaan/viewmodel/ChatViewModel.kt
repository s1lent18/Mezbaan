package com.example.mezbaan.viewmodel

import androidx.lifecycle.ViewModel
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

class ChatViewModel : ViewModel() {
    private lateinit var socket: Socket

    init {
        try {
            socket = IO.socket("http://localhost:5000/")
            socket.connect()

            socket.emit("register", "user123")

            socket.on("message") { args ->
                if (args.isNotEmpty()) {
                    val data = args[0] as JSONObject
                    val senderId = data.getString("senderId")
                    val message = data.getString("message")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun sendMessage(recipientId: String, message: String) {
        val data = JSONObject()
        data.put("senderId", "user123")
        data.put("recipientId", recipientId)
        data.put("message", message)

        socket.emit("user-message", data)
    }

    override fun onCleared() {
        super.onCleared()
        socket.disconnect()
    }
}