package ru.music.singlealbumapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import ru.music.singlealbumapp.R
import ru.music.singlealbumapp.databinding.ActivityMainBinding
import ru.music.singlealbumapp.media.MediaLifecycleObserver
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    lateinit var mediaObserver: MediaLifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

    }
}