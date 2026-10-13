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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.model.ConsultationBooking
import com.example.model.ExpertProfile
import com.example.model.UserRole
import com.example.ui.theme.AgriGreenContainer
import com.example.ui.theme.AgriGreenOnContainer
import com.example.ui.theme.AgriGreenPrimary
import com.example.ui.theme.AgriHarvestAmber

@Composable
fun PlatformAdminScreen(
  platformEarnings: Int,
  isGoldMember: Boolean,
  experts: List<ExpertProfile>,
  bookings: List<ConsultationBooking>,
  onSwitchToFarmer: () -> Unit,
  onSwitchToExpert: () -> Unit
) {
  val totalConsultationsCount = experts.sumOf { it.consultationsCount }
  val totalGrossGmv = experts.sumOf { (it.totalEarningsRupees / 0.85).toInt() }
  val totalExpertPayouts = experts.sumOf { it.totalEarningsRupees }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF7FAF7))
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0369A1)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(20.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "AgriConnect Business Overview",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "Platform Owner & Economics Dashboard",
                color = Color(0xFFE0F2FE),
                fontSize = 12.sp
              )
            }
            Box(
              modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color(0x33FFFFFF)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Analytics,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(18.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column {
              Text(text = "Net Platform Commission", fontSize = 11.sp, color = Color(0xFFBAE6FD))
              Text(
                text = "₹$platformEarnings",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
              )
            }

            Column(horizontalAlignment = Alignment.End) {
              Text(text = "Total Gross GMV", fontSize = 11.sp, color = Color(0xFFBAE6FD))
              Text(
                text = "₹$totalGrossGmv",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
            }
          }
        }
      }
    }

    // Key Performance Indicators Grid
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        AdminKpiCard(
          title = "Active Farmers",
          value = "1,842",
          subtitle = "+14% this month",
          icon = Icons.Default.Groups,
          color = AgriGreenPrimary,
          modifier = Modifier.weight(1f)
        )
        AdminKpiCard(
          title = "Certified Experts",
          value = "${experts.size} Specialists",
          subtitle = "100% Verified",
          icon = Icons.Default.Person,
          color = AgriHarvestAmber,
          modifier = Modifier.weight(1f)
        )
      }
    }

    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        AdminKpiCard(
          title = "Consultations",
          value = "$totalConsultationsCount Done",
          subtitle = "Avg Rating ⭐ 4.8",
          icon = Icons.Default.TrendingUp,
          color = Color(0xFF0284C7),
          modifier = Modifier.weight(1f)
        )
        AdminKpiCard(
          title = "Kisan Gold Subscriptions",
          value = if (isGoldMember) "143 Active" else "142 Active",
          subtitle = "₹299/Season",
          icon = Icons.Default.WorkspacePremium,
          color = Color(0xFF9333EA),
          modifier = Modifier.weight(1f)
        )
      }
    }

    // Platform Monetization Model & Benefits Breakdown
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Text(
            text = "How AgriConnect Generates Sustainable Value",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF131D16)
          )
          Text(
            text = "Triple-win model for Farmers, Specialists, and Platform Owner",
            fontSize = 12.sp,
            color = Color(0xFF5A6B5F)
          )

          Spacer(modifier = Modifier.height(14.dp))

          BenefitRow(
            title = "1. For the Farmer",
            highlight = "Instant AI Diagnosis + Verified Guidance",
            description = "Farmers get instant Gemini AI crop disease scans in seconds, saving up to 40% in avoidable crop damage, plus direct verified expert access."
          )

          HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFEFF3EF))

          BenefitRow(
            title = "2. For the Agricultural Expert",
            highlight = "Monetized Knowledge & Flexible Tele-Advisory",
            description = "Specialists earn extra income per consultation (e.g. ₹255 net per 15-min chat), build reputation with reviews, and issue signed digital prescriptions."
          )

          HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFEFF3EF))

          BenefitRow(
            title = "3. For the Platform Builder / Owner",
            highlight = "15% Take-Rate + Gold Subscriptions",
            description = "Platform collects a 15% service commission on every booking, plus recurring Kisan Gold membership fees (₹299/season), creating a scalable SaaS business."
          )
        }
      }
    }

    // Recent Financial Transactions Ledger
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Text(
            text = "Recent Platform Commission Ledger",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF131D16)
          )

          Spacer(modifier = Modifier.height(10.dp))

          bookings.take(5).forEach { b ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = "${b.cropName} Consultation • ${b.expertName}",
                  fontSize = 13.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = Color(0xFF131D16)
                )
                Text(
                  text = "Ref: ${b.transactionId} • ${b.selectedDate}",
                  fontSize = 11.sp,
                  color = Color(0xFF5A6B5F)
                )
              }

              Column(horizontalAlignment = Alignment.End) {
                Text(
                  text = "+₹${b.platformFee}",
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Bold,
                  color = AgriGreenPrimary
                )
                Text(
                  text = "15% Commission",
                  fontSize = 10.sp,
                  color = Color(0xFF5A6B5F)
                )
              }
            }
          }
        }
      }
    }

    // Quick Portal Jump Actions
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Button(
          onClick = onSwitchToFarmer,
          modifier = Modifier.weight(1f),
          colors = ButtonDefaults.buttonColors(containerColor = AgriGreenPrimary),
          shape = RoundedCornerShape(10.dp)
        ) {
          Text("Switch to Farmer Portal")
        }

        Button(
          onClick = onSwitchToExpert,
          modifier = Modifier.weight(1f),
          colors = ButtonDefaults.buttonColors(containerColor = AgriHarvestAmber),
          shape = RoundedCornerShape(10.dp)
        ) {
          Text("Switch to Expert Portal")
        }
      }
    }
  }
}

@Composable
private fun AdminKpiCard(
  title: String,
  value: String,
  subtitle: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  color: Color,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = modifier
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = title, fontSize = 11.sp, color = Color(0xFF5A6B5F))
        Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
      }
      Spacer(modifier = Modifier.height(4.dp))
      Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF131D16))
      Text(text = subtitle, fontSize = 10.sp, color = color, fontWeight = FontWeight.SemiBold)
    }
  }
}

@Composable
private fun BenefitRow(
  title: String,
  highlight: String,
  description: String
) {
  Column {
    Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF131D16))
    Text(text = highlight, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = AgriGreenPrimary)
    Spacer(modifier = Modifier.height(2.dp))
    Text(text = description, fontSize = 12.sp, color = Color(0xFF4C5D51), lineHeight = 16.sp)
  }
}
