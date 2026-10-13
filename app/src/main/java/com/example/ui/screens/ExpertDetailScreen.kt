package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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

@Composable
fun ExpertDetailScreen(
  expert: ExpertProfile,
  onBookConsultation: (ExpertProfile) -> Unit,
  onNavigateBack: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF7FAF7))
  ) {
    Surface(
      color = Color.White,
      shadowElevation = 2.dp,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 8.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = onNavigateBack,
          modifier = Modifier.testTag("expert_detail_back_button")
        ) {
          Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "Expert Profile",
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          color = Color(0xFF131D16)
        )
      }
    }

    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Box(
              modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(AgriGreenContainer),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = expert.name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString(""),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = AgriGreenOnContainer
              )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = expert.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF131D16)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Icon(
                imageVector = Icons.Default.Verified,
                contentDescription = "Verified",
                tint = AgriGreenPrimary,
                modifier = Modifier.size(20.dp)
              )
            }

            Text(
              text = expert.title,
              fontSize = 14.sp,
              color = AgriGreenPrimary,
              fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = AgriHarvestAmber, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(3.dp))
              Text(text = "${expert.rating}", fontWeight = FontWeight.Bold, fontSize = 13.sp)
              Text(text = " (${expert.reviewCount} farmer reviews) • ${expert.experienceYears} yrs experience", fontSize = 12.sp, color = Color(0xFF5A6B5F))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Price & Schedule Tag
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = Color(0xFFF4F7F4)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column {
                  Text(text = "Fee per Session", fontSize = 11.sp, color = Color(0xFF5A6B5F))
                  Text(
                    text = "₹${expert.priceInRupees} / consultation",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriGreenPrimary
                  )
                }

                Column(horizontalAlignment = Alignment.End) {
                  Text(text = "Available Hours", fontSize = 11.sp, color = Color(0xFF5A6B5F))
                  Text(
                    text = expert.availableTime,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF131D16)
                  )
                }
              }
            }
          }
        }
      }

      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(18.dp)) {
            Text(text = "About & Specialization", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = expert.bio,
              fontSize = 13.sp,
              color = Color(0xFF4C5D51),
              lineHeight = 19.sp
            )

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = Color(0xFFEFF3EF))
            Spacer(modifier = Modifier.height(14.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.School, contentDescription = null, tint = AgriGreenPrimary, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(text = expert.qualifications, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = AgriGreenPrimary, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(text = expert.location, fontSize = 13.sp, color = Color(0xFF4C5D51))
            }
          }
        }
      }

      item {
        Button(
          onClick = { onBookConsultation(expert) },
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("detail_book_consultation_button"),
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = AgriGreenPrimary)
        ) {
          Icon(imageVector = Icons.Default.CalendarMonth, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text(text = "Book Consultation Slot", fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
