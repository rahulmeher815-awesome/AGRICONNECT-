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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.model.ExpertProfile
import com.example.ui.theme.AgriGreenContainer
import com.example.ui.theme.AgriGreenOnContainer
import com.example.ui.theme.AgriGreenPrimary
import com.example.ui.theme.AgriHarvestAmber
import com.example.viewmodel.ExpertTab

@Composable
fun ExpertDashboardScreen(
  selectedTab: ExpertTab,
  onSelectTab: (ExpertTab) -> Unit,
  currentExpert: ExpertProfile,
  onUpdateExpertSettings: (expertId: String, price: Int, hours: String, bio: String) -> Unit,
  onWithdrawEarnings: (expertId: String, amount: Int) -> Boolean,
  bookings: List<ConsultationBooking>,
  messagesMap: Map<String, List<ChatMessage>>,
  onOpenChat: (bookingId: String) -> Unit,
  onOpenPrescriptionModal: (booking: ConsultationBooking) -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF7FAF7))
  ) {
    ScrollableTabRow(
      selectedTabIndex = selectedTab.ordinal,
      containerColor = Color.White,
      contentColor = AgriHarvestAmber,
      edgePadding = 16.dp,
      indicator = { tabPositions ->
        if (selectedTab.ordinal < tabPositions.size) {
          TabRowDefaults.SecondaryIndicator(
            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab.ordinal]),
            color = AgriHarvestAmber,
            height = 3.dp
          )
        }
      }
    ) {
      ExpertTab.values().forEach { tab ->
        Tab(
          selected = selectedTab == tab,
          onClick = { onSelectTab(tab) },
          text = {
            Text(
              text = tab.label,
              fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal,
              fontSize = 13.sp,
              color = if (selectedTab == tab) AgriHarvestAmber else Color(0xFF5A6B5F)
            )
          },
          modifier = Modifier.testTag("expert_tab_${tab.name}")
        )
      }
    }

    when (selectedTab) {
      ExpertTab.EARNINGS_WALLET -> {
        ExpertEarningsSection(
          expert = currentExpert,
          bookings = bookings,
          onWithdraw = onWithdrawEarnings
        )
      }
      ExpertTab.BOOKING_SESSIONS -> {
        ExpertSessionsSection(
          bookings = bookings,
          onOpenChat = onOpenChat,
          onOpenPrescription = onOpenPrescriptionModal
        )
      }
      ExpertTab.PROFILE_SCHEDULE -> {
        ExpertRatesBioSection(
          expert = currentExpert,
          onSaveSettings = onUpdateExpertSettings
        )
      }
    }
  }
}

@Composable
private fun ExpertEarningsSection(
  expert: ExpertProfile,
  bookings: List<ConsultationBooking>,
  onWithdraw: (expertId: String, amount: Int) -> Boolean
) {
  var withdrawSuccess by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF78350F)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(20.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(text = "Doctor Tele-Advisory Wallet", color = Color(0xFFFEF3C7), fontSize = 12.sp)
              Text(
                text = "₹${expert.pendingPayoutRupees}",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
              )
              Text(text = "Available Withdrawable Balance", color = Color(0xFFFDE68A), fontSize = 11.sp)
            }

            Box(
              modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(Color(0x33FFFFFF)),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.AccountBalance, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
            }
          }

          Spacer(modifier = Modifier.height(18.dp))

          Button(
            onClick = {
              if (expert.pendingPayoutRupees > 0) {
                onWithdraw(expert.id, expert.pendingPayoutRupees)
                withdrawSuccess = true
              }
            },
            enabled = expert.pendingPayoutRupees > 0,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFF78350F)),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().testTag("withdraw_payout_button")
          ) {
            Text("Instant Payout to Bank / UPI (₹${expert.pendingPayoutRupees})", fontWeight = FontWeight.Bold, fontSize = 13.sp)
          }

          if (withdrawSuccess) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "✓ Payout transfer initiated to verified bank account via IMPS.", color = Color(0xFFBBF7D0), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
          }
        }
      }
    }

    // Stats Grid
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          modifier = Modifier.weight(1f)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Text(text = "Lifetime Earnings", fontSize = 11.sp, color = Color(0xFF5A6B5F))
            Text(text = "₹${expert.totalEarningsRupees}", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = AgriGreenPrimary)
            Text(text = "Net after 15% platform fee", fontSize = 10.sp, color = Color(0xFF6B7280))
          }
        }

        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          modifier = Modifier.weight(1f)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Text(text = "Completed Sessions", fontSize = 11.sp, color = Color(0xFF5A6B5F))
            Text(text = "${expert.consultationsCount} Farmers", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color(0xFF131D16))
            Text(text = "Rating ⭐ ${expert.rating} (${expert.reviewCount})", fontSize = 10.sp, color = AgriHarvestAmber, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // Consultation Earnings Breakdown Table
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Text(text = "Transparent Earnings Breakdown", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF131D16))
          Text(text = "How every consultation payout is calculated", fontSize = 12.sp, color = Color(0xFF5A6B5F))

          Spacer(modifier = Modifier.height(12.dp))

          PayoutCalculationRow("Consultation Rate per Session", "₹${expert.priceInRupees}")
          PayoutCalculationRow("AgriConnect Platform Service Fee (15%)", "-₹${(expert.priceInRupees * 0.15).toInt()}")
          HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFEFF3EF))
          PayoutCalculationRow("Net Payout Credited to Expert", "₹${expert.priceInRupees - (expert.priceInRupees * 0.15).toInt()}", isHighlighted = true)
        }
      }
    }
  }
}

@Composable
private fun PayoutCalculationRow(label: String, value: String, isHighlighted: Boolean = false) {
  Row(
    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(text = label, fontSize = 13.sp, color = Color(0xFF4C5D51))
    Text(
      text = value,
      fontSize = if (isHighlighted) 15.sp else 13.sp,
      fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.SemiBold,
      color = if (isHighlighted) AgriGreenPrimary else Color(0xFF131D16)
    )
  }
}

@Composable
private fun ExpertSessionsSection(
  bookings: List<ConsultationBooking>,
  onOpenChat: (bookingId: String) -> Unit,
  onOpenPrescription: (booking: ConsultationBooking) -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Text(text = "Farmer Advisory Sessions", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF131D16))
      Text(text = "Scheduled consultations waiting for recommendations", fontSize = 12.sp, color = Color(0xFF5A6B5F))
    }

    items(bookings) { booking ->
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(text = booking.farmerName, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF131D16))
              Text(text = "Crop Issue: ${booking.cropName} (${booking.categoryName})", fontSize = 12.sp, color = AgriGreenPrimary, fontWeight = FontWeight.Medium)
            }
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFFEF3C7))
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Text(text = "Net: ₹${booking.expertPayout}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB45309))
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          Text(text = "📅 Slot: ${booking.selectedDate}, ${booking.selectedTime} • Ref: ${booking.transactionId}", fontSize = 12.sp, color = Color(0xFF4C5D51))

          if (booking.prescription != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "✓ Digital Prescription Issued", color = AgriGreenPrimary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
          }

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            OutlinedButton(
              onClick = { onOpenPrescription(booking) },
              modifier = Modifier.weight(1f),
              shape = RoundedCornerShape(10.dp)
            ) {
              Icon(imageVector = Icons.Default.Description, contentDescription = null, modifier = Modifier.size(15.dp), tint = AgriHarvestAmber)
              Spacer(modifier = Modifier.width(4.dp))
              Text("Prescribe", fontSize = 12.sp, color = Color(0xFFB45309), fontWeight = FontWeight.Bold)
            }

            Button(
              onClick = { onOpenChat(booking.id) },
              modifier = Modifier.weight(1f),
              colors = ButtonDefaults.buttonColors(containerColor = AgriGreenPrimary),
              shape = RoundedCornerShape(10.dp)
            ) {
              Icon(imageVector = Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(15.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Chat", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }
}

@Composable
private fun ExpertRatesBioSection(
  expert: ExpertProfile,
  onSaveSettings: (expertId: String, price: Int, hours: String, bio: String) -> Unit
) {
  var priceInput by remember(expert.id) { mutableStateOf(expert.priceInRupees.toString()) }
  var availableTimeInput by remember(expert.id) { mutableStateOf(expert.availableTime) }
  var bioInput by remember(expert.id) { mutableStateOf(expert.bio) }
  var savedSuccess by remember { mutableStateOf(false) }

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
        Column(modifier = Modifier.padding(18.dp)) {
          Text(text = "Specialist Profile & Consultation Rate", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF131D16))
          Text(text = "Set your live availability and consultation pricing", fontSize = 12.sp, color = Color(0xFF5A6B5F))

          Spacer(modifier = Modifier.height(14.dp))

          Text(text = "Consultation Fee (₹):", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF4C5D51))
          Spacer(modifier = Modifier.height(6.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            IconButton(
              onClick = {
                val current = priceInput.toIntOrNull() ?: 300
                if (current > 50) priceInput = (current - 50).toString()
              }
            ) {
              Icon(imageVector = Icons.Default.Remove, contentDescription = null)
            }

            OutlinedTextField(
              value = priceInput,
              onValueChange = { priceInput = it.filter { char -> char.isDigit() } },
              prefix = { Text("₹") },
              modifier = Modifier.weight(1f),
              shape = RoundedCornerShape(10.dp),
              singleLine = true
            )

            IconButton(
              onClick = {
                val current = priceInput.toIntOrNull() ?: 300
                priceInput = (current + 50).toString()
              }
            ) {
              Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          OutlinedTextField(
            value = availableTimeInput,
            onValueChange = { availableTimeInput = it },
            label = { Text("Working Hours Schedule") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            singleLine = true
          )

          Spacer(modifier = Modifier.height(12.dp))

          OutlinedTextField(
            value = bioInput,
            onValueChange = { bioInput = it },
            label = { Text("Bio & Experience Description") },
            modifier = Modifier.fillMaxWidth().height(100.dp),
            shape = RoundedCornerShape(10.dp)
          )

          if (savedSuccess) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "✓ Profile & Consultation fee saved successfully!", color = AgriGreenPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
          }

          Spacer(modifier = Modifier.height(16.dp))

          Button(
            onClick = {
              val p = priceInput.toIntOrNull() ?: 300
              onSaveSettings(expert.id, p, availableTimeInput, bioInput)
              savedSuccess = true
            },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = AgriHarvestAmber),
            shape = RoundedCornerShape(12.dp)
          ) {
            Text("Save Profile Changes", fontWeight = FontWeight.Bold, color = Color.White)
          }
        }
      }
    }
  }
}
