package com.example.mymajor1.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class ActivityScheduleResponse(
    @SerializedName("sowing_date")
    val sowingDate: LocalDate,

    @SerializedName("irrigation_dates")
    val irrigationDates: List<LocalDate>,

    @SerializedName("fertilizer_dates")
    val fertilizerDates: List<LocalDate>,

    @SerializedName("harvest_date")
    val harvestDate: LocalDate,

    @SerializedName("crop_name")
    val cropName: String
)


