package com.utsman.media

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    companion object {
        private const val MAX_STREAM = 1
        private const val PRIORITY = 1
    }

    private var soundId = 0
    private var soundLoaded = false

    private val audioAttributes: AudioAttributes by lazy {
        AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
    }

    private val soundPool: SoundPool by lazy {
        SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .setMaxStreams(MAX_STREAM)
            .build()
    }

    private val btnPlaySound: Button by lazy {
        findViewById(R.id.btn_play_sound)
    }

    private val btnPlayInternalVideo: Button by lazy {
        findViewById(R.id.btn_play_internal_video)
    }

    private val btnPlayInternetVideo: Button by lazy {
        findViewById(R.id.btn_play_internet_video)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        soundPool.setOnLoadCompleteListener { mSoundPool, i, i2 ->
            soundLoaded = true
        }

        soundId = soundPool.load(this, R.raw.destroy, PRIORITY)

        btnPlaySound.setOnClickListener {
            val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val actualVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat()
            val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC).toFloat()

            val volume = actualVolume / maxVolume
            if (soundLoaded) {
                soundPool.play(soundId, volume, volume, 1, 0, 1f)
            } else {
                Toast.makeText(this, "sound pool not loaded", Toast.LENGTH_SHORT).show()
            }
        }

        btnPlayInternalVideo.setOnClickListener {
            val intent = Intent(this, InternalVideoActivity::class.java)
            startActivity(intent)
        }

        btnPlayInternetVideo.setOnClickListener {
            val intent = Intent(this, InternetVideoActivity::class.java)
            startActivity(intent)
        }
    }
}