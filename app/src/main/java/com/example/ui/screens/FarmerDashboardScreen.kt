package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import com.example.model.AgriculturalCategory
import com.example.model.AiCropDiagnosis
import com.example.model.ChatMessage
import com.example.model.ConsultationBooking
import com.example.model.CropProblem
import com.example.model.ExpertProfile
import com.example.model.FertilizerCalculation
import com.example.model.MandiCommodityPrice
import com.example.model.WeatherAdvisory
import com.example.ui.components.ExpertCard
import com.example.ui.theme.AgriGreenContainer
import com.example.ui.theme.AgriGreenOnContainer
import com.example.ui.theme.AgriGreenPrimary
import com.example.ui.theme.AgriHarvestAmber
import com.example.viewmodel.FarmerTab
import com.example.viewmodel.ProblemFormState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FarmerDashboardScreen(
  selectedTab: FarmerTab,
  onSelectTab: (FarmerTab) -> Unit,
  categories: List<AgriculturalCategory>,
  problemForm: ProblemFormState,
  onCropNameChange: (String) -> Unit,
  onCategoryChange: (String) -> Unit,
  onDescriptionChange: (String) -> Unit,
  onLocationChange: (String) -> Unit,
  onPrefillExample: (crop: String, categoryId: String, desc: String, loc: String) -> Unit,
  onRunAiDiagnosis: () -> Unit,
  isDiagnosingAi: Boolean,
  latestAiDiagnosis: AiCropDiagnosis?,
  experts: List<ExpertProfile>,
  activeCategoryFilter: String?,
  onSetCategoryFilter: (String?) -> Unit,
  onViewExpertProfile: (ExpertProfile) -> Unit,
  onBookConsultation: (ExpertProfile) -> Unit,
  bookings: List<ConsultationBooking>,
  messagesMap: Map<String, List<ChatMessage>>,
  onOpenChat: (bookingId: String) -> Unit,
  onRateBooking: (booking: ConsultationBooking) -> Unit = {},
  mandiPrices: List<MandiCommodityPrice>,
  weatherAdvisory: WeatherAdvisory,
  fertilizerCalculation: FertilizerCalculation?,
  onCalculateFertilizer: (crop: String, acres: Float) -> Unit,
  isGoldMember: Boolean,
  onUpgradeToGold: () -> Unit
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF7FAF7))
  ) {
    // Top Tabs
    ScrollableTabRow(
      selectedTabIndex = selectedTab.ordinal,
      containerColor = Color.White,
      contentColor = AgriGreenPrimary,
      edgePadding = 12.dp,
      indicator = { tabPositions ->
        if (selectedTab.ordinal < tabPositions.size) {
          TabRowDefaults.SecondaryIndicator(
            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab.ordinal]),
            color = AgriGreenPrimary,
            height = 3.dp
          )
        }
      }
    ) {
      FarmerTab.values().forEach { tab ->
        Tab(
          selected = selectedTab == tab,
          onClick = { onSelectTab(tab) },
          text = {
            Text(
              text = tab.label,
              fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal,
              fontSize = 13.sp,
              color = if (selectedTab == tab) AgriGreenPrimary else Color(0xFF5A6B5F)
            )
          },
          modifier = Modifier.testTag("farmer_tab_${tab.name}")
        )
      }
    }

    when (selectedTab) {
      FarmerTab.AI_DIAGNOSIS -> {
        AiCropDoctorSection(
          categories = categories,
          form = problemForm,
          onCropNameChange = onCropNameChange,
          onCategoryChange = onCategoryChange,
          onDescriptionChange = onDescriptionChange,
          onLocationChange = onLocationChange,
          onPrefillExample = onPrefillExample,
          onRunDiagnosis = onRunAiDiagnosis,
          isLoading = isDiagnosingAi,
          diagnosisResult = latestAiDiagnosis,
          onConsultSpecialist = {
            onSelectTab(FarmerTab.CONSULT_EXPERTS)
          }
        )
      }
      FarmerTab.CONSULT_EXPERTS -> {
        SpecialistsSection(
          categories = categories,
          experts = experts,
          activeFilter = activeCategoryFilter,
          diagnosis = latestAiDiagnosis,
          onFilterChange = onSetCategoryFilter,
          onViewProfile = onViewExpertProfile,
          onBookConsultation = onBookConsultation
        )
      }
      FarmerTab.MANDI_WEATHER -> {
        MandiWeatherToolsSection(
          mandiPrices = mandiPrices,
          weather = weatherAdvisory,
          fertilizerCalculation = fertilizerCalculation,
          onCalculateFertilizer = onCalculateFertilizer
        )
      }
      FarmerTab.MY_BOOKINGS -> {
        FarmerConsultationsSection(
          bookings = bookings,
          onOpenChat = onOpenChat,
          onRateBooking = onRateBooking
        )
      }
      FarmerTab.PROFILE -> {
        FarmerFarmProfileSection(
          isGoldMember = isGoldMember,
          onUpgradeToGold = onUpgradeToGold
        )
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AiCropDoctorSection(
  categories: List<AgriculturalCategory>,
  form: ProblemFormState,
  onCropNameChange: (String) -> Unit,
  onCategoryChange: (String) -> Unit,
  onDescriptionChange: (String) -> Unit,
  onLocationChange: (String) -> Unit,
  onPrefillExample: (crop: String, categoryId: String, desc: String, loc: String) -> Unit,
  onRunDiagnosis: () -> Unit,
  isLoading: Boolean,
  diagnosisResult: AiCropDiagnosis?,
  onConsultSpecialist: () -> Unit
) {
  var categoryMenuExpanded by remember { mutableStateOf(false) }
  val selectedCat = categories.find { it.id == form.selectedCategoryId } ?: categories.firstOrNull()

  val sampleCrops = listOf("Paddy", "Wheat", "Cotton", "Tomato", "Potato", "Onion")

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Hero Header
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(38.dp)
                  .clip(CircleShape)
                  .background(AgriGreenContainer),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.AutoAwesome,
                  contentDescription = null,
                  tint = AgriGreenPrimary,
                  modifier = Modifier.size(22.dp)
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "AI Crop Doctor & Disease Triage",
                  fontSize = 17.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF131D16)
                )
                Text(
                  text = "Instant diagnosis powered by Gemini Agricultural AI",
                  fontSize = 12.sp,
                  color = Color(0xFF5A6B5F)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Quick Crop Selector
          Text(text = "Select Crop:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF4C5D51))
          Spacer(modifier = Modifier.height(6.dp))
          LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(sampleCrops) { crop ->
              val isSelected = form.cropName.equals(crop, ignoreCase = true)
              FilterChip(
                selected = isSelected,
                onClick = { onCropNameChange(crop) },
                label = { Text(crop) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = AgriGreenPrimary,
                  selectedLabelColor = Color.White
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Quick Common Symptoms Chip
          Text(text = "Quick Field Symptoms:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF4C5D51))
          Spacer(modifier = Modifier.height(6.dp))
          LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            item {
              Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F6F2)),
                modifier = Modifier.clickable {
                  onPrefillExample(
                    "Paddy",
                    "plant_pathology",
                    "The leaves of my paddy crop are turning yellow with brown wavy borders.",
                    "Sangrur, Punjab"
                  )
                }
              ) {
                Text(
                  text = "🌾 Yellowing leaves & brown margins",
                  fontSize = 11.sp,
                  color = AgriGreenPrimary,
                  fontWeight = FontWeight.SemiBold,
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
              }
            }
            item {
              Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFDF4E5)),
                modifier = Modifier.clickable {
                  onPrefillExample(
                    "Cotton",
                    "entomology",
                    "Larvae bore into squares and small bolls with rosetted flowers.",
                    "Rajkot, Gujarat"
                  )
                }
              ) {
                Text(
                  text = "🐛 Bollworm / caterpillar bore",
                  fontSize = 11.sp,
                  color = Color(0xFFB45309),
                  fontWeight = FontWeight.SemiBold,
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
              }
            }
            item {
              Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                modifier = Modifier.clickable {
                  onPrefillExample(
                    "Tomato",
                    "plant_pathology",
                    "Upward curling, puckering of leaves with stunted inter-nodes.",
                    "Kolar, Karnataka"
                  )
                }
              ) {
                Text(
                  text = "🍅 Leaf curling & stunted shoot",
                  fontSize = 11.sp,
                  color = Color(0xFF1E40AF),
                  fontWeight = FontWeight.SemiBold,
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Crop Name
          OutlinedTextField(
            value = form.cropName,
            onValueChange = onCropNameChange,
            label = { Text("Crop Name") },
            placeholder = { Text("e.g. Paddy, Cotton, Tomato") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(10.dp)
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Category Dropdown
          ExposedDropdownMenuBox(
            expanded = categoryMenuExpanded,
            onExpandedChange = { categoryMenuExpanded = !categoryMenuExpanded }
          ) {
            OutlinedTextField(
              value = "${selectedCat?.iconGlyph ?: "🌿"} ${selectedCat?.name ?: "Select Domain"} (${selectedCat?.expertSpecialtyTag ?: ""})",
              onValueChange = {},
              readOnly = true,
              label = { Text("Specialization Domain") },
              trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryMenuExpanded) },
              modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
              shape = RoundedCornerShape(10.dp)
            )

            ExposedDropdownMenu(
              expanded = categoryMenuExpanded,
              onDismissRequest = { categoryMenuExpanded = false }
            ) {
              categories.forEach { cat ->
                DropdownMenuItem(
                  text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                      Text(text = cat.iconGlyph, fontSize = 16.sp)
                      Spacer(modifier = Modifier.width(8.dp))
                      Text(text = "${cat.name} (${cat.expertSpecialtyTag})", fontSize = 13.sp)
                    }
                  },
                  onClick = {
                    onCategoryChange(cat.id)
                    categoryMenuExpanded = false
                  }
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Symptoms description
          OutlinedTextField(
            value = form.description,
            onValueChange = onDescriptionChange,
            label = { Text("Observed Symptoms / Damage Details") },
            placeholder = { Text("Describe color changes, insect pests, spot shapes, or wilting pattern...") },
            modifier = Modifier
              .fillMaxWidth()
              .height(100.dp),
            shape = RoundedCornerShape(10.dp)
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Location
          OutlinedTextField(
            value = form.location,
            onValueChange = onLocationChange,
            label = { Text("Farm Location / District") },
            leadingIcon = { Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = AgriGreenPrimary) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(10.dp)
          )

          if (form.errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = form.errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
          }

          Spacer(modifier = Modifier.height(16.dp))

          Button(
            onClick = onRunDiagnosis,
            enabled = !isLoading,
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("run_ai_diagnosis_button"),
            colors = ButtonDefaults.buttonColors(containerColor = AgriGreenPrimary),
            shape = RoundedCornerShape(12.dp)
          ) {
            if (isLoading) {
              CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
              Spacer(modifier = Modifier.width(8.dp))
              Text("Analyzing with Agricultural AI...", fontWeight = FontWeight.Bold)
            } else {
              Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text("Run Instant AI Diagnosis", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
          }
        }
      }
    }

    // AI Diagnosis Result Card
    if (diagnosisResult != null) {
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("ai_diagnosis_result_card")
        ) {
          Column(modifier = Modifier.padding(18.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.Top
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "AI Diagnostic Report",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = AgriGreenPrimary
                )
                Text(
                  text = diagnosisResult.diseaseIdentified,
                  fontSize = 17.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF131D16)
                )
              }

              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(12.dp))
                  .background(if (diagnosisResult.severity == "Critical") Color(0xFFFEE2E2) else Color(0xFFFEF3C7))
                  .padding(horizontal = 10.dp, vertical = 4.dp)
              ) {
                Text(
                  text = "${diagnosisResult.severity} Severity",
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (diagnosisResult.severity == "Critical") Color(0xFFDC2626) else Color(0xFFB45309)
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Analysis
            Text(
              text = diagnosisResult.symptomsAnalysis,
              fontSize = 13.sp,
              color = Color(0xFF374151),
              lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = Color(0xFFEFF3EF))
            Spacer(modifier = Modifier.height(14.dp))

            // Chemical Treatment
            TreatmentItem(
              title = "Chemical Fungicide / Formulation:",
              body = diagnosisResult.chemicalRemedy,
              dosage = diagnosisResult.sprayDosage,
              isChemical = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Organic Treatment
            TreatmentItem(
              title = "Organic & Biological Control:",
              body = diagnosisResult.organicRemedy,
              dosage = "Spray every 7 days as preventative buffer",
              isChemical = false
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Preventative cultural
            Text(
              text = "Farm Management Note:",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = Color(0xFF4C5D51)
            )
            Text(
              text = diagnosisResult.preventativeMeasure,
              fontSize = 12.sp,
              color = Color(0xFF5A6B5F)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
              onClick = onConsultSpecialist,
              modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .testTag("consult_specialist_after_ai_button"),
              colors = ButtonDefaults.buttonColors(containerColor = AgriGreenPrimary),
              shape = RoundedCornerShape(10.dp)
            ) {
              Text("Consult a ${diagnosisResult.recommendedDomain} Specialist", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
          }
        }
      }
    }
  }
}

@Composable
private fun TreatmentItem(
  title: String,
  body: String,
  dosage: String,
  isChemical: Boolean
) {
  Card(
    shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isChemical) Color(0xFFF9FAFB) else Color(0xFFF0FDF4)
    ),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Text(
        text = title,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = if (isChemical) Color(0xFF1F2937) else Color(0xFF166534)
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = body,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF111827)
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = "Dosage: $dosage",
        fontSize = 11.sp,
        color = Color(0xFF4B5563)
      )
    }
  }
}

@Composable
private fun SpecialistsSection(
  categories: List<AgriculturalCategory>,
  experts: List<ExpertProfile>,
  activeFilter: String?,
  diagnosis: AiCropDiagnosis?,
  onFilterChange: (String?) -> Unit,
  onViewProfile: (ExpertProfile) -> Unit,
  onBookConsultation: (ExpertProfile) -> Unit
) {
  val filtered = if (activeFilter == null) experts else experts.filter { it.categoryId == activeFilter }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    if (diagnosis != null) {
      item {
        Card(
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = AgriGreenContainer),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(AgriGreenPrimary),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.Psychology, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "Specialists Matched for ${diagnosis.cropName}:",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = AgriGreenOnContainer
              )
              Text(
                text = "${diagnosis.diseaseIdentified} (${diagnosis.recommendedDomain})",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = AgriGreenPrimary
              )
            }
          }
        }
      }
    }

    // Filter Chips
    item {
      Column {
        Text(text = "Specialization Domain:", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF4C5D51))
        Spacer(modifier = Modifier.height(6.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          item {
            FilterChip(
              selected = activeFilter == null,
              onClick = { onFilterChange(null) },
              label = { Text("All Experts (${experts.size})") },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = AgriGreenPrimary,
                selectedLabelColor = Color.White
              )
            )
          }
          items(categories) { cat ->
            FilterChip(
              selected = activeFilter == cat.id,
              onClick = { onFilterChange(cat.id) },
              label = { Text("${cat.iconGlyph} ${cat.name}") },
              colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = AgriGreenPrimary,
                selectedLabelColor = Color.White
              )
            )
          }
        }
      }
    }

    items(filtered) { expert ->
      ExpertCard(
        expert = expert,
        onViewProfile = onViewProfile,
        onBookConsultation = onBookConsultation
      )
    }
  }
}

@Composable
private fun MandiWeatherToolsSection(
  mandiPrices: List<MandiCommodityPrice>,
  weather: WeatherAdvisory,
  fertilizerCalculation: FertilizerCalculation?,
  onCalculateFertilizer: (crop: String, acres: Float) -> Unit
) {
  var selectedCrop by remember { mutableStateOf("Paddy") }
  var acreageInput by remember { mutableStateOf("5") }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Weather Card
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(text = "Local Agricultural Weather", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF131D16))
              Text(text = weather.location, fontSize = 12.sp, color = Color(0xFF5A6B5F))
            }
            Text(text = weather.temperatureC, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = AgriGreenPrimary)
          }

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(text = "💧 Humidity: ${weather.humidityPercent}", fontSize = 12.sp, color = Color(0xFF4C5D51))
            Text(text = "💨 Wind: ${weather.windSpeedKmh}", fontSize = 12.sp, color = Color(0xFF4C5D51))
            Text(text = "🌧 Rain: ${weather.rainChance}", fontSize = 12.sp, color = Color(0xFF4C5D51))
          }

          Spacer(modifier = Modifier.height(10.dp))

          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .background(if (weather.isSprayingSuitable) Color(0xFFE8F5E9) else Color(0xFFFEF3C7))
              .padding(10.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = if (weather.isSprayingSuitable) Icons.Default.CheckCircle else Icons.Default.Warning,
                contentDescription = null,
                tint = if (weather.isSprayingSuitable) AgriGreenPrimary else AgriHarvestAmber,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = weather.sprayingRecommendation,
                fontSize = 11.sp,
                color = if (weather.isSprayingSuitable) Color(0xFF14532D) else Color(0xFF78350F),
                lineHeight = 15.sp
              )
            }
          }
        }
      }
    }

    // Mandi Prices Card
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.Store, contentDescription = null, tint = AgriGreenPrimary, modifier = Modifier.size(20.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(text = "Wholesale Mandi Rates", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF131D16))
            }
            Text(text = "APMC Verified", fontSize = 11.sp, color = Color(0xFF5A6B5F), fontWeight = FontWeight.Medium)
          }

          Spacer(modifier = Modifier.height(10.dp))

          mandiPrices.forEach { item ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(text = item.commodity, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF131D16))
                Text(text = "${item.mandiName}, ${item.state}", fontSize = 11.sp, color = Color(0xFF6B7280))
              }
              Column(horizontalAlignment = Alignment.End) {
                Text(text = "₹${item.modalPricePerQtl} / Qtl", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                Text(
                  text = item.priceTrend,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.SemiBold,
                  color = if (item.isPositiveTrend) Color(0xFF15803D) else Color(0xFFDC2626)
                )
              }
            }
            HorizontalDivider(color = Color(0xFFF3F4F6))
          }
        }
      }
    }

    // Fertilizer Calculator Card
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Calculate, contentDescription = null, tint = AgriHarvestAmber, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "Acreage Fertilizer & NPK Calculator", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF131D16))
          }
          Text(text = "Avoid over-spending and prevent soil acidity", fontSize = 12.sp, color = Color(0xFF5A6B5F))

          Spacer(modifier = Modifier.height(12.dp))

          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Paddy", "Wheat", "Cotton").forEach { crop ->
              FilterChip(
                selected = selectedCrop == crop,
                onClick = {
                  selectedCrop = crop
                  val acres = acreageInput.toFloatOrNull() ?: 1f
                  onCalculateFertilizer(crop, acres)
                },
                label = { Text(crop) }
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            OutlinedTextField(
              value = acreageInput,
              onValueChange = {
                acreageInput = it
                val acres = it.toFloatOrNull() ?: 1f
                onCalculateFertilizer(selectedCrop, acres)
              },
              label = { Text("Field Area (Acres)") },
              modifier = Modifier.weight(1f),
              shape = RoundedCornerShape(10.dp),
              singleLine = true
            )
          }

          if (fertilizerCalculation != null) {
            Spacer(modifier = Modifier.height(14.dp))
            Card(
              shape = RoundedCornerShape(12.dp),
              colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FBF9)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Text(
                  text = "Recommended for ${fertilizerCalculation.acreage} Acres ${fertilizerCalculation.cropName}:",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = AgriGreenPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text(text = "Urea (46% N):", fontSize = 13.sp)
                  Text(text = "${"%.1f".format(fertilizerCalculation.ureaBagsRequired)} bags (45kg)", fontWeight = FontWeight.Bold)
                }
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text(text = "DAP (18-46-0):", fontSize = 13.sp)
                  Text(text = "${"%.1f".format(fertilizerCalculation.dapBagsRequired)} bags (50kg)", fontWeight = FontWeight.Bold)
                }
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text(text = "MOP Potash (60% K):", fontSize = 13.sp)
                  Text(text = "${"%.1f".format(fertilizerCalculation.mopBagsRequired)} bags (50kg)", fontWeight = FontWeight.Bold)
                }
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween
                ) {
                  Text(text = "Zinc Sulphate:", fontSize = 13.sp)
                  Text(text = "${"%.1f".format(fertilizerCalculation.zincSulphateKg)} kg", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = Color(0xFFE5E7EB))
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                  text = fertilizerCalculation.applicationScheduleNotes,
                  fontSize = 11.sp,
                  color = Color(0xFF4B5563),
                  lineHeight = 15.sp
                )
              }
            }
          }
        }
      }
    }
  }
}

@Composable
private fun FarmerConsultationsSection(
  bookings: List<ConsultationBooking>,
  onOpenChat: (bookingId: String) -> Unit,
  onRateBooking: (booking: ConsultationBooking) -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Text(text = "My Doctor Consultations & Prescriptions", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF131D16))
      Text(text = "Verified tele-advisory sessions with digital prescriptions", fontSize = 12.sp, color = Color(0xFF5A6B5F))
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
            verticalAlignment = Alignment.Top
          ) {
            Column {
              Text(text = booking.expertName, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF131D16))
              Text(text = booking.expertTitle, fontSize = 12.sp, color = AgriGreenPrimary, fontWeight = FontWeight.Medium)
            }
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFE8F5E9))
                .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Text(text = booking.status, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = AgriGreenPrimary)
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(text = "Topic: ${booking.cropName} (${booking.categoryName}) • ${booking.selectedDate}, ${booking.selectedTime}", fontSize = 12.sp, color = Color(0xFF4C5D51))
          Text(text = "Ref: ${booking.transactionId} • Fee: ₹${booking.expertPrice}", fontSize = 11.sp, color = Color(0xFF6B7280))

          if (booking.prescription != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Card(
              shape = RoundedCornerShape(10.dp),
              colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
              border = CardDefaults.outlinedCardBorder().copy(width = 1.dp),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(imageVector = Icons.Default.Description, contentDescription = null, tint = AgriGreenPrimary, modifier = Modifier.size(16.dp))
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(text = "Signed Prescription Issued", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = AgriGreenPrimary)
                }
                Text(
                  text = booking.prescription.medicines.joinToString { "${it.name} (${it.dosage})" },
                  fontSize = 11.sp,
                  color = Color(0xFF1F2937)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = "✓ ${booking.paymentStatus}", fontSize = 11.sp, color = Color(0xFF15803D), fontWeight = FontWeight.Medium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              OutlinedButton(
                onClick = { onRateBooking(booking) },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.testTag("rate_booking_${booking.id}")
              ) {
                Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = AgriHarvestAmber, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Rate", fontSize = 12.sp, color = Color(0xFFB45309), fontWeight = FontWeight.Bold)
              }

              Button(
                onClick = { onOpenChat(booking.id) },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AgriGreenPrimary)
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
}

@Composable
private fun FarmerFarmProfileSection(
  isGoldMember: Boolean,
  onUpgradeToGold: () -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Kisan Gold Banner
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (isGoldMember) Color(0xFF0F766E) else Color(0xFFB45309)
        ),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = if (isGoldMember) "✓ AgriConnect Kisan Gold Active" else "Upgrade to Kisan Gold",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
              Text(
                text = "Unlimited AI Disease Scans • 20% Specialist Discount",
                fontSize = 12.sp,
                color = Color(0xFFFEF3C7)
              )
            }
            Icon(imageVector = Icons.Default.WorkspacePremium, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
          }

          if (!isGoldMember) {
            Spacer(modifier = Modifier.height(14.dp))
            Button(
              onClick = onUpgradeToGold,
              colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color(0xFFB45309)),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.fillMaxWidth().testTag("upgrade_gold_button")
            ) {
              Text("Activate Kisan Gold for ₹299 / Season", fontWeight = FontWeight.Bold, fontSize = 13.sp)
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
        Column(modifier = Modifier.padding(20.dp)) {
          Text(text = "Ramesh Patel", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF131D16))
          Text(text = "Registered Farmer • Sangrur District, Punjab", fontSize = 13.sp, color = Color(0xFF5A6B5F))

          Spacer(modifier = Modifier.height(14.dp))
          HorizontalDivider(color = Color(0xFFEDF3EE))
          Spacer(modifier = Modifier.height(12.dp))

          FarmDetailRow("Farm Acreage", "12 Acres")
          FarmDetailRow("Main Crops", "Paddy (Basmati), Wheat (HD-3086)")
          FarmDetailRow("Soil Classification", "Alluvial Sandy Loam")
          FarmDetailRow("Water Source", "Tubewell & Canal System")
          FarmDetailRow("Kisan Credit Card (KCC)", "Active • PNB Sangrur")
        }
      }
    }
  }
}

@Composable
private fun FarmDetailRow(label: String, value: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 5.dp),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(text = label, fontSize = 13.sp, color = Color(0xFF5A6B5F))
    Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF131D16))
  }
}
