package com.sudarshanhs.chatapp.ui.screen.chat.view_model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.sudarshanhs.chatapp.core.utility.AppUtils
import com.sudarshanhs.chatapp.core.utility.SharedPrefsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val sharedPrefsHelper: SharedPrefsHelper) :
    ViewModel() {

    // Firestore instance for database operations
    private val firestore = FirebaseFirestore.getInstance()

    // List to hold all chat messages
    val messages = mutableStateListOf<ChatMessage>()
        get() = field

    // List to hold messages sent by the current user
    private val myMessages = mutableListOf<ChatMessage>()

    // List to hold messages received from the other user
    private val otherMessages = mutableListOf<ChatMessage>()


    val receiverName = mutableStateOf("")

    /**
     * Fetches all messages sent by the current user to a specific receiver.
     *
     * @param receiverId The ID of the receiver.
     */
    fun getAllMyMsg(receiverId: String) {

        val chatId = "chat_with_$receiverId"
        firestore.collection("chats").document(chatId).collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error == null && snapshot != null) {
                    messages.removeAll(myMessages)
                    myMessages.clear()
                    for (doc in snapshot.documents) {
                        val message = doc.toObject(ChatMessage::class.java)
                        message?.isMe = true
                        if (message != null) {
                            myMessages.add(message)
                        }
                    }
                    messages.addAll(myMessages)
                    messages.sortBy { it.timestamp }
                }
            }


    }

    /**
     * Fetches all messages received from the other user.
     */
    fun fetchNextPersonChat() {
        val myUserId = sharedPrefsHelper.getUserId() ?: ""
        val currentUserId = "chat_with_$myUserId"

        firestore.collection("chats").document(currentUserId).collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error == null && snapshot != null) {
                    messages.removeAll(otherMessages)
                    otherMessages.clear()
                    for (doc in snapshot.documents) {
                        val message = doc.toObject(ChatMessage::class.java)
                        message?.isMe = false
                        if (message != null) {
                            otherMessages.add(message)
                        }
                    }

                    messages.addAll(otherMessages)
                    messages.sortBy { it.timestamp }
                }
            }


    }


    /**
     * Sends a message to the specified chat.
     *
     * @param chatId The ID of the chat.
     * @param message The message to be sent.
     */
    fun sendMessage(chatId: String, message: ChatMessage) {
        firestore.collection("chats").document(chatId).collection("messages")
            .add(message)
    }


    /**
     * Fetches the name of the receiver based on their user ID.
     *
     * @param uid The user ID of the receiver.
     */
    fun fetchReceiverName(uid: String = "") {
        receiverName.value = AppUtils.getUsernameByUid(uid)
    }


}

/**
 * Data class representing a chat message.
 *
 * @param senderId The ID of the sender.
 * @param receiverId The ID of the receiver.
 * @param text The content of the message.
 * @param isMe Flag indicating if the message was sent by the current user.
 * @param timestamp The timestamp of the message.
 */
data class ChatMessage(
    val senderId: String = "",
    val receiverId: String = "",
    val text: String = "",
    var isMe: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)