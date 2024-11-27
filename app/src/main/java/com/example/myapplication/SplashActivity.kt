package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SplashActivity", "SplashActivity onCreate called") // Add log for confirmation
        setContentView(R.layout.activity_splash)
        Log.d("SplashActivity", "setContentView completed")
        splashStart();
    }

    private fun splashStart() {
        LongOperation().execute()
    }

    @SuppressLint("StaticFieldLeak")
    private open inner class LongOperation : AsyncTask<String?, Void?, String?>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: String?): String? {
            for (i in 0..6){
                try {
                    Thread.sleep(1000)
                }
                catch (e:Exception){
                    Thread.interrupted()
                }
            }
            return "result"
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val intent=Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(/* intent = */ intent)
            finish()
        }
    }
}