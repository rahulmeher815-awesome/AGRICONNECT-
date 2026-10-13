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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import androidx.compose.ui.window.Dialog
import com.example.model.ConsultationBooking
import com.example.model.ExpertProfile
import com.example.ui.theme.AgriGreenContainer
import com.example.ui.theme.AgriGreenOnContainer
import com.example.ui.theme.AgriGreenPrimary
import com.example.ui.theme.AgriHarvestAmber

@Composable
fun BookingSlotDialog(
  expert: ExpertProfile,
  onDismiss: () -> Unit,
  onConfirmBooking: (slot: String) -> Unit
) {
  var selectedSlot by remember {
    mutableStateOf(expert.availableSlots.firstOrNull() ?: "Today, 6:00 PM")
  }
  var selectedPaymentMethod by remember { mutableStateOf("UPI (GPay / PhonePe)") }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = Color.White,
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .testTag("booking_slot_dialog")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "Book Consultation",
              fontSize = 18.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF131D16)
            )
            Text(
              text = expert.name,
              fontSize = 14.sp,
              color = AgriGreenPrimary,
              fontWeight = FontWeight.Medium
            )
          }
          IconButton(onClick = onDismiss) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "Select Available Time Slot:",
          fontSize = 13.sp,
          fontWeight = FontWeight.SemiBold,
          color = Color(0xFF4C5D51)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(
          verticalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          expert.availableSlots.forEach { slot ->
            val isSelected = slot == selectedSlot
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .clickable { selectedSlot = slot }
                .testTag("slot_option_$slot"),
              shape = RoundedCornerShape(10.dp),
              colors = CardDefaults.cardColors(
                containerColor = if (isSelected) AgriGreenContainer else Color(0xFFF6FAF7)
              ),
              border = if (isSelected) CardDefaults.outlinedCardBorder().copy(width = 1.5.dp) else null
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    tint = if (isSelected) AgriGreenPrimary else Color(0xFF6B7C70),
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = slot,
                    fontSize = 13.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) AgriGreenOnContainer else Color(0xFF223026)
                  )
                }
                if (isSelected) {
                  Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    tint = AgriGreenPrimary,
                    modifier = Modifier.size(18.dp)
                  )
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Payment Method Selector
        Text(
          text = "Select Payment Method:",
          fontSize = 12.sp,
          fontWeight = FontWeight.SemiBold,
          color = Color(0xFF4C5D51)
        )
        Spacer(modifier = Modifier.height(6.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          listOf("UPI (GPay / PhonePe)", "Kisan Credit Card (KCC)").forEach { method ->
            val isSelected = method == selectedPaymentMethod
            Card(
              shape = RoundedCornerShape(8.dp),
              colors = CardDefaults.cardColors(
                containerColor = if (isSelected) Color(0xFFE8F5E9) else Color(0xFFF3F4F6)
              ),
              modifier = Modifier.clickable { selectedPaymentMethod = method }
            ) {
              Text(
                text = method,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) AgriGreenPrimary else Color(0xFF4B5563),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Price Breakdown
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FBF9)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Specialist Tele-Advisory Fee:", fontSize = 13.sp, color = Color(0xFF5A6B5F))
              Text(text = "₹${expert.priceInRupees}", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(text = "Platform Service & Verification:", fontSize = 13.sp, color = Color(0xFF5A6B5F))
              Text(text = "Included (15%)", fontSize = 13.sp, color = AgriGreenPrimary, fontWeight = FontWeight.Medium)
            }
            HorizontalDivider(
              modifier = Modifier.padding(vertical = 8.dp),
              color = Color(0xFFE2EBE4)
            )
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(text = "Total Payable:", fontSize = 14.sp, fontWeight = FontWeight.Bold)
              Text(
                text = "₹${expert.priceInRupees}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AgriGreenPrimary
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
          onClick = { onConfirmBooking(selectedSlot) },
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("confirm_booking_button"),
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = AgriGreenPrimary)
        ) {
          Icon(imageVector = Icons.Default.Payment, contentDescription = null, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(text = "Pay & Confirm Consultation", fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun BookingReceiptDialog(
  booking: ConsultationBooking,
  onDismiss: () -> Unit,
  onOpenChat: (bookingId: String) -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(22.dp),
      color = Color.White,
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .testTag("booking_receipt_dialog")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(AgriGreenContainer),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Success",
            tint = AgriGreenPrimary,
            modifier = Modifier.size(38.dp)
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "Consultation Confirmed",
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold,
          color = AgriGreenPrimary
        )

        Box(
          modifier = Modifier
            .padding(top = 6.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFE8F5E9))
            .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
          Text(
            text = "Payment Verified • ${booking.transactionId}",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = AgriGreenPrimary
          )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF7FAF7)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            ReceiptRow(label = "Specialist", value = booking.expertName)
            ReceiptRow(label = "Specialty Domain", value = booking.expertTitle)
            ReceiptRow(label = "Scheduled Date", value = booking.selectedDate)
            ReceiptRow(label = "Scheduled Time", value = booking.selectedTime)
            ReceiptRow(label = "Crop Topic", value = booking.cropName)
            ReceiptRow(label = "Txn Reference", value = booking.transactionId)
            HorizontalDivider(color = Color(0xFFE2ECE4))
            ReceiptRow(
              label = "Amount Paid",
              value = "₹${booking.expertPrice}",
              isHighlighted = true
            )
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
          onClick = { onOpenChat(booking.id) },
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("start_chat_from_receipt_button"),
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = AgriGreenPrimary)
        ) {
          Text(
            text = "Open Consultation Channel",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
          onClick = onDismiss,
          modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
          shape = RoundedCornerShape(12.dp)
        ) {
          Text(text = "View in Consultations Tab", color = Color(0xFF5A6B5F))
        }
      }
    }
  }
}

@Composable
private fun ReceiptRow(
  label: String,
  value: String,
  isHighlighted: Boolean = false
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = label,
      fontSize = 13.sp,
      color = Color(0xFF5E6F63)
    )
    Text(
      text = value,
      fontSize = if (isHighlighted) 15.sp else 12.sp,
      fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.SemiBold,
      color = if (isHighlighted) AgriGreenPrimary else Color(0xFF131D16)
    )
  }
}
