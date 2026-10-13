package com.example.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.ExpertProfile
import com.example.ui.theme.AgriGreenContainer
import com.example.ui.theme.AgriGreenOnContainer
import com.example.ui.theme.AgriGreenPrimary
import com.example.ui.theme.AgriHarvestAmber

@Composable
fun StarRatingDialog(
  expert: ExpertProfile,
  bookingCropTopic: String = "Paddy",
  bookingId: String? = null,
  onDismiss: () -> Unit,
  onSubmitRating: (expertId: String, stars: Int, bookingId: String?) -> Unit
) {
  var selectedStars by remember { mutableStateOf(5) }
  val selectedFeedbackChips = remember { mutableStateListOf<String>() }

  val feedbackOptions = listOf(
    "Accurate Diagnosis",
    "Quick Response",
    "Saved My Crop",
    "Clear Dosage Advice",
    "Cost Effective"
  )

  val ratingDescription = when (selectedStars) {
    5 -> "⭐ 5 Stars — Excellent Guidance!"
    4 -> "⭐ 4 Stars — Very Helpful & Practical"
    3 -> "⭐ 3 Stars — Good Consultation"
    2 -> "⭐ 2 Stars — Fair Advice"
    else -> "⭐ 1 Star — Needs Improvement"
  }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(22.dp),
      color = Color.White,
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .testTag("star_rating_dialog")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Rate Consultation",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF131D16)
          )
          IconButton(onClick = onDismiss) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
          modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(AgriGreenContainer),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = expert.name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString(""),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = AgriGreenOnContainer
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = expert.name,
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF131D16)
        )

        Text(
          text = "${expert.title} • Topic: $bookingCropTopic",
          fontSize = 12.sp,
          color = AgriGreenPrimary,
          fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
          text = "How helpful was the agricultural guidance?",
          fontSize = 13.sp,
          color = Color(0xFF5A6B5F),
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Interactive 5 Star Row
        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          for (starIndex in 1..5) {
            val isFilled = starIndex <= selectedStars
            IconButton(
              onClick = { selectedStars = starIndex },
              modifier = Modifier
                .size(44.dp)
                .testTag("star_button_$starIndex")
            ) {
              Icon(
                imageVector = if (isFilled) Icons.Default.Star else Icons.Default.StarBorder,
                contentDescription = "$starIndex Star",
                tint = if (isFilled) AgriHarvestAmber else Color(0xFFCBD8CF),
                modifier = Modifier.size(36.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Descriptive Rating Label
        Text(
          text = ratingDescription,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold,
          color = AgriHarvestAmber
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Optional Quick Tags
        Text(
          text = "What was good about the consultation?",
          fontSize = 11.sp,
          fontWeight = FontWeight.SemiBold,
          color = Color(0xFF6B7C70),
          modifier = Modifier.align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(6.dp))

        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          items(feedbackOptions) { chip ->
            val isSelected = selectedFeedbackChips.contains(chip)
            Card(
              shape = RoundedCornerShape(16.dp),
              colors = CardDefaults.cardColors(
                containerColor = if (isSelected) AgriGreenContainer else Color(0xFFF3F7F4)
              ),
              modifier = Modifier
                .clickable {
                  if (isSelected) selectedFeedbackChips.remove(chip) else selectedFeedbackChips.add(chip)
                }
                .testTag("feedback_chip_${chip.take(8)}")
            ) {
              Text(
                text = chip,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) AgriGreenOnContainer else Color(0xFF4C5D51),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
          onClick = {
            onSubmitRating(expert.id, selectedStars, bookingId)
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("submit_rating_button"),
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = AgriGreenPrimary)
        ) {
          Text(
            text = "Submit Rating & Update Profile",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
          onClick = onDismiss,
          modifier = Modifier
            .fillMaxWidth()
            .height(42.dp),
          shape = RoundedCornerShape(12.dp)
        ) {
          Text(text = "Cancel", color = Color(0xFF5A6B5F))
        }
      }
    }
  }
}
