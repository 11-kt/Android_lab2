package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private var isCounting = false
    private lateinit var textSecondsElapsed: TextView
    private lateinit var preference: SharedPreferences

    private var backgroundThread = Thread {
        try {
            while (true) {
                Thread.sleep(1000)
                if (isCounting) {
                    textSecondsElapsed.post {
                        textSecondsElapsed.text = ("Seconds elapsed: " + ++secondsElapsed)
                    }
                }
            }
        } catch (E: InterruptedException) { Log.e("Exception", "InterruptedException") }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preference = getPreferences(MODE_PRIVATE)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        secondsElapsed = preference.getInt(getString(R.string.secondsElapsed), 0)
        backgroundThread.start()
    }

    override fun onResume() {
        super.onResume()
        isCounting = true
    }

    override fun onPause() {
        super.onPause()
        isCounting = false
        with(preference.edit()) {
            putInt(getString(R.string.secondsElapsed), secondsElapsed)
            apply()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        backgroundThread.interrupt()
    }

}
