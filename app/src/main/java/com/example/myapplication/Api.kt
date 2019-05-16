package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET

interface Api {
    //urls
    @get:GET("users?q=rokano")
    val users: Call<UserList>
}