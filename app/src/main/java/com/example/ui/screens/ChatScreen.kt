package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ChatMessage
import com.example.model.ConsultationBooking
import com.example.model.UserRole
import com.example.ui.theme.AgriGreenContainer
import com.example.ui.theme.AgriGreenOnContainer
import com.example.ui.theme.AgriGreenPrimary
import com.example.ui.theme.AgriHarvestAmber

@Composable
fun ChatScreen(
  booking: ConsultationBooking,
  messages: List<ChatMessage>,
  onSendMessage: (bookingId: String, text: String, senderRole: UserRole?) -> Unit,
  onCompleteAndRateSession: () -> Unit,
  onNavigateBack: () -> Unit
) {
  var messageInput by remember { mutableStateOf("") }
  var activeSenderRole by remember { mutableStateOf(UserRole.FARMER) }
  val listState = rememberLazyListState()

  LaunchedEffect(messages.size) {
    if (messages.isNotEmpty()) {
      listState.animateScrollToItem(messages.size - 1)
    }
  }

  val promptChips = listOf(
    "📷 Here is a clear picture of the leaves",
    "🍂 The yellowing started 4 days ago",
    "💧 We irrigate every alternate day",
    "💊 Which fungicide or bio-spray do you recommend?",
    "🧪 Should I do a soil nutrient test?"
  )

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF7FAF7))
      .imePadding()
      .navigationBarsPadding()
  ) {
    // Chat Header
    Surface(
      color = Color.White,
      shadowElevation = 2.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 8.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          IconButton(
            onClick = onNavigateBack,
            modifier = Modifier.testTag("chat_back_button")
          ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
          }

          Box(
            modifier = Modifier
              .size(42.dp)
              .clip(CircleShape)
              .background(AgriGreenContainer),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = booking.expertName.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString(""),
              fontWeight = FontWeight.Bold,
              color = AgriGreenOnContainer,
              fontSize = 15.sp
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Text(
              text = booking.expertName,
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp,
              color = Color(0xFF131D16)
            )
            Text(
              text = "Topic: ${booking.cropName} (${booking.categoryName})",
              fontSize = 11.sp,
              color = AgriGreenPrimary,
              fontWeight = FontWeight.Medium
            )
          }
        }

        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          // Rate Expert Button
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
            modifier = Modifier
              .clickable { onCompleteAndRateSession() }
              .testTag("chat_rate_expert_header_button")
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Rate",
                tint = AgriHarvestAmber,
                modifier = Modifier.size(13.dp)
              )
              Spacer(modifier = Modifier.width(3.dp))
              Text(
                text = "Rate",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFB45309)
              )
            }
          }

          // Sender Role Toggle for Demonstration
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
              containerColor = if (activeSenderRole == UserRole.FARMER) AgriGreenContainer else Color(0xFFE0F2FE)
            ),
            modifier = Modifier
              .clickable {
                activeSenderRole = if (activeSenderRole == UserRole.FARMER) UserRole.EXPERT else UserRole.FARMER
              }
              .testTag("chat_role_toggle_button")
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = if (activeSenderRole == UserRole.FARMER) "Farmer" else "Expert",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = if (activeSenderRole == UserRole.FARMER) AgriGreenOnContainer else Color(0xFF0369A1)
              )
              Spacer(modifier = Modifier.width(3.dp))
              Icon(
                imageVector = Icons.Default.SwapHoriz,
                contentDescription = "Toggle Sender",
                tint = if (activeSenderRole == UserRole.FARMER) AgriGreenPrimary else Color(0xFF0369A1),
                modifier = Modifier.size(14.dp)
              )
            }
          }
        }
      }
    }

    // Messages Area
    LazyColumn(
      state = listState,
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .padding(horizontal = 14.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      item {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(12.dp))
              .background(Color(0xFFE8F0E9))
              .padding(horizontal = 12.dp, vertical = 4.dp)
          ) {
            Text(
              text = "Session with ${booking.expertName} • ${booking.selectedDate}, ${booking.selectedTime}",
              fontSize = 11.sp,
              color = Color(0xFF4C5D51),
              fontWeight = FontWeight.Medium
            )
          }

          Spacer(modifier = Modifier.height(6.dp))

          Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
            border = CardDefaults.outlinedCardBorder().copy(width = 1.dp),
            modifier = Modifier
              .clickable { onCompleteAndRateSession() }
              .testTag("chat_complete_and_rate_banner_button")
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = AgriHarvestAmber,
                modifier = Modifier.size(15.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Consultation Complete? Tap to Rate Expert ⭐",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFB45309)
              )
            }
          }
        }
      }

      items(messages) { msg ->
        val isFarmer = msg.senderRole == UserRole.FARMER

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = if (isFarmer) Arrangement.End else Arrangement.Start
        ) {
          Column(
            horizontalAlignment = if (isFarmer) Alignment.End else Alignment.Start,
            modifier = Modifier.widthIn(max = 280.dp)
          ) {
            Text(
              text = msg.senderName,
              fontSize = 10.sp,
              color = Color(0xFF758579),
              modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
            )

            Card(
              shape = RoundedCornerShape(
                topStart = 14.dp,
                topEnd = 14.dp,
                bottomStart = if (isFarmer) 14.dp else 2.dp,
                bottomEnd = if (isFarmer) 2.dp else 14.dp
              ),
              colors = CardDefaults.cardColors(
                containerColor = if (isFarmer) AgriGreenPrimary else Color.White
              ),
              elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
              Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                Text(
                  text = msg.text,
                  color = if (isFarmer) Color.White else Color(0xFF131D16),
                  fontSize = 13.sp,
                  lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = msg.timestampFormatted,
                  color = if (isFarmer) Color(0xFFB5E0C2) else Color(0xFF8F9E93),
                  fontSize = 9.sp,
                  modifier = Modifier.align(Alignment.End)
                )
              }
            }
          }
        }
      }
    }

    // Quick Prompt Suggestion Chips (College Demonstration helper)
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
        .padding(top = 8.dp, bottom = 4.dp)
    ) {
      Text(
        text = "Quick Suggestion Chips:",
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF6B7C70),
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
      )

      LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 12.dp, vertical = 4.dp)
      ) {
        items(promptChips) { chipText ->
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F6F2)),
            modifier = Modifier
              .clickable {
                onSendMessage(booking.id, chipText, activeSenderRole)
              }
              .testTag("chat_chip_${chipText.take(10)}")
          ) {
            Text(
              text = chipText,
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium,
              color = AgriGreenPrimary,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
          }
        }
      }
    }

    // Text Input Bar
    Surface(
      color = Color.White,
      shadowElevation = 3.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        OutlinedTextField(
          value = messageInput,
          onValueChange = { messageInput = it },
          placeholder = { Text("Type message as ${activeSenderRole.label}...") },
          modifier = Modifier
            .weight(1f)
            .testTag("chat_message_input"),
          shape = RoundedCornerShape(20.dp),
          maxLines = 3
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
          onClick = {
            if (messageInput.isNotBlank()) {
              onSendMessage(booking.id, messageInput, activeSenderRole)
              messageInput = ""
            }
          },
          modifier = Modifier
            .size(46.dp)
            .clip(CircleShape)
            .background(AgriGreenPrimary)
            .testTag("chat_send_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.Send,
            contentDescription = "Send Message",
            tint = Color.White,
            modifier = Modifier.size(20.dp)
          )
        }
      }
    }
  }
}
