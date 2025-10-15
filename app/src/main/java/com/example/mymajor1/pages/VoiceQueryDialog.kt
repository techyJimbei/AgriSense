package com.example.mymajor1.pages

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mymajor1.R

@Composable
fun VoiceQueryDialog(
    isListening: Boolean,
    partialText: String,
    finalText: String,
    backendResponse: String,
    isLoading: Boolean,
    errorMessage: String?,
    audioLevel: Float,
    onStartListening: () -> Unit,
    onStopListening: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.white)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = "Voice Assistant",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.text_green)
                )

                AnimatedMicrophoneButton(
                    isListening = isListening,
                    audioLevel = audioLevel,
                    onStartListening = onStartListening,
                    onStopListening = onStopListening
                )

                AnimatedVisibility(
                    visible = isListening,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Text(
                        text = "Listening...",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(R.color.text_green)
                    )
                }

                AnimatedVisibility(
                    visible = partialText.isNotEmpty(),
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(R.color.bg_green).copy(alpha = 0.3f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                text = "Your Query:",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.text_green)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = partialText,
                                fontSize = 16.sp,
                                color = colorResource(R.color.black)
                            )
                        }
                    }
                }

                AnimatedVisibility(
                    visible = finalText.isNotEmpty(),
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(R.color.light_green).copy(alpha = 0.3f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                text = "You said:",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.text_green)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = finalText,
                                fontSize = 16.sp,
                                color = colorResource(R.color.black)
                            )
                        }
                    }
                }

                AnimatedVisibility(
                    visible = isLoading,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CircularProgressIndicator(
                            color = colorResource(R.color.text_green)
                        )
                        Text(
                            text = "Processing...",
                            fontSize = 14.sp,
                            color = colorResource(R.color.text_green)
                        )
                    }
                }

                AnimatedVisibility(
                    visible = backendResponse.isNotEmpty() && !isLoading,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(R.color.text_green).copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Text(
                                text = "Response:",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.text_green)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = backendResponse,
                                fontSize = 16.sp,
                                color = colorResource(R.color.black)
                            )
                        }
                    }
                }

                errorMessage?.let { error ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Red.copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = error,
                            fontSize = 14.sp,
                            color = Color.Red,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.text_green)
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Close")
                }
            }
        }
    }
}

@Composable
fun AnimatedMicrophoneButton(
    isListening: Boolean,
    audioLevel: Float,
    onStartListening: () -> Unit,
    onStopListening: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val audioScale = remember(audioLevel) {
        1f + (audioLevel / 20f)
    }

    Box(
        contentAlignment = Alignment.Center
    ) {

        if (isListening) {
            Box(
                modifier = Modifier
                    .size(120.dp * scale)
                    .background(
                        color = colorResource(R.color.text_green).copy(alpha = 0.2f),
                        shape = CircleShape
                    )
            )
        }

        IconButton(
            onClick = {
                if (isListening) {
                    onStopListening()
                } else {
                    onStartListening()
                }
            },
            modifier = Modifier
                .size(80.dp)
                .scale(if (isListening) audioScale else 1f)
                .background(
                    color = if (isListening)
                        colorResource(R.color.text_green)
                    else
                        colorResource(R.color.light_green),
                    shape = CircleShape
                )
        ) {
            Icon(
                painter = painterResource(R.drawable.microphone_icon),
                contentDescription = "microphone",
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
        }
    }
}