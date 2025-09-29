package com.example.mymajor1.login
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymajor1.R
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.bg_green))
    ) {
        Image(painter = painterResource(R.drawable.bg), contentDescription = "crop", modifier = Modifier
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
            Image(painter = painterResource(R.drawable.logo), contentDescription = "logo", modifier = Modifier.size(78.dp))
            Spacer(modifier = Modifier.height(7.dp))

            Text(text = stringResource(R.string.name), color = colorResource(R.color.text_green), fontSize = 20.sp)
            Spacer(modifier = Modifier.height(70.dp))

            Text(text = "Sign In", color = colorResource(R.color.text_green), fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(31.dp))

            Text(text = "Enter valid username and password", color = colorResource(R.color.black), fontSize = 13.sp)
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(modifier=Modifier.height(40.dp) ,shape = RoundedCornerShape(7.dp), value = "", onValueChange = {}, label = {
                    Text(
                        text = "User name/mobile number",
                        color = colorResource(R.color.grey),
                        fontSize = 12.sp
                    )
                }, colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(18.dp))

            OutlinedTextField(modifier=Modifier.height(40.dp) ,shape = RoundedCornerShape(7.dp), value = "", onValueChange = {}, label = {
                    Text(
                        text = "Password",
                        color = colorResource(R.color.grey),
                        fontSize = 12.sp
                    )
                }, colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(5.dp))

            Text(text = "Forget password?", color = colorResource(R.color.text_green), fontSize = 13.sp, textDecoration=TextDecoration.Underline, modifier = Modifier.fillMaxWidth().padding(start = 0.dp, top = 0.dp, bottom = 0.dp,end=65.dp)  .clickable { }, textAlign = TextAlign.End)
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {}, modifier = Modifier.fillMaxWidth().padding(horizontal = 65.dp), shape = RoundedCornerShape(7.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.text_green))) {
                Text(text = "Login", color = colorResource(R.color.white), fontSize = 15.sp) }
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "-Or continue with-", color = colorResource(R.color.grey), fontSize = 13.sp)
            Spacer(modifier = Modifier.height(14.dp))

            OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth().padding(horizontal = 65.dp), shape = RoundedCornerShape(7.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.light_green))) {
                Image(painter = painterResource(R.drawable.google), contentDescription = "google", modifier = Modifier.size(17.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Google", color = colorResource(R.color.black), fontSize = 13.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth().padding(horizontal = 65.dp), shape = RoundedCornerShape(7.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.light_green))) {
                Image(painter = painterResource(R.drawable.facebook), contentDescription = "facebook", modifier = Modifier.size(17.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Facebook", color = colorResource(R.color.black), fontSize = 13.sp)
            }
            Spacer(modifier = Modifier.height(15.dp))

            Row {
                Text(text = "Don’t have an account? ", fontWeight = FontWeight.Medium, color = colorResource(R.color.grey), fontSize = 13.sp)
                Text(modifier = Modifier.clickable { }, text = "Sign Up",textDecoration=TextDecoration.Underline, fontWeight = FontWeight.Bold, color = colorResource(R.color.text_green), fontSize = 13.sp)
            }
        }
    }
}
