package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.UserRole
import com.example.ui.components.AgriTopBar
import com.example.ui.dialogs.BookingReceiptDialog
import com.example.ui.dialogs.BookingSlotDialog
import com.example.ui.dialogs.PrescriptionDialog
import com.example.ui.dialogs.StarRatingDialog
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.ExpertDashboardScreen
import com.example.ui.screens.ExpertDetailScreen
import com.example.ui.screens.FarmerDashboardScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PlatformAdminScreen
import com.example.ui.theme.AgriConnectTheme
import com.example.viewmodel.AgriConnectViewModel
import com.example.viewmodel.FarmerTab
import com.example.viewmodel.Screen

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      AgriConnectTheme {
        AgriConnectApp()
      }
    }
  }
}

@Composable
fun AgriConnectApp(viewModel: AgriConnectViewModel = viewModel()) {
  val currentRole by viewModel.currentRole.collectAsState()
  val currentScreen by viewModel.currentScreen.collectAsState()
  val categories = viewModel.categories
  val experts by viewModel.experts.collectAsState()
  val bookings by viewModel.bookings.collectAsState()
  val allMessages by viewModel.allMessages.collectAsState()
  val problemForm by viewModel.problemForm.collectAsState()
  val activeCategoryFilter by viewModel.activeCategoryFilter.collectAsState()
  val lastSubmittedProblem by viewModel.lastSubmittedProblem.collectAsState()
  val selectedExpertForBooking by viewModel.selectedExpertForBooking.collectAsState()
  val bookingSuccessReceipt by viewModel.bookingSuccessReceipt.collectAsState()
  val expertToRate by viewModel.expertToRate.collectAsState()
  val showPrescriptionModalForBooking by viewModel.showPrescriptionModalForBooking.collectAsState()
  val isDiagnosingAi by viewModel.isDiagnosingWithAi.collectAsState()
  val latestAiDiagnosis by viewModel.latestAiDiagnosis.collectAsState()
  val fertilizerCalculation by viewModel.fertilizerCalculation.collectAsState()
  val platformEarnings by viewModel.platformCommissionEarnings.collectAsState()
  val isGoldMember by viewModel.isKisanGoldMember.collectAsState()

  val canNavigateBack = currentScreen !is Screen.Home

  BackHandler(enabled = canNavigateBack) {
    viewModel.navigateBack()
  }

  Scaffold(
    topBar = {
      if (currentScreen !is Screen.ChatScreen) {
        AgriTopBar(
          currentRole = currentRole,
          isGoldMember = isGoldMember,
          canNavigateBack = canNavigateBack,
          onNavigateBack = { viewModel.navigateBack() },
          onSwitchRole = { newRole -> viewModel.switchRole(newRole) },
          onHomeClick = { viewModel.navigateTo(Screen.Home) }
        )
      }
    },
    modifier = Modifier.fillMaxSize()
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      when (val screen = currentScreen) {
        is Screen.Home -> {
          HomeScreen(
            categories = categories,
            onAskAnExpert = {
              viewModel.switchRole(UserRole.FARMER)
              viewModel.navigateTo(Screen.FarmerDashboard(FarmerTab.AI_DIAGNOSIS))
            },
            onBecomeAnExpert = {
              viewModel.switchRole(UserRole.EXPERT)
            },
            onSelectCategory = { categoryId ->
              viewModel.setCategoryFilter(categoryId)
              viewModel.switchRole(UserRole.FARMER)
              viewModel.navigateTo(Screen.FarmerDashboard(FarmerTab.CONSULT_EXPERTS))
            }
          )
        }

        is Screen.FarmerDashboard -> {
          FarmerDashboardScreen(
            selectedTab = screen.selectedTab,
            onSelectTab = { tab ->
              viewModel.navigateTo(Screen.FarmerDashboard(tab))
            },
            categories = categories,
            problemForm = problemForm,
            onCropNameChange = { viewModel.updateCropName(it) },
            onCategoryChange = { viewModel.updateSelectedCategory(it) },
            onDescriptionChange = { viewModel.updateDescription(it) },
            onLocationChange = { viewModel.updateLocation(it) },
            onPrefillExample = { crop, cat, desc, loc ->
              viewModel.prefillExampleProblem(crop, cat, desc, loc)
            },
            onRunAiDiagnosis = { viewModel.runAiDiagnosis() },
            isDiagnosingAi = isDiagnosingAi,
            latestAiDiagnosis = latestAiDiagnosis,
            experts = experts,
            activeCategoryFilter = activeCategoryFilter,
            onSetCategoryFilter = { viewModel.setCategoryFilter(it) },
            onViewExpertProfile = { expert ->
              viewModel.navigateTo(Screen.ExpertDetail(expert.id, lastSubmittedProblem?.id))
            },
            onBookConsultation = { expert ->
              viewModel.openBookingModal(expert)
            },
            bookings = bookings,
            messagesMap = allMessages,
            onOpenChat = { bookingId ->
              viewModel.openChatForBooking(bookingId)
            },
            onRateBooking = { booking ->
              val exp = experts.firstOrNull { it.id == booking.expertId } ?: experts.first()
              viewModel.openRatingDialog(exp, booking.cropName, booking.id)
            },
            mandiPrices = viewModel.mandiPrices,
            weatherAdvisory = viewModel.weatherAdvisory,
            fertilizerCalculation = fertilizerCalculation,
            onCalculateFertilizer = { crop, acres ->
              viewModel.calculateFertilizer(crop, acres)
            },
            isGoldMember = isGoldMember,
            onUpgradeToGold = { viewModel.upgradeToKisanGold() }
          )
        }

        is Screen.ExpertDashboard -> {
          val expertProfile = experts.firstOrNull { it.id == "exp_1" } ?: experts.first()
          ExpertDashboardScreen(
            selectedTab = screen.selectedTab,
            onSelectTab = { tab ->
              viewModel.navigateTo(Screen.ExpertDashboard(tab))
            },
            currentExpert = expertProfile,
            onUpdateExpertSettings = { id, price, hours, bio ->
              viewModel.updateExpertProfileSettings(id, price, hours, bio)
            },
            onWithdrawEarnings = { id, amt ->
              viewModel.withdrawExpertPayout(id, amt)
            },
            bookings = bookings,
            messagesMap = allMessages,
            onOpenChat = { bookingId ->
              viewModel.openChatForBooking(bookingId)
            },
            onOpenPrescriptionModal = { b ->
              viewModel.openPrescriptionModal(b)
            }
          )
        }

        is Screen.PlatformAdmin -> {
          PlatformAdminScreen(
            platformEarnings = platformEarnings,
            isGoldMember = isGoldMember,
            experts = experts,
            bookings = bookings,
            onSwitchToFarmer = { viewModel.switchRole(UserRole.FARMER) },
            onSwitchToExpert = { viewModel.switchRole(UserRole.EXPERT) }
          )
        }

        is Screen.ExpertDetail -> {
          val expert = experts.firstOrNull { it.id == screen.expertId } ?: experts.first()
          ExpertDetailScreen(
            expert = expert,
            onBookConsultation = {
              viewModel.openBookingModal(it)
            },
            onNavigateBack = { viewModel.navigateBack() }
          )
        }

        is Screen.BookingConfirmation -> {
          BookingReceiptDialog(
            booking = screen.booking,
            onDismiss = { viewModel.navigateTo(Screen.FarmerDashboard(FarmerTab.MY_BOOKINGS)) },
            onOpenChat = { bookingId -> viewModel.openChatForBooking(bookingId) }
          )
        }

        is Screen.ChatScreen -> {
          val booking = bookings.firstOrNull { it.id == screen.bookingId }
            ?: bookings.firstOrNull()
          if (booking != null) {
            val messages = allMessages[booking.id] ?: emptyList()
            ChatScreen(
              booking = booking,
              messages = messages,
              onSendMessage = { bookingId, text, role ->
                viewModel.sendChatMessage(bookingId, text, role)
              },
              onCompleteAndRateSession = {
                val exp = experts.firstOrNull { it.id == booking.expertId } ?: experts.first()
                viewModel.openRatingDialog(exp, booking.cropName, booking.id)
              },
              onNavigateBack = { viewModel.navigateBack() }
            )
          } else {
            HomeScreen(
              categories = categories,
              onAskAnExpert = { viewModel.switchRole(UserRole.FARMER) },
              onBecomeAnExpert = { viewModel.switchRole(UserRole.EXPERT) },
              onSelectCategory = { viewModel.setCategoryFilter(it) }
            )
          }
        }
      }

      // Booking Slot Selection Modal
      selectedExpertForBooking?.let { expert ->
        BookingSlotDialog(
          expert = expert,
          onDismiss = { viewModel.dismissBookingModal() },
          onConfirmBooking = { slot ->
            viewModel.confirmBooking(expert, slot)
          }
        )
      }

      // Booking Confirmed Receipt Dialog
      bookingSuccessReceipt?.let { booking ->
        BookingReceiptDialog(
          booking = booking,
          onDismiss = { viewModel.dismissBookingReceipt() },
          onOpenChat = { bookingId -> viewModel.openChatForBooking(bookingId) }
        )
      }

      // Star Rating Dialog
      expertToRate?.let { (expert, cropTopic, bookingId) ->
        StarRatingDialog(
          expert = expert,
          bookingCropTopic = cropTopic,
          bookingId = bookingId,
          onDismiss = { viewModel.dismissRatingDialog() },
          onSubmitRating = { expertId, stars, bId ->
            viewModel.submitRating(expertId, stars, bId)
          }
        )
      }

      // Digital Prescription Issue Modal (for Expert)
      showPrescriptionModalForBooking?.let { booking ->
        PrescriptionDialog(
          booking = booking,
          onDismiss = { viewModel.dismissPrescriptionModal() },
          onIssuePrescription = { bookingId, expertName, cropName, medicines, advice, days ->
            viewModel.issuePrescription(bookingId, expertName, cropName, medicines, advice, days)
          }
        )
      }
    }
  }
}
