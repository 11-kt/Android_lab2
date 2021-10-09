package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity1 : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private var isCounting = false
    private lateinit var textSecondsElapsed: TextView

    private var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            if (isCounting) {
                textSecondsElapsed.post {
                    textSecondsElapsed.text = ("Seconds elapsed: " + ++secondsElapsed)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onResume() {
        super.onResume()
        isCounting = true
    }

    override fun onPause() {
        super.onPause()
        isCounting = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(getString(R.string.secondsElapsed), secondsElapsed)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        secondsElapsed = savedInstanceState.getInt(getString(R.string.secondsElapsed))
    }

}
