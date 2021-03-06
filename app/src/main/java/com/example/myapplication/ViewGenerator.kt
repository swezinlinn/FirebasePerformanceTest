package com.example.myapplication

import android.widget.TextView
import com.mindorks.placeholderview.annotations.Layout
import com.mindorks.placeholderview.annotations.Resolve
import com.mindorks.placeholderview.annotations.View

@Layout(R.layout.layout_view)
class ViewGenerator(private val userData : Users){
    @View(R.id.textView1)
    lateinit var textView1: TextView
    @View(R.id.textView2)
    lateinit var textView2: TextView
    @View(R.id.textView3)
    lateinit var textView3: TextView

    @Resolve
    public fun onResolve(){
        textView1.text = userData.id.toString()
        textView2.text = userData.login
        textView3.text = userData.score.toString()
    }
}