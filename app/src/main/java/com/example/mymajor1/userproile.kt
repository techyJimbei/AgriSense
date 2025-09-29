package com.example.mymajor1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymajor1.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfile() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_green))
    ) {
        Image(
            painter = painterResource(R.drawable.bg),
            contentDescription = "crop",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier.size(78.dp)
            )

            Spacer(modifier = Modifier.height(7.dp))

            Text(
                text = stringResource(R.string.name),
                color = colorResource(R.color.text_green),
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(70.dp))

            val fieldData = listOf(
                "Full Name" to "Enter your full name",
                "Contact Number" to "Enter your contact number",
                "Gender" to "Enter your gender",
                "Last Crop(s)" to "Enter last grown crops",
                "Address" to "Enter your address",
                "Language" to "Preferred language",
                "Age" to "Enter your age"
            )

            fieldData.forEach { (labelText, placeholderText) ->
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    shape = RoundedCornerShape(7.dp),
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 65.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White
                    ),
                    label = {
                        Text(
                            text = labelText,
                            fontSize = 12.sp,
                            color = colorResource(R.color.grey)
                        )
                    },
                    placeholder = {
                        Text(
                            text = placeholderText,
                            fontSize = 12.sp,
                            color = colorResource(R.color.grey)
                        )
                    }
                )

                Spacer(modifier = Modifier.height(18.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 65.dp),
                shape = RoundedCornerShape(7.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.text_green)
                )
            ) {
                Text(
                    text = "Save Profile",
                    color = colorResource(R.color.white),
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
