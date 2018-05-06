package com.hro.ictlab.ict_lab.retrofit

import com.google.gson.annotations.SerializedName

class AuthenticationForm(@SerializedName("email") val email: String, @SerializedName("password") val password: String)