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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AgriculturalCategory
import com.example.model.UserRole
import com.example.ui.theme.AgriGreenContainer
import com.example.ui.theme.AgriGreenOnContainer
import com.example.ui.theme.AgriGreenPrimary
import com.example.ui.theme.AgriHarvestAmber

@Composable
fun HomeScreen(
  categories: List<AgriculturalCategory>,
  onAskAnExpert: () -> Unit,
  onBecomeAnExpert: () -> Unit,
  onSelectCategory: (String) -> Unit
) {
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF7FAF7))
      .verticalScroll(scrollState)
      .padding(bottom = 32.dp)
  ) {
    // Hero Section with agriculture gradient
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          brush = Brush.verticalGradient(
            colors = listOf(
              AgriGreenPrimary,
              Color(0xFF267347)
            )
          )
        )
        .padding(horizontal = 20.dp, vertical = 28.dp)
    ) {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Nature Badge
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0x33FFFFFF))
            .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "🌱", fontSize = 14.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Empowering Indian Agriculture",
              color = Color.White,
              fontSize = 12.sp,
              fontWeight = FontWeight.Medium
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "AgriConnect",
          fontSize = 32.sp,
          fontWeight = FontWeight.ExtraBold,
          color = Color.White,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "“Connect Farmers with the Right Agriculture Experts.”",
          fontSize = 16.sp,
          fontWeight = FontWeight.SemiBold,
          color = Color(0xFFD6F0DF),
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = "Describe your farming problem, find the right expert, and get agricultural guidance.",
          fontSize = 14.sp,
          color = Color(0xFFE2EFE5),
          textAlign = TextAlign.Center,
          lineHeight = 20.sp,
          modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Hero CTA Buttons
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Button(
            onClick = onAskAnExpert,
            modifier = Modifier
              .weight(1f)
              .height(50.dp)
              .testTag("home_ask_an_expert_button"),
            colors = ButtonDefaults.buttonColors(
              containerColor = Color.White,
              contentColor = AgriGreenPrimary
            ),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Agriculture,
              contentDescription = null,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Ask an Expert",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
          }

          OutlinedButton(
            onClick = onBecomeAnExpert,
            modifier = Modifier
              .weight(1f)
              .height(50.dp)
              .testTag("home_become_an_expert_button"),
            colors = ButtonDefaults.outlinedButtonColors(
              contentColor = Color.White
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(
              brush = Brush.linearGradient(listOf(Color.White, Color(0xFFD6F0DF)))
            ),
            shape = RoundedCornerShape(12.dp)
          ) {
            Icon(
              imageVector = Icons.Default.School,
              contentDescription = null,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Become an Expert",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // 3-Step Section
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
    ) {
      Text(
        text = "How AgriConnect Works",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF131D16)
      )
      Text(
        text = "3 simple steps to resolve crop challenges",
        fontSize = 13.sp,
        color = Color(0xFF5A6B5F)
      )

      Spacer(modifier = Modifier.height(14.dp))

      StepCard(
        stepNumber = 1,
        title = "Describe your problem",
        description = "Enter your crop name, symptoms, and select category (e.g. Paddy yellowing leaves, pest attacks).",
        icon = Icons.Default.Assignment
      )

      Spacer(modifier = Modifier.height(10.dp))

      StepCard(
        stepNumber = 2,
        title = "Find the right expert",
        description = "Get matched instantly with verified agronomists, plant pathologists, entomologists, and soil scientists.",
        icon = Icons.Default.PersonSearch
      )

      Spacer(modifier = Modifier.height(10.dp))

      StepCard(
        stepNumber = 3,
        title = "Connect with the expert",
        description = "Select an available time slot, confirm demo booking, and chat directly for remedial solutions.",
        icon = Icons.Default.Chat
      )
    }

    Spacer(modifier = Modifier.height(28.dp))

    // Agriculture Expert Categories Section
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Expertise Domains",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF131D16)
          )
          Text(
            text = "Select domain to browse qualified specialists",
            fontSize = 13.sp,
            color = Color(0xFF5A6B5F)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        categories.forEach { category ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onSelectCategory(category.id) }
              .testTag("category_card_${category.id}"),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Box(
                modifier = Modifier
                  .size(42.dp)
                  .clip(CircleShape)
                  .background(Color(0xFFEAF5EE)),
                contentAlignment = Alignment.Center
              ) {
                Text(text = category.iconGlyph, fontSize = 20.sp)
              }

              Spacer(modifier = Modifier.width(14.dp))

              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = category.name,
                  fontWeight = FontWeight.Bold,
                  fontSize = 15.sp,
                  color = Color(0xFF131D16)
                )
                Text(
                  text = category.description,
                  fontSize = 12.sp,
                  color = Color(0xFF5A6B5F),
                  maxLines = 1
                )
              }

              Text(
                text = "View →",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = AgriGreenPrimary
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun StepCard(
  stepNumber: Int,
  title: String,
  description: String,
  icon: ImageVector
) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(40.dp)
          .clip(CircleShape)
          .background(AgriGreenContainer),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "$stepNumber",
          color = AgriGreenOnContainer,
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp
        )
      }

      Spacer(modifier = Modifier.width(14.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          fontWeight = FontWeight.Bold,
          fontSize = 15.sp,
          color = Color(0xFF131D16)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = description,
          fontSize = 12.sp,
          color = Color(0xFF5A6B5F),
          lineHeight = 16.sp
        )
      }
    }
  }
}
