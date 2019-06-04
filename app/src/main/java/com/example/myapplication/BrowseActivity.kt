package com.example.myapplication

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import kotlinx.android.synthetic.main.activity_browse.*
import kotlinx.android.synthetic.main.content_browse.*


class BrowseActivity : AppCompatActivity() {
    private var outage_stage : OutageStage? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        val database = FirebaseDatabase.getInstance().reference
        val ref = database.child("outage_stage")

        setSupportActionBar(toolbar)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                outage_stage = dataSnapshot.getValue(OutageStage::class.java)

                txv_1.text = outage_stage?.flag.toString()
                txv_2.text = outage_stage?.message.toString()

                if(outage_stage?.flag!!){
                    txv_2.visibility = View.VISIBLE
                    imageView.visibility = View.VISIBLE
                }else{
                    txv_2.visibility = View.INVISIBLE
                    imageView.visibility = View.INVISIBLE
                }
                setImage()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    fun setImage(){
        Glide.with(this)
            .load(outage_stage?.url)
            .into(imageView)
    }

}
