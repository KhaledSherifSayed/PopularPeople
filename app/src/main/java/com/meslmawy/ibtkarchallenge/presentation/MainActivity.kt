package com.meslmawy.ibtkarchallenge.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.meslmawy.ibtkarchallenge.R
import com.meslmawy.ibtkarchallenge.extensions.applyExitMaterialTransform

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyExitMaterialTransform()
        setContentView(R.layout.activity_main)
    }
}