package com.example.mymajor1.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mymajor1.R


data class Scheme(
    val id: Int,
    val title: String,
    val eligibility: String,
    val benefits: String,
    val howToApply: String,
    val contact: String
)


object GovernmentSchemes {
    val schemes = listOf(
        Scheme(
            id = 1,
            title = "Pradhan Mantri Kisan Samman Nidhi (PM-KISAN)",
            eligibility = "All land holding eligible farmer families (subject to exclusion criteria) are eligible. " +
                    "However, higher economic status beneficiaries are not eligible, including:\n" +
                    "• Institutional Land Holders\n" +
                    "• Holders of constitutional posts, Ministers, MPs, MLAs, etc.\n" +
                    "• Serving or retired Govt. employees (excluding Class IV/MTS)\n" +
                    "• Pensioners with ₹10,000+ monthly pension (excluding Class IV/MTS)\n" +
                    "• Income Tax payees\n" +
                    "• Professionals such as Doctors, Engineers, Lawyers, CAs, and Architects.",
            benefits = "Under PM-KISAN, all landholding farmer families receive ₹6,000 per year, " +
                    "paid in three equal installments of ₹2,000 every four months.",
            howToApply = "Eligible farmers can apply through village Patwaris, revenue officials, or designated agencies. " +
                    "They may also register at Common Service Centres (CSCs) by paying a nominal fee.\n\n" +
                    "Self-registration is available through the 'Farmers Corner' on the PM KISAN portal.\n\n" +
                    "Required details include:\n" +
                    "• Name, Age, Gender, Category (SC/ST)\n" +
                    "• Aadhaar or Enrollment Number with valid ID proof\n" +
                    "• Bank Account Number and Mobile Number\n\n" +
                    "For more info, visit the official PM KISAN website.",
            contact = "Government of India\nFor assistance, contact local agriculture offices or visit:\nhttps://pmkisan.gov.in"
        ),
        Scheme(
            id = 2,
            title = "Pradhan Mantri Fasal Bima Yojana (PMFBY)",
            eligibility = "All farmers including sharecroppers and tenant farmers growing notified crops in a notified area are eligible. " +
                    "Farmers must have insurable interest in the crop.",
            benefits = "Provides insurance coverage and financial support to farmers in case of crop failure. " +
                    "Covers yield losses due to non-preventable natural risks. " +
                    "Low premium: 2% for Kharif, 1.5% for Rabi, and 5% for annual commercial/horticultural crops.",
            howToApply = "Apply through:\n" +
                    "• Nearest bank branch\n" +
                    "• Common Service Centers (CSCs)\n" +
                    "• Agriculture department offices\n" +
                    "• Online through PMFBY portal\n\n" +
                    "Required documents:\n" +
                    "• Land records\n" +
                    "• Aadhaar Card\n" +
                    "• Bank account details",
            contact = "PMFBY Helpline: 1800-180-1551\nWebsite: https://pmfby.gov.in"
        ),
        Scheme(
            id = 3,
            title = "Kisan Credit Card (KCC)",
            eligibility = "All farmers - individual/joint borrowers who are owner cultivators, tenant farmers, " +
                    "oral lessees and share croppers are eligible. " +
                    "Self Help Groups (SHGs) or Joint Liability Groups (JLGs) of farmers are also eligible.",
            benefits = "Provides adequate and timely credit for:\n" +
                    "• Agriculture operations\n" +
                    "• Post-harvest expenses\n" +
                    "• Consumption needs\n" +
                    "• Working capital for maintenance\n" +
                    "• Investment credit for allied activities\n" +
                    "Interest subvention of 2% and prompt repayment incentive of 3%.",
            howToApply = "Visit any:\n" +
                    "• Scheduled Commercial Banks\n" +
                    "• Regional Rural Banks (RRBs)\n" +
                    "• Cooperative Banks\n\n" +
                    "Required documents:\n" +
                    "• Application form with photograph\n" +
                    "• Identity proof (Aadhaar/Voter ID/PAN)\n" +
                    "• Address proof\n" +
                    "• Land documents",
            contact = "Contact your nearest bank branch or Agriculture Department"
        ),
        Scheme(
            id = 4,
            title = "Soil Health Card Scheme",
            eligibility = "All farmers holding agricultural land are eligible. " +
                    "The scheme aims to issue soil health cards to all 14 crore farm holdings in the country.",
            benefits = "Free soil testing and health card issued every 2 years containing:\n" +
                    "• Status of 12 parameters (N, P, K, S, Zn, Fe, Cu, Mn, Bo and pH, EC, OC)\n" +
                    "• Fertilizer recommendations and soil amendment required\n" +
                    "Helps improve productivity and reduce input costs.",
            howToApply = "Contact:\n" +
                    "• State Agriculture Department\n" +
                    "• Soil Testing Laboratory\n" +
                    "• Local Krishi Vigyan Kendra (KVK)\n\n" +
                    "Soil samples will be collected from your field and tested free of cost.",
            contact = "Department of Agriculture & Cooperation\nWebsite: https://soilhealth.dac.gov.in"
        ),
        Scheme(
            id = 5,
            title = "Pradhan Mantri Krishi Sinchai Yojana (PMKSY)",
            eligibility = "All farmers are eligible. Priority is given to:\n" +
                    "• Small and marginal farmers\n" +
                    "• SC/ST farmers\n" +
                    "• Women farmers\n" +
                    "• Beneficiaries of land reforms",
            benefits = "Financial assistance for:\n" +
                    "• Drip and sprinkler irrigation systems\n" +
                    "• Farm ponds and water harvesting structures\n" +
                    "• Micro irrigation\n" +
                    "Subsidy ranges from 55% to 100% depending on farmer category and component.",
            howToApply = "Apply through:\n" +
                    "• District Agriculture Office\n" +
                    "• Online through state agriculture portal\n" +
                    "• Common Service Centers\n\n" +
                    "Documents required:\n" +
                    "• Land ownership documents\n" +
                    "• Aadhaar card\n" +
                    "• Bank account details\n" +
                    "• Caste certificate (if applicable)",
            contact = "Ministry of Jal Shakti\nWebsite: https://pmksy.gov.in"
        ),
        Scheme(
            id = 6,
            title = "National Agriculture Market (e-NAM)",
            eligibility = "All farmers selling their produce in mandis integrated with e-NAM platform are eligible. " +
                    "Farmers need to register on the e-NAM portal.",
            benefits = "• Online trading platform for agricultural commodities\n" +
                    "• Better price discovery\n" +
                    "• Transparent auction process\n" +
                    "• Direct payment to farmers' bank accounts\n" +
                    "• Access to multiple markets across states",
            howToApply = "Register on e-NAM portal:\n" +
                    "1. Visit https://www.enam.gov.in\n" +
                    "2. Click on 'Farmer Registration'\n" +
                    "3. Fill details and upload documents\n" +
                    "4. Get registration number\n\n" +
                    "Required: Aadhaar, Bank details, Mobile number",
            contact = "e-NAM Helpdesk: 1800-270-0224\nEmail: nam@sfac.in"
        ),
        Scheme(
            id = 7,
            title = "Paramparagat Krishi Vikas Yojana (PKVY)",
            eligibility = "Farmers doing organic farming or willing to adopt organic farming are eligible. " +
                    "Farmers need to form clusters of 50 acres each (20 farmers).",
            benefits = "Financial assistance of ₹50,000 per hectare for 3 years including:\n" +
                    "• ₹31,000 for organic inputs\n" +
                    "• ₹8,800 for certification\n" +
                    "• ₹3,000 for value addition infrastructure\n" +
                    "• ₹7,200 for cluster formation and capacity building",
            howToApply = "Apply through:\n" +
                    "• District Agriculture Office\n" +
                    "• State Organic Certification Agency\n\n" +
                    "Form a cluster of farmers and apply as a group.\n" +
                    "Training on organic farming will be provided.",
            contact = "Department of Agriculture & Cooperation\nWebsite: https://pgsindia-ncof.gov.in"
        ),
        Scheme(
            id = 8,
            title = "Sub-Mission on Agricultural Mechanization (SMAM)",
            eligibility = "All categories of farmers are eligible with priority to:\n" +
                    "• Small and marginal farmers\n" +
                    "• SC/ST farmers\n" +
                    "• Women farmers\n" +
                    "• Farmers of North Eastern states",
            benefits = "Financial assistance for purchase of agricultural machinery and equipment:\n" +
                    "• 50% subsidy for SC/ST/Small & Marginal farmers\n" +
                    "• 40% subsidy for other farmers\n" +
                    "• 80% subsidy for demonstration of hi-tech equipment\n" +
                    "Maximum subsidy limit: ₹1.25 lakh for individuals",
            howToApply = "Apply online through:\n" +
                    "• Direct Benefit Transfer in Agriculture (DBT Agriculture) portal\n" +
                    "• State Agriculture Department website\n\n" +
                    "Required documents:\n" +
                    "• Aadhaar Card\n" +
                    "• Land documents\n" +
                    "• Bank account details\n" +
                    "• Caste certificate (if applicable)",
            contact = "Department of Agriculture & Cooperation\nWebsite: https://agrimachinery.nic.in"
        ),
        Scheme(
            id = 9,
            title = "Rashtriya Krishi Vikas Yojana (RKVY)",
            eligibility = "State governments prepare and submit projects for funding. " +
                    "Farmers benefit through state-implemented schemes under RKVY including:\n" +
                    "• Seed production\n" +
                    "• Soil health management\n" +
                    "• Farm mechanization\n" +
                    "• Marketing infrastructure",
            benefits = "100% central assistance to states for:\n" +
                    "• Infrastructure development\n" +
                    "• Agricultural innovation\n" +
                    "• Value addition projects\n" +
                    "• Farmer training programs\n" +
                    "Beneficiaries receive assistance as per state scheme guidelines.",
            howToApply = "Farmers should:\n" +
                    "• Contact State Agriculture Department\n" +
                    "• Check for schemes under RKVY in their state\n" +
                    "• Apply through district agriculture offices\n\n" +
                    "Each state has different components - check state agriculture portal.",
            contact = "Ministry of Agriculture & Farmers Welfare\nWebsite: https://rkvy.nic.in"
        ),
        Scheme(
            id = 10,
            title = "Modified Interest Subvention Scheme (MISS)",
            eligibility = "Farmers availing short-term crop loans up to ₹3 lakh from:\n" +
                    "• Public Sector Banks\n" +
                    "• Private Sector Banks\n" +
                    "• Regional Rural Banks\n" +
                    "• Cooperative Banks",
            benefits = "Interest subvention of 2% per annum on crop loans up to ₹3 lakh.\n" +
                    "Additional 3% interest subvention for prompt repayment (making effective rate 4%).\n" +
                    "Post-harvest loans for 6 months at 2% interest subvention against negotiable warehouse receipts.",
            howToApply = "No separate application required.\n" +
                    "Benefit is automatically provided when taking crop loans from banks.\n\n" +
                    "Ensure:\n" +
                    "• Aadhaar seeding with bank account\n" +
                    "• Valid KCC (Kisan Credit Card)\n" +
                    "• Timely repayment for additional benefit",
            contact = "Contact your bank branch for more details"
        )
    )
}

@Composable
fun SchemeCard(
    scheme: Scheme,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 6.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 14.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = scheme.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                ),
                color = colorResource(R.color.text_green),
                modifier = Modifier.weight(1f)
            )

            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(color = colorResource(R.color.text_green)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Arrow",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GovernmentSchemeScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val filteredList = GovernmentSchemes.schemes.filter {
        it.title.contains(searchQuery.text, ignoreCase = true)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_green))
    ) {
        Image(
            painter = painterResource(R.drawable.bg),
            contentDescription = "background",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Government Schemes") },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = colorResource(R.color.text_green),
                        titleContentColor = Color.White
                    )
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search schemes...") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = colorResource(R.color.text_green)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorResource(R.color.text_green),
                        unfocusedBorderColor = colorResource(R.color.text_green),
                        cursorColor = colorResource(R.color.text_green),
                        containerColor = Color.White.copy(alpha = 0.95f),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 8.dp)
                ) {
                    items(filteredList) { scheme ->
                        SchemeCard(
                            scheme = scheme,
                            onClick = {
                                // Navigate to detail screen with scheme ID
                                navController.navigate("schemeDetail/${scheme.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}