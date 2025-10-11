package com.example.mymajor1.pages

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.mymajor1.R
import com.example.mymajor1.viewmodel.CropDetectionViewModel
import org.tensorflow.lite.Interpreter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

fun loadModelFile(context: Context, modelName: String): ByteBuffer {
    val fileDescriptor = context.assets.openFd(modelName)
    val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
    val fileChannel = inputStream.channel
    val startOffset = fileDescriptor.startOffset
    val declaredLength = fileDescriptor.declaredLength
    val buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    buffer.order(ByteOrder.nativeOrder())
    return buffer
}

fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri {
    val file = File(context.cacheDir, "captured_image_${System.currentTimeMillis()}.jpg")
    FileOutputStream(file).use { out ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
    }
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CropDiagnosisScreen(
    navController: NavController,
    cropDetectionViewModel: CropDetectionViewModel
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var inputPrompt by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val cropDiseaseInfo = cropDetectionViewModel.cropDiseaseInfo.collectAsState().value
    val isLoading = cropDetectionViewModel.isLoading.collectAsState().value
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Show bottom sheet when data is available
    LaunchedEffect(cropDiseaseInfo) {
        if (cropDiseaseInfo != null) {
            showBottomSheet = true
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            selectedImageUri = it
            processImage(context, it, cropDetectionViewModel)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            val uri = saveBitmapToCache(context, it)
            selectedImageUri = uri
            processImage(context, uri, cropDetectionViewModel)
        }
    }

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
                .fillMaxSize()
                .padding(top = 32.dp, start = 12.dp, end = 12.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                Image(
                    painter = painterResource(R.drawable.back_icon),
                    contentDescription = "back icon",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { navController.popBackStack() }
                )
            }

            Text("Diagnose Your Crop Health", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Text(
                "Upload images of your crop leaves to quickly identify diseases and pests.",
                fontSize = 16.sp,
                color = colorResource(R.color.text_green)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .shadow(4.dp, RoundedCornerShape(24.dp))
                    .background(colorResource(R.color.light_green), RoundedCornerShape(24.dp))
            ) {
                OutlinedTextField(
                    value = inputPrompt,
                    onValueChange = { inputPrompt = it },
                    placeholder = {
                        Text("|Describe your crop's condition...", color = colorResource(R.color.text_green))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 56.dp)
                        .align(Alignment.TopStart),
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    maxLines = 4
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .shadow(4.dp, RoundedCornerShape(24.dp))
                    .background(colorResource(R.color.light_green), RoundedCornerShape(24.dp))
                    .clickable {
                        if (!isLoading) {
                            galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }
                    }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.add_image_icon),
                        contentDescription = null,
                        modifier = Modifier.size(90.dp)
                    )
                    Text("Upload Image", fontSize = 16.sp, color = colorResource(R.color.text_green), fontWeight = FontWeight.Medium)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .shadow(4.dp, RoundedCornerShape(24.dp))
                    .background(colorResource(R.color.text_green), RoundedCornerShape(24.dp))
                    .clickable {
                        if (!isLoading) {
                            cameraLauncher.launch(null)
                        }
                    }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.camera_icon),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                    Text("Take a photo", fontSize = 16.sp, color = colorResource(R.color.white), fontWeight = FontWeight.Medium)
                }
            }
        }

        // Loading Indicator
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
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
                            "Analyzing your crop...",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = colorResource(R.color.text_green)
                        )
                    }
                }
            }
        }

        // Bottom Sheet Dialog
        if (showBottomSheet && cropDiseaseInfo != null) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
                containerColor = Color.White,
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Diagnosis Result",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.text_green)
                        )
                        IconButton(onClick = { showBottomSheet = false }) {
                            Icon(
                                painter = painterResource(R.drawable.back_icon),
                                contentDescription = "Close",
                                modifier = Modifier.size(24.dp),
                                tint = colorResource(R.color.text_green)
                            )
                        }
                    }

                    Divider(color = colorResource(R.color.text_green).copy(alpha = 0.2f), thickness = 1.dp)

                    // Disease Name - Prominent
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(R.color.text_green).copy(alpha = 0.1f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Disease Identified",
                                fontSize = 14.sp,
                                color = colorResource(R.color.text_green).copy(alpha = 0.7f),
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                cropDiseaseInfo.diseaseName,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.text_green),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    // Symptoms Section
                    InfoSection(
                        title = "Symptoms",
                        content = cropDiseaseInfo.symptoms,
                        icon = "🔍"
                    )

                    // Pest/Pathogen Section
                    InfoSection(
                        title = "Causative Agent",
                        content = cropDiseaseInfo.pestName,
                        icon = "🦠"
                    )

                    // Remedy Section - Highlighted
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(R.color.text_green)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text("💊", fontSize = 24.sp)
                                Text(
                                    "Recommended Treatment",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                            Text(
                                cropDiseaseInfo.remedy,
                                fontSize = 15.sp,
                                color = Color.White,
                                lineHeight = 22.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun InfoSection(title: String, content: String, icon: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.light_green)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(icon, fontSize = 22.sp)
                Text(
                    title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.text_green)
                )
            }
            Text(
                content,
                fontSize = 15.sp,
                color = colorResource(R.color.text_green).copy(alpha = 0.9f),
                lineHeight = 22.sp
            )
        }
    }
}

fun processImage(context: Context, uri: Uri, viewModel: CropDetectionViewModel) {
    try {
        val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
        val inputBuffer = convertBitmapToByteBuffer(resizedBitmap)
        val modelFile = loadModelFile(context, "PlantDiseaseModel.tflite")
        val interpreter = Interpreter(modelFile)
        val output = Array(1) { FloatArray(43) }
        interpreter.run(inputBuffer, output)
        val labels = context.assets.open("labels.txt").bufferedReader().use { it.readLines() }
        val topIndex = output[0].indices.maxByOrNull { output[0][it] } ?: -1
        val detectedDisease = if (topIndex != -1) labels.getOrElse(topIndex) { "Unknown" } else "Unknown"
        Log.d("ModelResult", "Detected disease: $detectedDisease")
        interpreter.close()
        viewModel.detectDisease(detectedDisease)
    } catch (e: Exception) {
        Log.e("ModelError", "Error running model: ${e.message}", e)
    }
}

private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
    val inputSize = 224
    val byteBuffer = ByteBuffer.allocateDirect(4 * inputSize * inputSize * 3)
    byteBuffer.order(ByteOrder.nativeOrder())
    val intValues = IntArray(inputSize * inputSize)
    bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
    for (pixelValue in intValues) {
        val r = ((pixelValue shr 16) and 0xFF) / 255.0f
        val g = ((pixelValue shr 8) and 0xFF) / 255.0f
        val b = (pixelValue and 0xFF) / 255.0f
        byteBuffer.putFloat(r)
        byteBuffer.putFloat(g)
        byteBuffer.putFloat(b)
    }
    byteBuffer.rewind()
    return byteBuffer
}