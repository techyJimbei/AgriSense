package com.example.mymajor1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymajor1.R

@Composable
fun SchemeDescriptionScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_green))
    ) {

        Image(
            painter = painterResource(R.drawable.bg),
            contentDescription = "bottom background",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, end = 24.dp, top = 48.dp, bottom = 28.dp) // added top margin here
                .verticalScroll(rememberScrollState())
        ) {
            // Title
            Text(
                text = "Pradhan Mantri Kisan Samman Nidhi",
                color = colorResource(R.color.text_green),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Eligibility
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Eligibility",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(R.color.text_green)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "All land holding eligible farmer families (subject to exclusion criteria) are eligible. " +
                                "However, higher economic status beneficiaries are not eligible, including:\n" +
                                "- Institutional Land Holders\n" +
                                "- Holders of constitutional posts, Ministers, MPs, MLAs, etc.\n" +
                                "- Serving or retired Govt. employees (excluding Class IV/MTS)\n" +
                                "- Pensioners with ₹10,000+ monthly pension (excluding Class IV/MTS)\n" +
                                "- Income Tax payees\n" +
                                "- Professionals such as Doctors, Engineers, Lawyers, CAs, and Architects.",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Benefits
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Benefits",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(R.color.text_green)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Under PM-KISAN, all landholding farmer families receive ₹6,000 per year, " +
                                "paid in three equal installments of ₹2,000 every four months.",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // How to Apply
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "How to Apply",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(R.color.text_green)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Eligible farmers can apply through village Patwaris, revenue officials, or designated agencies. " +
                                "They may also register at Common Service Centres (CSCs) by paying a nominal fee.\n\n" +
                                "Self-registration is available through the 'Farmers Corner' on the PM KISAN portal.\n\n" +
                                "Required details include:\n" +
                                "- Name, Age, Gender, Category (SC/ST)\n" +
                                "- Aadhaar or Enrollment Number with valid ID proof\n" +
                                "- Bank Account Number and Mobile Number\n\n" +
                                "For more info, visit the official PM KISAN website.",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Contact
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Contact",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(R.color.text_green)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Government of India\nFor assistance, contact local agriculture offices or visit:\nhttps://pmkisan.gov.in",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}
