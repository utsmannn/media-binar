package com.utsman.media

import android.net.Uri
import android.os.Bundle
import android.webkit.URLUtil
import android.widget.Button
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class InternetVideoActivity : AppCompatActivity() {

    companion object {
        private const val PLAYBACK_TIME_KEY = "playback_time"
    }

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

    private val tvBuffer: TextView by lazy {
        findViewById(R.id.tv_buffer)
    }

    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_internet_video)

        val url = "https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_2mb.mp4"
        val uri = getMedia(url)

        videoView.setVideoURI(uri)

        val mediaController = MediaController(this)

        mediaController.setMediaPlayer(videoView)
        videoView.setMediaController(mediaController)

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(PLAYBACK_TIME_KEY)
        }

    }

    private fun startVideo() {
        videoView.setOnPreparedListener {
            tvBuffer.isVisible = false

            if (currentPosition > 0) {
                videoView.seekTo(currentPosition)
            } else {
                videoView.seekTo(1)
            }

            videoView.start()
        }

        videoView.setOnCompletionListener {
            Toast.makeText(this, "Complete", Toast.LENGTH_SHORT).show()
            videoView.seekTo(0)
        }
    }

    private fun getMedia(url: String): Uri {
        return if (URLUtil.isValidUrl(url)) {
            Uri.parse(url)
        } else {
            val localVideo = "android.resource://$packageName/${R.raw.bumi}"
            Uri.parse(localVideo)
        }
    }

    override fun onStart() {
        super.onStart()
        startVideo()
    }

    override fun onStop() {
        super.onStop()
        videoView.stopPlayback()
    }

    override fun onPause() {
        super.onPause()
        videoView.pause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(PLAYBACK_TIME_KEY, videoView.currentPosition)
    }
}