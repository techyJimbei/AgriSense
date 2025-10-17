package com.example.mymajor1.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymajor1.R
import com.example.mymajor1.model.CropActivity
import com.example.mymajor1.viewmodel.CropCalendarViewModel
import com.example.mymajor1.viewmodel.FarmerViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CropCalendarScreen(
    viewModel: CropCalendarViewModel,
    farmerViewModel: FarmerViewModel,
    onDayClick: (LocalDate) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val farmerInfo by farmerViewModel.farmerInfo.collectAsState()

    LaunchedEffect(viewModel.currentMonth) {
        viewModel.loadActivities(farmerInfo?.farmerId ?: 0)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_green))
    ) {
        // Background Image
        Image(
            painter = painterResource(R.drawable.bg),
            contentDescription = "background",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp, start = 12.dp, end = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Back Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(R.drawable.back_icon),
                    contentDescription = "back icon",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { onBackClick() }
                )
            }

            Text(
                "Crop Activity Calendar",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

            Text(
                "Track your farming activities and plan ahead",
                fontSize = 16.sp,
                color = colorResource(R.color.text_green)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(R.color.text_green)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.changeMonth(farmerInfo?.farmerId ?: 0, -1) }
                    ) {
                        Icon(
                            Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Previous month",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Text(
                        text = "${
                            viewModel.currentMonth.month.getDisplayName(
                                TextStyle.FULL,
                                Locale.getDefault()
                            )
                        } ${viewModel.currentMonth.year}",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(
                        onClick = { viewModel.changeMonth(farmerInfo?.farmerId ?: 0, 1) }
                    ) {
                        Icon(
                            Icons.Default.KeyboardArrowRight,
                            contentDescription = "Next month",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                if (viewModel.isLoading) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(48.dp),
                                color = colorResource(R.color.text_green)
                            )
                            Text(
                                "Loading activities...",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = colorResource(R.color.text_green)
                            )
                        }
                    }
                } else {
                    CalendarGrid(
                        currentMonth = viewModel.currentMonth,
                        activities = viewModel.activities,
                        onDayClick = onDayClick
                    )
                }
            }
        }
    }
}

@Composable
fun CalendarGrid(
    currentMonth: YearMonth,
    activities: List<CropActivity>,
    onDayClick: (LocalDate) -> Unit
) {
    val firstDayOfMonth = currentMonth.atDay(1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
    val daysInMonth = currentMonth.lengthOfMonth()

    val activityMap = activities
        .filter { it.date.year == currentMonth.year && it.date.month == currentMonth.month }
        .groupBy { it.date }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Day headers
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = colorResource(R.color.text_green)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Days grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Empty cells before first day
                items(firstDayOfWeek) {
                    Box(modifier = Modifier.aspectRatio(1f))
                }

                // Days of month
                items(daysInMonth) { day ->
                    val date = currentMonth.atDay(day + 1)
                    val dayActivities = activityMap[date] ?: emptyList()

                    DayCell(
                        day = day + 1,
                        activities = dayActivities,
                        isToday = date == LocalDate.now(),
                        onClick = { onDayClick(date) }
                    )
                }
            }
        }
    }
}

@Composable
fun DayCell(
    day: Int,
    activities: List<CropActivity>,
    isToday: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isToday -> colorResource(R.color.text_green).copy(alpha = 0.15f)
        activities.isNotEmpty() -> colorResource(R.color.light_green)
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = day.toString(),
                fontSize = 14.sp,
                fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                color = if (isToday) colorResource(R.color.text_green) else Color.Black
            )

            if (activities.isNotEmpty()) {
                Row(
                    modifier = Modifier.padding(top = 4.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    activities.take(3).forEach { activity ->
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .padding(1.dp)
                                .clip(CircleShape)
                                .background(activity.type.color)
                        )
                    }
                }
            }
        }
    }
}