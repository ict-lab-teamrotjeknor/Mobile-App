package com.hro.ictlab.ict_lab.retrofit

import com.google.gson.annotations.SerializedName

class Reservation(@SerializedName("Name") val day: String,
                             @SerializedName("Hours") val hours: MutableList<Hour>)

class Hour(@SerializedName("HourId") val id: Int?,
           @SerializedName("Class") val classroom: String?,
           @SerializedName("Teacher") val teacher: String?,
           @SerializedName("Course") val course: String?,
           @SerializedName("SpecialReason") val reason: String?,
           @SerializedName("Reserved") val reserved: Boolean?)
