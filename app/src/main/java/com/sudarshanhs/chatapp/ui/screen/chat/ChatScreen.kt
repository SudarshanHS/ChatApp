package com.sudarshanhs.chatapp.ui.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sudarshanhs.chatapp.extension_function.formatToTime
import com.sudarshanhs.chatapp.ui.common.ChatAppToolBar
import com.sudarshanhs.chatapp.ui.screen.chat.view_model.ChatMessage
import com.sudarshanhs.chatapp.ui.screen.chat.view_model.ChatViewModel


@Composable
fun ChatScreen(navController: NavController, userId: String?) {
    val viewModel: ChatViewModel = hiltViewModel()
    val chatId = "chat_with_$userId" // Example chat ID
    val messages = viewModel.messages

    viewModel.fetchReceiverName(userId ?: "")

    LaunchedEffect(chatId) {
        viewModel.getAllMyMsg(userId ?: "")
        viewModel.fetchNextPersonChat()

    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 36.dp)) {
        ChatAppToolBar(
            title = "Chat with ${viewModel.receiverName.value}",
            modifier = Modifier.fillMaxWidth()
        ){}

        // Chat Messages
        ChatScreen(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp), messages = messages
        )

        // Input Field and Send Button
        var messageText by remember { mutableStateOf("") }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            androidx.compose.material3.TextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier.weight(1f)
            )
            Button(onClick = {
                if (messageText.isNotBlank()) {
                    viewModel.sendMessage(
                        chatId,
                        ChatMessage(senderId = "currentUserId", text = messageText)
                    )
                    messageText = ""
                }
            }) {
                Text(text = "Send")
            }
        }
    }
}


@Composable
fun ChatScreen(modifier: Modifier, messages: List<ChatMessage>) {

    val listState = rememberLazyListState()

    // Scroll to the bottom whenever messages are updated
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier
    ) {
        items(messages) { message ->
            MessageView(message = message)
        }
    }
}

@Composable
fun MessageView1(message: ChatMessage) {
    // Define background color based on message sender
    val backgroundColor = if (message.isMe) {
        Color.LightGray // Background for user's message
    } else {
        Color.White // Background for the other person's message
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = if (message.isMe) {
            Arrangement.End // Align to the right for user's message
        } else {
            Arrangement.Start // Align to the left for other's message
        }
    ) {
        Text(
            text = message.text,
            modifier = Modifier
                .background(backgroundColor)
                .padding(10.dp),  // Padding inside the background
            style = TextStyle(fontWeight = FontWeight.Bold) // Optional styling
        )
    }
}


@Composable
fun MessageView(message: ChatMessage) {
    val bubbleColor = if (message.isMe) Color(0xFFD2F8D2) else Color.White
    val alignment = if (message.isMe) Arrangement.End else Arrangement.Start
    val bubbleShape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomStart = if (message.isMe) 16.dp else 0.dp,
        bottomEnd = if (message.isMe) 0.dp else 16.dp
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = alignment
    ) {
        Column(
            modifier = Modifier
                .background(color = bubbleColor, shape = bubbleShape)
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .widthIn(max = 280.dp)
        ) {
            Text(
                text = message.text,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = message.timestamp.formatToTime(),
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Gray
                ),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}
