package com.hro.ictlab.ict_lab.retrofit

import com.google.gson.annotations.SerializedName

class AuthenticationResponse(@SerializedName("token") val token: String,
                             @SerializedName("Succeed") val succeed: Boolean,
                             @SerializedName("error") val error: String?)