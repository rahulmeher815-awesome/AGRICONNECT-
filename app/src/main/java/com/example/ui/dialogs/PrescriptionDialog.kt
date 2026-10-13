package com.example.ui.dialogs

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.ConsultationBooking
import com.example.model.PrescribedMedicine
import com.example.ui.theme.AgriGreenContainer
import com.example.ui.theme.AgriGreenOnContainer
import com.example.ui.theme.AgriGreenPrimary
import com.example.ui.theme.AgriHarvestAmber

@Composable
fun PrescriptionDialog(
  booking: ConsultationBooking,
  onDismiss: () -> Unit,
  onIssuePrescription: (bookingId: String, expertName: String, cropName: String, medicines: List<PrescribedMedicine>, advice: String, days: Int) -> Unit
) {
  var medicineName by remember { mutableStateOf("Propiconazole 25% EC (Tilt)") }
  var dosageInput by remember { mutableStateOf("1.0 ml per Liter of water (200 ml/acre)") }
  var applicationMethod by remember { mutableStateOf("Foliar Spray during evening (4 PM - 6 PM)") }
  var adviceText by remember { mutableStateOf("Drain standing field water for 36-48 hours. Avoid urea/nitrogen top dressing until lesions turn necrotic and stop expanding.") }
  var withholdingDays by remember { mutableStateOf("14") }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = Color.White,
      modifier = Modifier
        .fillMaxWidth()
        .padding(6.dp)
        .testTag("prescription_dialog")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState())
          .padding(20.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Description,
              contentDescription = null,
              tint = AgriHarvestAmber,
              modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "Issue Digital Prescription",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF131D16)
              )
              Text(
                text = "For ${booking.farmerName} • ${booking.cropName}",
                fontSize = 12.sp,
                color = Color(0xFF5A6B5F)
              )
            }
          }
          IconButton(onClick = onDismiss) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FBF9)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Text(
              text = "Recommended Chemical / Biological Remedy:",
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold,
              color = Color(0xFF4C5D51)
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
              value = medicineName,
              onValueChange = { medicineName = it },
              label = { Text("Product / Formulation Name") },
              modifier = Modifier.fillMaxWidth(),
              singleLine = true,
              shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
              value = dosageInput,
              onValueChange = { dosageInput = it },
              label = { Text("Accurate Dosage") },
              modifier = Modifier.fillMaxWidth(),
              singleLine = true,
              shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
              value = applicationMethod,
              onValueChange = { applicationMethod = it },
              label = { Text("Application Method & Time") },
              modifier = Modifier.fillMaxWidth(),
              singleLine = true,
              shape = RoundedCornerShape(8.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
          value = adviceText,
          onValueChange = { adviceText = it },
          label = { Text("Agronomic Instructions for Farmer") },
          modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
          shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = withholdingDays,
          onValueChange = { withholdingDays = it.filter { c -> c.isDigit() } },
          label = { Text("Safe Pre-Harvest Withholding Period (Days)") },
          modifier = Modifier.fillMaxWidth(),
          singleLine = true,
          shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Button(
          onClick = {
            val med = PrescribedMedicine(
              name = medicineName,
              dosage = dosageInput,
              applicationMethod = applicationMethod,
              targetPestOrDisease = booking.cropName + " Diagnosis"
            )
            val days = withholdingDays.toIntOrNull() ?: 14
            onIssuePrescription(
              booking.id,
              booking.expertName,
              booking.cropName,
              listOf(med),
              adviceText,
              days
            )
          },
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("submit_prescription_button"),
          colors = ButtonDefaults.buttonColors(containerColor = AgriGreenPrimary),
          shape = RoundedCornerShape(12.dp)
        ) {
          Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(text = "Sign & Issue Digital Prescription", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
      }
    }
  }
}
