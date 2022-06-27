package com.mitul.countrypicker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mitul.countrypicker.databinding.ActivityMainBinding


class MainActivity1 : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

    }

}