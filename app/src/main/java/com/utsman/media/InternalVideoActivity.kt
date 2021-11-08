package com.utsman.media

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class InternalVideoActivity : AppCompatActivity() {

    private val videoView: VideoView by lazy {
        findViewById(R.id.video_view)
    }

    private val btnPlay: Button by lazy {
        findViewById(R.id.btn_play)
    }

    private val btnPause: Button by lazy {
        findViewById(R.id.btn_pause)
    }

    private val btnStop: Button by lazy {
        findViewById(R.id.btn_stop)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internal_video)

        val pathVideo = "android.resource://$packageName/${R.raw.bumi}"
        videoView.setVideoURI(Uri.parse(pathVideo))

        btnPlay.setOnClickListener {
            videoView.start()
        }

        btnPause.setOnClickListener {
            videoView.pause()
        }

        btnStop.setOnClickListener {
            videoView.stopPlayback()
            videoView.setVideoURI(Uri.parse(pathVideo))
        }
    }
}