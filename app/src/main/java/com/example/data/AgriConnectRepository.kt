package com.example.data

import com.example.model.AgriculturalCategory
import com.example.model.AiCropDiagnosis
import com.example.model.ChatMessage
import com.example.model.ConsultationBooking
import com.example.model.CropProblem
import com.example.model.DigitalPrescription
import com.example.model.ExpertProfile
import com.example.model.FertilizerCalculation
import com.example.model.MandiCommodityPrice
import com.example.model.PrescribedMedicine
import com.example.model.UserRole
import com.example.model.WeatherAdvisory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class AgriConnectRepository {

  val categories: List<AgriculturalCategory> = listOf(
    AgriculturalCategory(
      id = "plant_pathology",
      name = "Plant Pathology",
      expertSpecialtyTag = "Plant Disease",
      description = "Diagnosis of crop diseases, fungal infections, bacterial wilt, and leaf spot remedies.",
      iconGlyph = "🌿"
    ),
    AgriculturalCategory(
      id = "entomology",
      name = "Entomology",
      expertSpecialtyTag = "Pest Attack",
      description = "Pest management, insect control, stem borers, aphid prevention, and biopesticides.",
      iconGlyph = "🐛"
    ),
    AgriculturalCategory(
      id = "agronomy",
      name = "Agronomy",
      expertSpecialtyTag = "Crop Yield & Cultivation",
      description = "Crop cultivation planning, seed rate, sowing schedules, and field management.",
      iconGlyph = "🌾"
    ),
    AgriculturalCategory(
      id = "soil_science",
      name = "Soil Science",
      expertSpecialtyTag = "Soil & Fertilizer",
      description = "Soil nutrient testing, NPK fertilizer recommendations, salinity, and pH balancing.",
      iconGlyph = "🧪"
    ),
    AgriculturalCategory(
      id = "horticulture",
      name = "Horticulture",
      expertSpecialtyTag = "Fruits & Vegetables",
      description = "Vegetables, orchards, flowering crops, greenhouse cultivation, and pruning guidance.",
      iconGlyph = "🍎"
    ),
    AgriculturalCategory(
      id = "agri_engineering",
      name = "Agricultural Engineering",
      expertSpecialtyTag = "Farm Machinery & Irrigation",
      description = "Drip irrigation design, solar water pumps, farm machinery, and automation.",
      iconGlyph = "🚜"
    ),
    AgriculturalCategory(
      id = "dairy",
      name = "Dairy",
      expertSpecialtyTag = "Cattle & Milk Production",
      description = "Cattle nutrition, mastitis management, breeding cycles, and milk yield enhancement.",
      iconGlyph = "🐄"
    ),
    AgriculturalCategory(
      id = "poultry",
      name = "Poultry",
      expertSpecialtyTag = "Broiler & Layer Farming",
      description = "Poultry disease prevention, vaccination schedules, feed formulation, and housing.",
      iconGlyph = "🐓"
    ),
    AgriculturalCategory(
      id = "fisheries",
      name = "Fisheries",
      expertSpecialtyTag = "Aquaculture & Fish Pond",
      description = "Fish pond water quality, fingerling health, aerator setups, and feed management.",
      iconGlyph = "🐟"
    )
  )

  private val _experts = MutableStateFlow<List<ExpertProfile>>(
    listOf(
      ExpertProfile(
        id = "exp_1",
        name = "Dr. Rahul Kumar",
        title = "Plant Pathology Expert",
        expertise = "Plant Pathology",
        categoryId = "plant_pathology",
        experienceYears = 8,
        rating = 4.8f,
        reviewCount = 143,
        priceInRupees = 300,
        availableTime = "Monday–Friday, 5 PM–8 PM",
        availableSlots = listOf(
          "Today, 5:30 PM",
          "Today, 6:00 PM",
          "Tomorrow, 5:00 PM",
          "Tomorrow, 6:30 PM",
          "Friday, 7:00 PM"
        ),
        location = "Punjab Agricultural University, Ludhiana",
        bio = "Chief scientist specializing in cereal crop fungal infections, bacterial leaf blight, and bio-fungicide treatment protocols.",
        qualifications = "Ph.D. in Plant Pathology, ICAR Certified",
        contactEmail = "rahul.kumar@agriconnect.org",
        totalEarningsRupees = 36465,
        pendingPayoutRupees = 4080,
        consultationsCount = 143
      ),
      ExpertProfile(
        id = "exp_2",
        name = "Dr. Priya Sharma",
        title = "Agronomy Specialist",
        expertise = "Agronomy",
        categoryId = "agronomy",
        experienceYears = 10,
        rating = 4.9f,
        reviewCount = 210,
        priceInRupees = 350,
        availableTime = "Mon, Wed, Fri, 4 PM–7 PM",
        availableSlots = listOf(
          "Today, 4:00 PM",
          "Tomorrow, 4:30 PM",
          "Friday, 5:00 PM"
        ),
        location = "IARI, New Delhi",
        bio = "Lead Agronomist in high-yield cropping patterns, zero-tillage sowing, water-efficient rice cultivation, and seedbed preparation.",
        qualifications = "Ph.D. in Agronomy, Senior Agronomist",
        contactEmail = "priya.sharma@agriconnect.org",
        totalEarningsRupees = 62475,
        pendingPayoutRupees = 5950,
        consultationsCount = 210
      ),
      ExpertProfile(
        id = "exp_3",
        name = "Dr. Ramesh Patel",
        title = "Entomology & Pest Specialist",
        expertise = "Entomology",
        categoryId = "entomology",
        experienceYears = 7,
        rating = 4.8f,
        reviewCount = 98,
        priceInRupees = 250,
        availableTime = "Monday–Saturday, 6 PM–9 PM",
        availableSlots = listOf(
          "Today, 6:30 PM",
          "Tomorrow, 7:00 PM",
          "Friday, 8:00 PM"
        ),
        location = "Anand Agricultural University, Gujarat",
        bio = "Expert in integrated pest management (IPM), bollworm control in cotton, and non-toxic pheromone trapping.",
        qualifications = "M.Sc. & Ph.D. in Agricultural Entomology",
        contactEmail = "ramesh.patel@agriconnect.org",
        totalEarningsRupees = 20825,
        pendingPayoutRupees = 2125,
        consultationsCount = 98
      ),
      ExpertProfile(
        id = "exp_4",
        name = "Dr. Ananya Sen",
        title = "Soil Science & Nutritionist",
        expertise = "Soil Science",
        categoryId = "soil_science",
        experienceYears = 6,
        rating = 4.6f,
        reviewCount = 84,
        priceInRupees = 280,
        availableTime = "Tue, Thu, Sat, 3 PM–6 PM",
        availableSlots = listOf(
          "Today, 3:30 PM",
          "Tomorrow, 4:00 PM",
          "Saturday, 5:00 PM"
        ),
        location = "Bidhan Chandra Krishi Viswavidyalaya, West Bengal",
        bio = "Advising farmers on soil micronutrient correction (Zinc, Boron), organic compost ratios, and alkaline soil treatment.",
        qualifications = "Ph.D. in Soil Science & Chemistry",
        contactEmail = "ananya.sen@agriconnect.org",
        totalEarningsRupees = 19992,
        pendingPayoutRupees = 2380,
        consultationsCount = 84
      ),
      ExpertProfile(
        id = "exp_5",
        name = "Prof. Vikram Singh",
        title = "Horticulture & Fruit Crops",
        expertise = "Horticulture",
        categoryId = "horticulture",
        experienceYears = 12,
        rating = 4.9f,
        reviewCount = 175,
        priceInRupees = 400,
        availableTime = "Monday–Friday, 5 PM–8 PM",
        availableSlots = listOf(
          "Today, 5:00 PM",
          "Tomorrow, 6:00 PM",
          "Friday, 7:00 PM"
        ),
        location = "Dr. YSR Horticultural University, Andhra Pradesh",
        bio = "Pioneer in mango, citrus, and tomato polyhouse farming with canopy management and post-harvest preservation.",
        qualifications = "Ph.D. in Pomology & Vegetable Science",
        contactEmail = "vikram.singh@agriconnect.org",
        totalEarningsRupees = 59500,
        pendingPayoutRupees = 6800,
        consultationsCount = 175
      )
    )
  )
  val experts: StateFlow<List<ExpertProfile>> = _experts.asStateFlow()

  private val _problems = MutableStateFlow<List<CropProblem>>(
    listOf(
      CropProblem(
        id = "prob_init_1",
        cropName = "Paddy",
        categoryId = "plant_pathology",
        categoryName = "Plant Disease",
        description = "The leaves of my paddy crop are turning yellow with brown tips along the borders.",
        location = "Punjab (Sangrur District)",
        submittedAt = "10 Oct, 09:30 AM",
        matchedExpertIds = listOf("exp_1"),
        aiDiagnosis = AiCropDiagnosis(
          cropName = "Paddy",
          diseaseIdentified = "Bacterial Leaf Blight & Zinc Deficiency",
          severity = "Moderate",
          confidenceScore = 94,
          symptomsAnalysis = "Yellowing along leaf edges indicates bacterial vascular blockage combined with reduced micronutrient uptake.",
          chemicalRemedy = "Streptocycline (6g/60L) + Copper Oxychloride 50% WP (500g/acre)",
          organicRemedy = "Cow dung supernatant 5% + Pseudomonas fluorescens 1%",
          sprayDosage = "1.5g Copper Oxychloride + 0.1g Streptocycline per liter water",
          recommendedDomain = "Plant Pathology",
          preventativeMeasure = "Drain excess standing water for 36 hours; split nitrogen doses."
        )
      )
    )
  )
  val problems: StateFlow<List<CropProblem>> = _problems.asStateFlow()

  private val _bookings = MutableStateFlow<List<ConsultationBooking>>(
    listOf(
      ConsultationBooking(
        id = "book_active_1",
        problemId = "prob_init_1",
        expertId = "exp_1",
        expertName = "Dr. Rahul Kumar",
        expertTitle = "Plant Pathology Expert",
        expertPrice = 300,
        platformFee = 45,
        expertPayout = 255,
        cropName = "Paddy",
        categoryName = "Plant Disease",
        selectedDate = "10 October",
        selectedTime = "6:00 PM",
        status = "Active Consultation",
        paymentStatus = "Verified via UPI",
        transactionId = "TXN_AGRI_948210",
        farmerName = "Ramesh Patel",
        prescription = DigitalPrescription(
          id = "rx_init_1",
          bookingId = "book_active_1",
          expertName = "Dr. Rahul Kumar",
          cropName = "Paddy",
          medicines = listOf(
            PrescribedMedicine(
              name = "Propiconazole 25% EC (Tilt)",
              dosage = "1.0 ml per Liter water (200 ml/acre)",
              applicationMethod = "Foliar Spray during evening",
              targetPestOrDisease = "Bacterial Leaf Blight / Blast"
            ),
            PrescribedMedicine(
              name = "Zinc Sulphate 33% (Chelated)",
              dosage = "1.5 g per Liter water",
              applicationMethod = "Tank mix spray",
              targetPestOrDisease = "Khaira / Zinc Deficiency"
            )
          ),
          generalAdvice = "Maintain alternate wetting and drying. Avoid urea application for 6 days until lesions turn brown and stop spreading.",
          withholdingPeriodDays = 14,
          issuedDate = "10 Oct, 06:15 PM"
        )
      )
    )
  )
  val bookings: StateFlow<List<ConsultationBooking>> = _bookings.asStateFlow()

  private val _messages = MutableStateFlow<Map<String, List<ChatMessage>>>(
    mapOf(
      "book_active_1" to listOf(
        ChatMessage(
          id = "msg_1",
          bookingId = "book_active_1",
          senderRole = UserRole.FARMER,
          senderName = "Ramesh Patel (Farmer)",
          text = "Namaste Dr. Rahul. The leaves of my paddy crop are turning yellow with brown tips along the borders.",
          timestampFormatted = "10:02 AM"
        ),
        ChatMessage(
          id = "msg_2",
          bookingId = "book_active_1",
          senderRole = UserRole.EXPERT,
          senderName = "Dr. Rahul Kumar",
          text = "Namaste Ramesh ji. I examined your field symptoms. This is early Bacterial Leaf Blight coupled with slight Zinc stress. I have generated your digital prescription below.",
          timestampFormatted = "10:05 AM",
          prescriptionAttachment = DigitalPrescription(
            id = "rx_init_1",
            bookingId = "book_active_1",
            expertName = "Dr. Rahul Kumar",
            cropName = "Paddy",
            medicines = listOf(
              PrescribedMedicine(
                name = "Propiconazole 25% EC",
                dosage = "1.0 ml / Liter",
                applicationMethod = "Foliar Spray at 4-6 PM",
                targetPestOrDisease = "Bacterial Leaf Blight"
              )
            ),
            generalAdvice = "Drain water for 24-36 hours and spray in cool evening hours.",
            withholdingPeriodDays = 14,
            issuedDate = "10 Oct, 10:06 AM"
          )
        )
      )
    )
  )
  val messages: StateFlow<Map<String, List<ChatMessage>>> = _messages.asStateFlow()

  // Mandi Wholesale APMC Market Prices
  val mandiPrices: List<MandiCommodityPrice> = listOf(
    MandiCommodityPrice("Paddy (Basmati)", "Khanna Mandi", "Punjab", 3850, "+₹65/Qtl", true, "Today"),
    MandiCommodityPrice("Wheat (HD-3086)", "Karnal Mandi", "Haryana", 2420, "+₹30/Qtl", true, "Today"),
    MandiCommodityPrice("Cotton (Medium)", "Rajkot Mandi", "Gujarat", 7250, "+₹110/Qtl", true, "Today"),
    MandiCommodityPrice("Mustard (Sarson)", "Alwar Mandi", "Rajasthan", 5620, "-₹25/Qtl", false, "Today"),
    MandiCommodityPrice("Tomato (Hybrid)", "Kolar Mandi", "Karnataka", 1450, "+₹80/Qtl", true, "Today"),
    MandiCommodityPrice("Onion (Nashik)", "Lasalgaon Mandi", "Maharashtra", 2180, "-₹40/Qtl", false, "Today")
  )

  // Agricultural Weather Advisory
  val weatherAdvisory: WeatherAdvisory = WeatherAdvisory(
    location = "Ludhiana & Sangrur, Punjab",
    temperatureC = "29°C",
    humidityPercent = "62%",
    condition = "Partly Cloudy • Gentle Breeze",
    rainChance = "15% (No heavy rain expected next 36h)",
    windSpeedKmh = "8 km/h",
    sprayingRecommendation = "Optimal conditions for foliar spray today between 4:00 PM – 6:30 PM. Low wind minimizes drift.",
    isSprayingSuitable = true
  )

  // Platform Business Metrics (For App Builder / Admin)
  private val _platformCommissionEarnings = MutableStateFlow(14850)
  val platformCommissionEarnings: StateFlow<Int> = _platformCommissionEarnings.asStateFlow()

  private val _isKisanGoldMember = MutableStateFlow(false)
  val isKisanGoldMember: StateFlow<Boolean> = _isKisanGoldMember.asStateFlow()

  fun submitProblemWithAi(
    cropName: String,
    categoryId: String,
    description: String,
    location: String,
    aiDiagnosis: AiCropDiagnosis?
  ): CropProblem {
    val category = categories.find { it.id == categoryId }
    val categoryName = category?.name ?: "General Agriculture"
    val newId = "prob_" + UUID.randomUUID().toString().take(8)
    val now = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date())

    val matchingExpertIds = _experts.value
      .filter { it.categoryId == categoryId }
      .map { it.id }

    val newProblem = CropProblem(
      id = newId,
      cropName = cropName.trim(),
      categoryId = categoryId,
      categoryName = categoryName,
      description = description.trim(),
      location = location.trim().ifEmpty { "Field Location" },
      submittedAt = now,
      matchedExpertIds = matchingExpertIds,
      aiDiagnosis = aiDiagnosis
    )

    _problems.update { listOf(newProblem) + it }
    return newProblem
  }

  fun createBooking(
    problemId: String?,
    expertId: String,
    slotString: String,
    cropName: String,
    categoryName: String,
    paymentMethod: String = "UPI (Instant Transfer)"
  ): ConsultationBooking {
    val expert = _experts.value.find { it.id == expertId } ?: _experts.value.first()
    val parts = slotString.split(",")
    val datePart = parts.getOrNull(0)?.trim() ?: "Today"
    val timePart = parts.getOrNull(1)?.trim() ?: "6:00 PM"

    val bookingId = "book_" + UUID.randomUUID().toString().take(8)
    val fee = expert.priceInRupees
    val platformCut = (fee * 0.15).toInt()
    val expertShare = fee - platformCut

    val newBooking = ConsultationBooking(
      id = bookingId,
      problemId = problemId,
      expertId = expert.id,
      expertName = expert.name,
      expertTitle = expert.title,
      expertPrice = fee,
      platformFee = platformCut,
      expertPayout = expertShare,
      cropName = cropName.ifEmpty { "Crop Consultation" },
      categoryName = categoryName.ifEmpty { expert.expertise },
      selectedDate = datePart,
      selectedTime = timePart,
      status = "Active Consultation",
      paymentStatus = "Verified via $paymentMethod",
      transactionId = "TXN_AGRI_" + UUID.randomUUID().toString().take(8).uppercase(),
      farmerName = "Ramesh Patel (Farmer)"
    )

    _bookings.update { listOf(newBooking) + it }

    // Update expert pending payout
    _experts.update { list ->
      list.map { exp ->
        if (exp.id == expert.id) {
          exp.copy(
            pendingPayoutRupees = exp.pendingPayoutRupees + expertShare,
            totalEarningsRupees = exp.totalEarningsRupees + expertShare,
            consultationsCount = exp.consultationsCount + 1
          )
        } else exp
      }
    }

    // Update platform revenue
    _platformCommissionEarnings.update { it + platformCut }

    // Initialize consultation chat
    val timeNow = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
    val welcomeMsg = ChatMessage(
      id = "msg_" + UUID.randomUUID().toString().take(6),
      bookingId = bookingId,
      senderRole = UserRole.EXPERT,
      senderName = expert.name,
      text = "Namaste! I am ${expert.name}. I received your consultation request regarding $cropName ($categoryName). Please describe how long you have seen these symptoms, or attach photos.",
      timestampFormatted = timeNow
    )
    _messages.update { current ->
      current + (bookingId to listOf(welcomeMsg))
    }

    return newBooking
  }

  fun sendMessage(
    bookingId: String,
    senderRole: UserRole,
    senderName: String,
    text: String,
    prescriptionAttachment: DigitalPrescription? = null
  ) {
    val timeNow = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
    val newMsg = ChatMessage(
      id = "msg_" + UUID.randomUUID().toString().take(8),
      bookingId = bookingId,
      senderRole = senderRole,
      senderName = senderName,
      text = text,
      timestampFormatted = timeNow,
      prescriptionAttachment = prescriptionAttachment
    )

    _messages.update { current ->
      val list = current[bookingId] ?: emptyList()
      current + (bookingId to (list + newMsg))
    }
  }

  fun issuePrescription(
    bookingId: String,
    expertName: String,
    cropName: String,
    medicines: List<PrescribedMedicine>,
    advice: String,
    withholdingDays: Int
  ): DigitalPrescription {
    val dateNow = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date())
    val rx = DigitalPrescription(
      id = "rx_" + UUID.randomUUID().toString().take(6),
      bookingId = bookingId,
      expertName = expertName,
      cropName = cropName,
      medicines = medicines,
      generalAdvice = advice,
      withholdingPeriodDays = withholdingDays,
      issuedDate = dateNow
    )

    // Attach to booking
    _bookings.update { list ->
      list.map { b ->
        if (b.id == bookingId) b.copy(prescription = rx) else b
      }
    }

    // Also send into chat
    sendMessage(
      bookingId = bookingId,
      senderRole = UserRole.EXPERT,
      senderName = expertName,
      text = "I have officially generated and signed your Digital Treatment Prescription for $cropName. Please follow the dosage carefully.",
      prescriptionAttachment = rx
    )

    return rx
  }

  fun rateExpert(
    expertId: String,
    stars: Int,
    bookingId: String? = null
  ): ExpertProfile? {
    var updatedProfile: ExpertProfile? = null
    _experts.update { list ->
      list.map { expert ->
        if (expert.id == expertId) {
          val newReviewCount = expert.reviewCount + 1
          val totalScore = (expert.rating * expert.reviewCount) + stars
          val newRating = (Math.round((totalScore / newReviewCount) * 10.0) / 10.0).toFloat()
          val updated = expert.copy(
            rating = newRating,
            reviewCount = newReviewCount
          )
          updatedProfile = updated
          updated
        } else {
          expert
        }
      }
    }

    if (bookingId != null) {
      _bookings.update { list ->
        list.map { b ->
          if (b.id == bookingId) {
            b.copy(status = "Completed (Rated ⭐ $stars)")
          } else {
            b
          }
        }
      }
    }
    return updatedProfile
  }

  fun withdrawExpertEarnings(expertId: String, amount: Int): Boolean {
    var success = false
    _experts.update { list ->
      list.map { exp ->
        if (exp.id == expertId && exp.pendingPayoutRupees >= amount) {
          success = true
          exp.copy(pendingPayoutRupees = exp.pendingPayoutRupees - amount)
        } else exp
      }
    }
    return success
  }

  fun upgradeToKisanGold() {
    _isKisanGoldMember.value = true
    _platformCommissionEarnings.update { it + 299 } // Membership fee revenue to platform
  }

  fun calculateFertilizer(crop: String, acres: Float): FertilizerCalculation {
    val a = if (acres <= 0f) 1f else acres
    val lower = crop.lowercase()
    return when {
      lower.contains("paddy") || lower.contains("rice") -> {
        FertilizerCalculation(
          cropName = "Paddy (Rice)",
          acreage = a,
          ureaBagsRequired = 2.6f * a,
          dapBagsRequired = 1.1f * a,
          mopBagsRequired = 0.8f * a,
          zincSulphateKg = 10f * a,
          applicationScheduleNotes = "Basal: All DAP + All MOP + 1/3 Urea. Active Tillering (21 days): 1/3 Urea + Zinc. Panicle Initiation (45 days): 1/3 Urea."
        )
      }
      lower.contains("wheat") -> {
        FertilizerCalculation(
          cropName = "Wheat",
          acreage = a,
          ureaBagsRequired = 2.8f * a,
          dapBagsRequired = 1.2f * a,
          mopBagsRequired = 0.6f * a,
          zincSulphateKg = 8f * a,
          applicationScheduleNotes = "Basal: Full DAP + Full MOP + 1/2 Urea. 1st Irrigation (CRI stage 21 days): Remaining 1/2 Urea."
        )
      }
      lower.contains("cotton") -> {
        FertilizerCalculation(
          cropName = "Cotton (Bt)",
          acreage = a,
          ureaBagsRequired = 3.2f * a,
          dapBagsRequired = 1.4f * a,
          mopBagsRequired = 1.0f * a,
          zincSulphateKg = 10f * a,
          applicationScheduleNotes = "Split Urea in 3 equal doses: at squaring, flowering, and boll development stages."
        )
      }
      else -> {
        FertilizerCalculation(
          cropName = crop.ifEmpty { "Field Crop" },
          acreage = a,
          ureaBagsRequired = 2.2f * a,
          dapBagsRequired = 1.0f * a,
          mopBagsRequired = 0.7f * a,
          zincSulphateKg = 7.5f * a,
          applicationScheduleNotes = "Basal application of DAP and MOP before final ploughing; split Urea after weeding."
        )
      }
    }
  }

  fun updateExpertProfile(
    expertId: String,
    priceInRupees: Int,
    availableTime: String,
    bio: String
  ) {
    _experts.update { list ->
      list.map { expert ->
        if (expert.id == expertId) {
          expert.copy(
            priceInRupees = priceInRupees,
            availableTime = availableTime,
            bio = bio
          )
        } else {
          expert
        }
      }
    }
  }
}
