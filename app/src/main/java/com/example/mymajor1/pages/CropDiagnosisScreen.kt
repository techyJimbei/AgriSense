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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.mymajor1.R
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

    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CropDiagnosisScreen(
    navController: NavController
) {

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var inputPrompt by remember { mutableStateOf("") }

    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            selectedImageUri = it
            processImage(context, it)
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let {
            val uri = saveBitmapToCache(context, it)
            selectedImageUri = uri
            processImage(context, uri)
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(R.drawable.back_icon),
                    contentDescription = "back icon",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable(onClick = {
                            navController.popBackStack()
                        })
                )

            }

            Text(
                text = "Diagnose Your Crop Health",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "Upload images of your crop leaves to\nquickly identify diseases and pests.",
                fontSize = 16.sp,
                color = colorResource(R.color.text_green)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .shadow(4.dp, RoundedCornerShape(24.dp))
                    .background(colorResource(R.color.light_green), RoundedCornerShape(24.dp))
                    .clickable(
                        onClick = {}
                    )
            ) {
                OutlinedTextField(
                    value = inputPrompt,
                    onValueChange = { inputPrompt = it },
                    placeholder = {
                        Text(
                            text = "|Describe your crop’s condition...",
                            color = colorResource(R.color.text_green)
                        )
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
                    singleLine = false,
                    maxLines = 4
                )

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.BottomEnd)
                        .offset(x = (-8).dp, y = (-8).dp)
                        .background(
                            colorResource(R.color.text_green),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .clickable {},
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.send_icon),
                        contentDescription = "send icon",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .shadow(4.dp, RoundedCornerShape(24.dp))
                    .background(colorResource(R.color.light_green), RoundedCornerShape(24.dp))
                    .clickable(
                        onClick = {
                            galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }
                    )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.add_image_icon),
                        contentDescription = "add image icon",
                        modifier = Modifier.size(90.dp)
                    )

                    Text(
                        text = "Upload Image",
                        fontSize = 16.sp,
                        color = colorResource(R.color.text_green),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .shadow(4.dp, RoundedCornerShape(24.dp))
                    .background(colorResource(R.color.text_green), RoundedCornerShape(24.dp))
                    .clickable(
                        onClick = {
                            cameraLauncher.launch(null)
                        }
                    )
            ){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.camera_icon),
                        contentDescription = "take picture icon",
                        modifier = Modifier.size(80.dp)
                    )

                    Text(
                        text = "Take a photo",
                        fontSize = 16.sp,
                        color = colorResource(R.color.white),
                        fontWeight = FontWeight.Medium
                    )
                }

            }
        }
    }

}


fun processImage(context: Context, uri: Uri) {
    try {
        val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)

        // 2. Convert bitmap to ByteBuffer
        val inputBuffer = convertBitmapToByteBuffer(resizedBitmap)

        // 3. Load TFLite model
        val modelFile = loadModelFile(context, "PlantDiseaseModel.tflite")
        val interpreter = Interpreter(modelFile)

        // 4. Prepare output array [1,43]
        val output = Array(1) { FloatArray(43) }

        // 5. Run inference
        interpreter.run(inputBuffer, output)

        // 6. Load class labels from assets
        val labels = context.assets.open("labels.txt").bufferedReader().use { it.readLines() }

        // 7. Find top prediction
        val topIndex = output[0].indices.maxByOrNull { output[0][it] } ?: -1
        val confidence = if (topIndex != -1) output[0][topIndex] else 0f
        val detectedDisease = if (topIndex != -1) labels.getOrElse(topIndex) { "Unknown" } else "Unknown"

        Log.d("ModelResult", "Detected disease: $detectedDisease with confidence: $confidence")

        interpreter.close()
    } catch (e: Exception) {
        Log.e("ModelError", "Error running model: ${e.message}", e)
    }
}


private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
    val inputSize = 224
    val byteBuffer = ByteBuffer.allocateDirect(4 * inputSize * inputSize * 3) // float size = 4 bytes
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