package com.padmanavo.bookhub.activity

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RotateDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.padmanavo.bookhub.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        CoroutineScope(Dispatchers.Main).launch{
            findViewById<TextView>(R.id.txtSStext).animate().alpha(0f).setStartDelay(1000).duration = 1000
            findViewById<TextView>(R.id.txtSSsubtext).animate().alpha(0f).setStartDelay(1500).duration = 1000
            findViewById<ImageView>(R.id.imgSSicon).animate().scaleX(2f).scaleY(2f).setStartDelay(2500).alpha(0f).setDuration(2500)

            val drawable = findViewById<RelativeLayout>(R.id.layoutSplashScreen).background as GradientDrawable
            val animator = ObjectAnimator.ofInt(drawable, "level", 0, 10000)
            animator.duration = 5000
            animator.repeatCount = ObjectAnimator.INFINITE
            animator.interpolator = LinearInterpolator()
            animator.start()
            delay(5000)
            val intent = Intent(this@SplashScreen, LoginSignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}