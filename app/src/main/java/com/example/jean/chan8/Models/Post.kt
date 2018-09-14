package com.example.jean.chan8.Models

import android.location.Location
import com.google.firebase.auth.FirebaseUser
import java.util.*

data class Post(val content: Content, val author: FirebaseUser?, val date: Date, val location: Location)