package com.jmzd.ghazal.foodappmvi.ui.detail.player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.jmzd.ghazal.foodappmvi.R
import com.jmzd.ghazal.foodappmvi.databinding.ActivityPlayerBinding
import com.jmzd.ghazal.foodappmvi.utils.VIDEO_ID
import com.jmzd.ghazal.foodappmvi.utils.YOUTUBE_API_KEY


@Suppress("DEPRECATION")
class PlayerActivity : YouTubeBaseActivity() {
    //Binding
    private var _binding: ActivityPlayerBinding? = null
    private val binding get() = _binding!!

    //Other
    private lateinit var player: YouTubePlayer
    //private lateinit var profileDialog: Dialog
    private var videoId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPlayerBinding.inflate(layoutInflater)
        //Full screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(binding.root)
        //Get id
        videoId = intent.getStringExtra(VIDEO_ID).toString()
        //Player initialize
        val listener = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer, p2: Boolean) {
                player = p1
                player.loadVideo(videoId)
                player.play()
            }

            override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {

            }
        }

        binding.videoPlayer.initialize(YOUTUBE_API_KEY, listener)

    }

    override fun onDestroy() {
        super.onDestroy()
        /*فقط در صورتی که قبلا initialize شده آزادش کن*/
        if (this::player.isInitialized)
            player.release()

        //if (this::profileDialog.isInitialized)
        //profileDialog.dismiss()
    }

}