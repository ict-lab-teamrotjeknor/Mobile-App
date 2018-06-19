package com.hro.ictlab.ict_lab.retrofit

import com.google.gson.annotations.SerializedName
import java.util.*

class UserNotification(@SerializedName("message") val message: String,
                       @SerializedName("type") val type: String,
                   @SerializedName("time") val time: Date,
                   @SerializedName("read") var read: Boolean)
