package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AgriConnectRepository
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
import com.example.service.GeminiCropDoctorService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class Screen {
  object Home : Screen()
  data class FarmerDashboard(val selectedTab: FarmerTab = FarmerTab.AI_DIAGNOSIS) : Screen()
  data class ExpertDashboard(val selectedTab: ExpertTab = ExpertTab.EARNINGS_WALLET) : Screen()
  object PlatformAdmin : Screen()
  data class ExpertDetail(val expertId: String, val fromProblemId: String? = null) : Screen()
  data class BookingConfirmation(val booking: ConsultationBooking) : Screen()
  data class ChatScreen(val bookingId: String) : Screen()
}

enum class FarmerTab(val label: String) {
  AI_DIAGNOSIS("AI Crop Doctor"),
  CONSULT_EXPERTS("Specialists"),
  MANDI_WEATHER("Mandi & Weather"),
  MY_BOOKINGS("Consultations"),
  PROFILE("My Farm")
}

enum class ExpertTab(val label: String) {
  EARNINGS_WALLET("Earnings & Payouts"),
  BOOKING_SESSIONS("Farmer Sessions"),
  PROFILE_SCHEDULE("Rates & Bio")
}

data class ProblemFormState(
  val cropName: String = "Paddy",
  val selectedCategoryId: String = "plant_pathology",
  val description: String = "",
  val location: String = "Punjab (Sangrur District)",
  val errorMessage: String? = null
)

class AgriConnectViewModel(
  private val repository: AgriConnectRepository = AgriConnectRepository(),
  private val geminiService: GeminiCropDoctorService = GeminiCropDoctorService()
) : ViewModel() {

  val categories: List<AgriculturalCategory> = repository.categories
  val experts: StateFlow<List<ExpertProfile>> = repository.experts
  val problems: StateFlow<List<CropProblem>> = repository.problems
  val bookings: StateFlow<List<ConsultationBooking>> = repository.bookings
  val allMessages: StateFlow<Map<String, List<ChatMessage>>> = repository.messages
  val mandiPrices: List<MandiCommodityPrice> = repository.mandiPrices
  val weatherAdvisory: WeatherAdvisory = repository.weatherAdvisory
  val platformCommissionEarnings: StateFlow<Int> = repository.platformCommissionEarnings
  val isKisanGoldMember: StateFlow<Boolean> = repository.isKisanGoldMember

  private val _currentRole = MutableStateFlow(UserRole.FARMER)
  val currentRole: StateFlow<UserRole> = _currentRole.asStateFlow()

  private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
  val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

  private val screenBackStack = mutableListOf<Screen>()

  private val _problemForm = MutableStateFlow(ProblemFormState())
  val problemForm: StateFlow<ProblemFormState> = _problemForm.asStateFlow()

  private val _activeCategoryFilter = MutableStateFlow<String?>("plant_pathology")
  val activeCategoryFilter: StateFlow<String?> = _activeCategoryFilter.asStateFlow()

  private val _lastSubmittedProblem = MutableStateFlow<CropProblem?>(null)
  val lastSubmittedProblem: StateFlow<CropProblem?> = _lastSubmittedProblem.asStateFlow()

  // AI Diagnosis state
  private val _isDiagnosingWithAi = MutableStateFlow(false)
  val isDiagnosingWithAi: StateFlow<Boolean> = _isDiagnosingWithAi.asStateFlow()

  private val _latestAiDiagnosis = MutableStateFlow<AiCropDiagnosis?>(null)
  val latestAiDiagnosis: StateFlow<AiCropDiagnosis?> = _latestAiDiagnosis.asStateFlow()

  // Fertilizer calculator state
  private val _fertilizerCalculation = MutableStateFlow<FertilizerCalculation?>(
    repository.calculateFertilizer("Paddy", 4f)
  )
  val fertilizerCalculation: StateFlow<FertilizerCalculation?> = _fertilizerCalculation.asStateFlow()

  // Selected expert for booking modal
  private val _selectedExpertForBooking = MutableStateFlow<ExpertProfile?>(null)
  val selectedExpertForBooking: StateFlow<ExpertProfile?> = _selectedExpertForBooking.asStateFlow()

  // Confirmed booking for receipt modal
  private val _bookingSuccessReceipt = MutableStateFlow<ConsultationBooking?>(null)
  val bookingSuccessReceipt: StateFlow<ConsultationBooking?> = _bookingSuccessReceipt.asStateFlow()

  // Rating dialog state
  private val _expertToRate = MutableStateFlow<Triple<ExpertProfile, String, String?>?>(null)
  val expertToRate: StateFlow<Triple<ExpertProfile, String, String?>?> = _expertToRate.asStateFlow()

  // Prescription modal state (for expert)
  private val _showPrescriptionModalForBooking = MutableStateFlow<ConsultationBooking?>(null)
  val showPrescriptionModalForBooking: StateFlow<ConsultationBooking?> = _showPrescriptionModalForBooking.asStateFlow()

  init {
    _lastSubmittedProblem.value = repository.problems.value.firstOrNull()
    _latestAiDiagnosis.value = repository.problems.value.firstOrNull()?.aiDiagnosis
  }

  fun navigateTo(screen: Screen) {
    screenBackStack.add(_currentScreen.value)
    _currentScreen.value = screen
  }

  fun navigateBack(): Boolean {
    if (screenBackStack.isNotEmpty()) {
      _currentScreen.value = screenBackStack.removeAt(screenBackStack.size - 1)
      return true
    }
    if (_currentScreen.value !is Screen.Home) {
      _currentScreen.value = Screen.Home
      return true
    }
    return false
  }

  fun switchRole(role: UserRole) {
    _currentRole.value = role
    when (role) {
      UserRole.FARMER -> _currentScreen.value = Screen.FarmerDashboard(FarmerTab.AI_DIAGNOSIS)
      UserRole.EXPERT -> _currentScreen.value = Screen.ExpertDashboard(ExpertTab.EARNINGS_WALLET)
      UserRole.PLATFORM_OWNER -> _currentScreen.value = Screen.PlatformAdmin
    }
  }

  fun updateCropName(name: String) {
    _problemForm.value = _problemForm.value.copy(cropName = name, errorMessage = null)
  }

  fun updateSelectedCategory(catId: String) {
    _problemForm.value = _problemForm.value.copy(selectedCategoryId = catId)
  }

  fun updateDescription(desc: String) {
    _problemForm.value = _problemForm.value.copy(description = desc, errorMessage = null)
  }

  fun updateLocation(loc: String) {
    _problemForm.value = _problemForm.value.copy(location = loc)
  }

  fun prefillExampleProblem(crop: String, categoryId: String, problemText: String, loc: String) {
    _problemForm.value = ProblemFormState(
      cropName = crop,
      selectedCategoryId = categoryId,
      description = problemText,
      location = loc,
      errorMessage = null
    )
  }

  fun runAiDiagnosis() {
    val form = _problemForm.value
    if (form.cropName.isBlank()) {
      _problemForm.value = form.copy(errorMessage = "Please enter your crop name")
      return
    }
    if (form.description.isBlank()) {
      _problemForm.value = form.copy(errorMessage = "Please describe the symptoms (e.g. yellow leaves, brown spots)")
      return
    }

    _isDiagnosingWithAi.value = true

    viewModelScope.launch {
      val categoryName = categories.find { it.id == form.selectedCategoryId }?.name ?: "Plant Pathology"
      val diagnosis = geminiService.diagnoseCropProblem(
        cropName = form.cropName,
        symptoms = form.description,
        categoryName = categoryName,
        location = form.location
      )

      val submitted = repository.submitProblemWithAi(
        cropName = form.cropName,
        categoryId = form.selectedCategoryId,
        description = form.description,
        location = form.location,
        aiDiagnosis = diagnosis
      )

      _latestAiDiagnosis.value = diagnosis
      _lastSubmittedProblem.value = submitted
      _activeCategoryFilter.value = form.selectedCategoryId
      _isDiagnosingWithAi.value = false
    }
  }

  fun setCategoryFilter(categoryId: String?) {
    _activeCategoryFilter.value = categoryId
  }

  fun calculateFertilizer(crop: String, acres: Float) {
    _fertilizerCalculation.value = repository.calculateFertilizer(crop, acres)
  }

  fun openBookingModal(expert: ExpertProfile) {
    _selectedExpertForBooking.value = expert
  }

  fun dismissBookingModal() {
    _selectedExpertForBooking.value = null
  }

  fun confirmBooking(expert: ExpertProfile, slotString: String, paymentMethod: String = "UPI (Instant)") {
    val crop = _lastSubmittedProblem.value?.cropName ?: "Paddy"
    val category = _lastSubmittedProblem.value?.categoryName ?: expert.expertise
    val problemId = _lastSubmittedProblem.value?.id

    val booking = repository.createBooking(
      problemId = problemId,
      expertId = expert.id,
      slotString = slotString,
      cropName = crop,
      categoryName = category,
      paymentMethod = paymentMethod
    )

    _selectedExpertForBooking.value = null
    _bookingSuccessReceipt.value = booking
  }

  fun dismissBookingReceipt() {
    _bookingSuccessReceipt.value = null
  }

  fun openRatingDialog(expert: ExpertProfile, cropTopic: String = "Paddy", bookingId: String? = null) {
    _expertToRate.value = Triple(expert, cropTopic, bookingId)
  }

  fun dismissRatingDialog() {
    _expertToRate.value = null
  }

  fun submitRating(expertId: String, stars: Int, bookingId: String? = null) {
    repository.rateExpert(expertId, stars, bookingId)
    _expertToRate.value = null
  }

  fun openChatForBooking(bookingId: String) {
    _bookingSuccessReceipt.value = null
    navigateTo(Screen.ChatScreen(bookingId))
  }

  fun sendChatMessage(bookingId: String, text: String, senderRole: UserRole? = null) {
    if (text.isBlank()) return
    val role = senderRole ?: _currentRole.value
    val senderName = if (role == UserRole.FARMER) "Ramesh Patel (Farmer)" else "Dr. Rahul Kumar"
    repository.sendMessage(bookingId, role, senderName, text.trim())

    if (role == UserRole.FARMER) {
      viewModelScope.launch {
        delay(1200)
        val expertReply = generateExpertAdviceReply(text.trim())
        repository.sendMessage(bookingId, UserRole.EXPERT, "Dr. Rahul Kumar", expertReply)
      }
    }
  }

  private fun generateExpertAdviceReply(query: String): String {
    val lower = query.lowercase()
    return when {
      lower.contains("yellow") || lower.contains("leaf") || lower.contains("leaves") ->
        "I analyzed the yellowing pattern. Apply Streptocycline (6g/60L water) + Copper Oxychloride 50% WP (500g/acre). Drain standing water for 48 hours to dry bacterial lesions."
      lower.contains("photo") || lower.contains("picture") || lower.contains("image") ->
        "Thank you for the high-res photo. The diamond-shaped lesions confirm early blast fungus. Spray Tricyclazole 75% WP @ 120g/acre immediately."
      lower.contains("water") || lower.contains("irrigation") ->
        "Adopt alternate wetting and drying (AWD) rather than stagnant standing water. This encourages stronger root respiration and cuts fungal sporulation."
      lower.contains("fertilizer") || lower.contains("npk") || lower.contains("urea") ->
        "Do not apply urea right now as it softens vegetative tissue, accelerating disease spread. Wait 5 days, then apply Muriate of Potash (MOP) to harden cell walls."
      else ->
        "Prescription recorded. Keep field edges clear of wild weeds and monitor tillering. I have also issued your digital prescription."
    }
  }

  fun openPrescriptionModal(booking: ConsultationBooking) {
    _showPrescriptionModalForBooking.value = booking
  }

  fun dismissPrescriptionModal() {
    _showPrescriptionModalForBooking.value = null
  }

  fun issuePrescription(
    bookingId: String,
    expertName: String,
    cropName: String,
    medicines: List<PrescribedMedicine>,
    advice: String,
    withholdingDays: Int
  ) {
    repository.issuePrescription(
      bookingId = bookingId,
      expertName = expertName,
      cropName = cropName,
      medicines = medicines,
      advice = advice,
      withholdingDays = withholdingDays
    )
    _showPrescriptionModalForBooking.value = null
  }

  fun withdrawExpertPayout(expertId: String, amount: Int): Boolean {
    return repository.withdrawExpertEarnings(expertId, amount)
  }

  fun upgradeToKisanGold() {
    repository.upgradeToKisanGold()
  }

  fun updateExpertProfileSettings(
    expertId: String,
    priceInRupees: Int,
    availableTime: String,
    bio: String
  ) {
    repository.updateExpertProfile(expertId, priceInRupees, availableTime, bio)
  }
}
