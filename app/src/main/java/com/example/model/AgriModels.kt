package com.example.model

enum class UserRole(val label: String, val badgeColorHex: Long) {
  FARMER("Farmer", 0xFF1E5E3A),
  EXPERT("Agriculture Expert", 0xFFD97706),
  PLATFORM_OWNER("Platform Business", 0xFF0284C7)
}

data class AgriculturalCategory(
  val id: String,
  val name: String,
  val expertSpecialtyTag: String,
  val description: String,
  val iconGlyph: String
)

data class ExpertProfile(
  val id: String,
  val name: String,
  val title: String,
  val expertise: String,
  val categoryId: String,
  val experienceYears: Int,
  val rating: Float,
  val reviewCount: Int,
  val priceInRupees: Int,
  val availableTime: String,
  val availableSlots: List<String>,
  val location: String,
  val bio: String,
  val qualifications: String,
  val contactEmail: String,
  val totalEarningsRupees: Int = 18450,
  val pendingPayoutRupees: Int = 3825,
  val consultationsCount: Int = 67
)

data class AiCropDiagnosis(
  val cropName: String,
  val diseaseIdentified: String,
  val severity: String, // "Low", "Moderate", "Critical"
  val confidenceScore: Int, // e.g. 94%
  val symptomsAnalysis: String,
  val chemicalRemedy: String,
  val organicRemedy: String,
  val sprayDosage: String,
  val recommendedDomain: String,
  val preventativeMeasure: String
)

data class CropProblem(
  val id: String,
  val cropName: String,
  val categoryId: String,
  val categoryName: String,
  val description: String,
  val location: String,
  val submittedAt: String,
  val matchedExpertIds: List<String> = emptyList(),
  val aiDiagnosis: AiCropDiagnosis? = null
)

data class PrescribedMedicine(
  val name: String,
  val dosage: String,
  val applicationMethod: String,
  val targetPestOrDisease: String
)

data class DigitalPrescription(
  val id: String,
  val bookingId: String,
  val expertName: String,
  val cropName: String,
  val medicines: List<PrescribedMedicine>,
  val generalAdvice: String,
  val withholdingPeriodDays: Int,
  val issuedDate: String
)

data class ConsultationBooking(
  val id: String,
  val problemId: String?,
  val expertId: String,
  val expertName: String,
  val expertTitle: String,
  val expertPrice: Int,
  val platformFee: Int = (expertPrice * 0.15).toInt(),
  val expertPayout: Int = expertPrice - (expertPrice * 0.15).toInt(),
  val cropName: String,
  val categoryName: String,
  val selectedDate: String,
  val selectedTime: String,
  val status: String = "Confirmed",
  val paymentStatus: String = "Paid via UPI",
  val transactionId: String = "TXN_AGRI_" + System.currentTimeMillis().toString().takeLast(6),
  val farmerName: String = "Ramesh Patel",
  val bookingTimestamp: Long = System.currentTimeMillis(),
  val prescription: DigitalPrescription? = null
)

data class ChatMessage(
  val id: String,
  val bookingId: String,
  val senderRole: UserRole,
  val senderName: String,
  val text: String,
  val timestampFormatted: String,
  val prescriptionAttachment: DigitalPrescription? = null
)

data class MandiCommodityPrice(
  val commodity: String,
  val mandiName: String,
  val state: String,
  val modalPricePerQtl: Int,
  val priceTrend: String, // e.g. "+₹80/Qtl"
  val isPositiveTrend: Boolean,
  val updatedDate: String
)

data class WeatherAdvisory(
  val location: String,
  val temperatureC: String,
  val humidityPercent: String,
  val condition: String,
  val rainChance: String,
  val windSpeedKmh: String,
  val sprayingRecommendation: String,
  val isSprayingSuitable: Boolean
)

data class FertilizerCalculation(
  val cropName: String,
  val acreage: Float,
  val ureaBagsRequired: Float,
  val dapBagsRequired: Float,
  val mopBagsRequired: Float,
  val zincSulphateKg: Float,
  val applicationScheduleNotes: String
)
