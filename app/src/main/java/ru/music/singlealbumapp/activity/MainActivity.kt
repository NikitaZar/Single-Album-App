package ru.music.singlealbumapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.music.singlealbumapp.R
import ru.music.singlealbumapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

    }
}