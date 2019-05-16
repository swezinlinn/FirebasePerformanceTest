package com.example.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.TextView
import butterknife.BindView
import com.google.firebase.perf.metrics.Trace;
import butterknife.ButterKnife
import com.mindorks.placeholderview.PlaceHolderView
import com.google.firebase.perf.FirebasePerformance;
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.support.v7.widget.LinearLayoutManager
import android.widget.ImageView
import android.widget.Toast
import butterknife.OnClick
import com.bumptech.glide.Glide
import com.google.firebase.perf.metrics.AddTrace


class MainActivity : AppCompatActivity() {
    private var TAG : String? = MainActivity::class.simpleName
    private lateinit var trace : Trace

    @BindView(R.id.phv_user)
    lateinit var phvUser: PlaceHolderView
    @BindView(R.id.imageView2)
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        setView()
        getUsers()
        loadImage()
    }


    @AddTrace(name = "load_image_trace", enabled = true)
    private fun loadImage(){
        Glide.with(this)
            .load("https://i2.wp.com/www.siasat.com/wp-content/uploads/2018/03/Rosamund-Pike.jpeg?fit=600%2C421&ssl=1")
            .into(imageView)
    }

    @OnClick(R.id.button)
    fun onClick(){
        trace = FirebasePerformance.getInstance().newTrace("go_to_next_activity")
        trace.start()
        trace.incrementMetric("go_to_next_activity_counter",1)
        val intent = Intent(this,BrowseActivity::class.java)
        startActivity(intent)
        trace.stop()
    }

    private fun setView(){
        phvUser.builder
            .setHasFixedSize(false)
            .setItemViewCacheSize(10)
            .setLayoutManager(
                LinearLayoutManager(
                    this,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            )
    }

    private fun getUsers() {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/search/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(Api::class.java)
        val call = api.users
        trace = FirebasePerformance.getInstance().newTrace("load_user_list")
        trace.start()
        call.enqueue(object : Callback<UserList>{
            override fun onFailure(call: Call<UserList>, t: Throwable) {
                trace.incrementMetric("load_user_list_on_Failure",1)
            }

            override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                if(response.isSuccessful){
                    trace.incrementMetric("load_user_list_on_success",1)
                    for(i in 0 until response.body()!!.users!!.size){
                        phvUser.addView(ViewGenerator(response.body()!!.users!![i]) )
                    }
                }
            }
        })
        trace.stop()
    }
}
