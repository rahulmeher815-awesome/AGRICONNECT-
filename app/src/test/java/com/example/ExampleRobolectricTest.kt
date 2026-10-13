package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AgriConnectRepository
import com.example.model.PrescribedMedicine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("AgriConnect", appName)
  }

  @Test
  fun `test expert repository matching booking rating and prescription flow`() {
    val repository = AgriConnectRepository()
    val categories = repository.categories
    assertTrue(categories.isNotEmpty())

    // Submit problem for paddy plant disease
    val problem = repository.submitProblemWithAi(
      cropName = "Paddy",
      categoryId = "plant_pathology",
      description = "The leaves of my paddy crop are turning yellow.",
      location = "Punjab",
      aiDiagnosis = null
    )
    assertNotNull(problem.id)
    assertEquals("Paddy", problem.cropName)
    assertEquals("plant_pathology", problem.categoryId)

    // Book consultation with Dr. Rahul Kumar
    val booking = repository.createBooking(
      problemId = problem.id,
      expertId = "exp_1",
      slotString = "10 October, 6:00 PM",
      cropName = "Paddy",
      categoryName = "Plant Disease"
    )
    assertNotNull(booking.id)
    assertEquals("Active Consultation", booking.status)
    assertEquals(300, booking.expertPrice)
    assertEquals(45, booking.platformFee) // 15% platform take
    assertEquals(255, booking.expertPayout)

    // Issue digital prescription
    val rx = repository.issuePrescription(
      bookingId = booking.id,
      expertName = "Dr. Rahul Kumar",
      cropName = "Paddy",
      medicines = listOf(
        PrescribedMedicine("Propiconazole 25% EC", "1ml/L", "Foliar Spray", "Leaf Blight")
      ),
      advice = "Drain field water for 48 hours",
      withholdingDays = 14
    )
    assertNotNull(rx.id)
    assertEquals(1, rx.medicines.size)

    // Test fertilizer calculator
    val fertCalc = repository.calculateFertilizer("Paddy", 5f)
    assertTrue(fertCalc.ureaBagsRequired > 0)

    val initialExpert = repository.experts.value.first { it.id == "exp_1" }
    val initialReviews = initialExpert.reviewCount

    // Rate expert with 5 stars
    val updatedExpert = repository.rateExpert(
      expertId = "exp_1",
      stars = 5,
      bookingId = booking.id
    )

    assertNotNull(updatedExpert)
    assertEquals(initialReviews + 1, updatedExpert?.reviewCount)

    // Check booking status updated to Completed with rating
    val updatedBooking = repository.bookings.value.first { it.id == booking.id }
    assertTrue(updatedBooking.status.contains("Completed (Rated ⭐ 5)"))
  }
}
