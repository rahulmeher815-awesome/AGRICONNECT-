package com.example.ui.components

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.example.model.UserRole
import com.example.ui.theme.AgriGreenPrimary
import com.example.ui.theme.AgriHarvestAmber

@Composable
fun AgriTopBar(
  currentRole: UserRole,
  isGoldMember: Boolean = false,
  canNavigateBack: Boolean,
  onNavigateBack: () -> Unit,
  onSwitchRole: (UserRole) -> Unit,
  onHomeClick: () -> Unit
) {
  val barBackground = when (currentRole) {
    UserRole.FARMER -> AgriGreenPrimary
    UserRole.EXPERT -> Color(0xFF92400E)
    UserRole.PLATFORM_OWNER -> Color(0xFF075985)
  }

  Surface(
    modifier = Modifier.fillMaxWidth(),
    color = barBackground,
    shadowElevation = 3.dp
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .statusBarsPadding()
        .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.clickable { onHomeClick() }
        ) {
          if (canNavigateBack) {
            IconButton(
              onClick = onNavigateBack,
              modifier = Modifier
                .size(40.dp)
                .testTag("top_bar_back_button")
            ) {
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
              )
            }
            Spacer(modifier = Modifier.width(4.dp))
          }

          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(Color.White),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Agriculture,
              contentDescription = "AgriConnect Logo",
              tint = barBackground,
              modifier = Modifier.size(24.dp)
            )
          }

          Spacer(modifier = Modifier.width(8.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "AgriConnect",
                color = Color.White,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
              )
              if (isGoldMember && currentRole == UserRole.FARMER) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                  imageVector = Icons.Default.WorkspacePremium,
                  contentDescription = "Gold Member",
                  tint = AgriHarvestAmber,
                  modifier = Modifier.size(16.dp)
                )
              }
            }
            Text(
              text = when (currentRole) {
                UserRole.FARMER -> "Kisan Agri Portal"
                UserRole.EXPERT -> "Krishi Specialist Portal"
                UserRole.PLATFORM_OWNER -> "Platform Business Admin"
              },
              color = Color(0xFFD6F0DF),
              fontSize = 11.sp
            )
          }
        }

        // Role Switcher Pill cycling through Farmer, Expert, and Platform Business
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(
            containerColor = Color(0x33FFFFFF)
          ),
          modifier = Modifier
            .clickable {
              val nextRole = when (currentRole) {
                UserRole.FARMER -> UserRole.EXPERT
                UserRole.EXPERT -> UserRole.PLATFORM_OWNER
                UserRole.PLATFORM_OWNER -> UserRole.FARMER
              }
              onSwitchRole(nextRole)
            }
            .testTag("role_switcher_button")
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(9.dp)
                .clip(CircleShape)
                .background(
                  when (currentRole) {
                    UserRole.FARMER -> Color(0xFF77DDA0)
                    UserRole.EXPERT -> AgriHarvestAmber
                    UserRole.PLATFORM_OWNER -> Color(0xFF38BDF8)
                  }
                )
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = currentRole.label,
              color = Color.White,
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
              imageVector = Icons.Default.SwapHoriz,
              contentDescription = "Switch Portal Role",
              tint = Color(0xFFD6F0DF),
              modifier = Modifier.size(16.dp)
            )
          }
        }
      }
    }
  }
}
