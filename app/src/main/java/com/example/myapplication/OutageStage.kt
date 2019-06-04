package com.example.myapplication

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class OutageStage(
    val flag : Boolean?= null,
    val message : String?= "",
    val url : String?="")