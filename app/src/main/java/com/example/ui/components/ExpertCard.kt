package com.example.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ExpertProfile
import com.example.ui.theme.AgriGreenContainer
import com.example.ui.theme.AgriGreenOnContainer
import com.example.ui.theme.AgriGreenPrimary
import com.example.ui.theme.AgriHarvestAmber
import com.example.ui.theme.AgriOutline

@Composable
fun ExpertCard(
  expert: ExpertProfile,
  onViewProfile: (ExpertProfile) -> Unit,
  onBookConsultation: (ExpertProfile) -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("expert_card_${expert.id}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = Color.White
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
      ) {
        // Avatar Badge
        Box(
          modifier = Modifier
            .size(54.dp)
            .clip(CircleShape)
            .background(AgriGreenContainer),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = expert.name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString(""),
            color = AgriGreenOnContainer,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(
              text = expert.name,
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF131D16)
            )
            Icon(
              imageVector = Icons.Default.Verified,
              contentDescription = "Verified Expert",
              tint = AgriGreenPrimary,
              modifier = Modifier.size(18.dp)
            )
          }

          Text(
            text = expert.title,
            color = AgriGreenPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
          )

          Spacer(modifier = Modifier.height(4.dp))

          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "${expert.experienceYears} years experience",
              color = Color(0xFF5A6B5F),
              fontSize = 12.sp
            )
            Text(
              text = " • ",
              color = Color(0xFF9EABA2),
              fontSize = 12.sp
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Rating",
                tint = AgriHarvestAmber,
                modifier = Modifier.size(15.dp)
              )
              Spacer(modifier = Modifier.width(2.dp))
              Text(
                text = "${expert.rating}",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF131D16)
              )
              Text(
                text = " (${expert.reviewCount})",
                fontSize = 11.sp,
                color = Color(0xFF758579)
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Price & Timing Row
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(10.dp))
          .background(Color(0xFFF4F7F4))
          .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Consultation Fee",
            fontSize = 11.sp,
            color = Color(0xFF6B7C70)
          )
          Text(
            text = "₹${expert.priceInRupees} / consultation",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = AgriGreenPrimary
          )
        }

        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(start = 8.dp)
        ) {
          Icon(
            imageVector = Icons.Default.AccessTime,
            contentDescription = "Available Time",
            tint = Color(0xFF5A6B5F),
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = expert.availableTime,
            fontSize = 11.sp,
            color = Color(0xFF4C5D51),
            fontWeight = FontWeight.Medium
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Action Buttons
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        OutlinedButton(
          onClick = { onViewProfile(expert) },
          modifier = Modifier
            .weight(1f)
            .height(44.dp)
            .testTag("view_profile_button_${expert.id}"),
          shape = RoundedCornerShape(10.dp)
        ) {
          Text(
            text = "View Profile",
            fontSize = 13.sp,
            color = AgriGreenPrimary,
            fontWeight = FontWeight.SemiBold
          )
        }

        Button(
          onClick = { onBookConsultation(expert) },
          modifier = Modifier
            .weight(1.3f)
            .height(44.dp)
            .testTag("book_consultation_button_${expert.id}"),
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = AgriGreenPrimary)
        ) {
          Icon(
            imageVector = Icons.Default.CalendarMonth,
            contentDescription = "Book Consultation",
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Book Consultation",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}
