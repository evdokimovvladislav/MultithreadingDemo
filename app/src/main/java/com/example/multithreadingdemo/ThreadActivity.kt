package com.example.multithreadingdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.multithreadingdemo.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class ThreadActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var timerIsStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            if (binding.editTime.text.isNotEmpty() && !timerIsStarted) {
                timerIsStarted = true
                val seconds = binding.editTime.text.toString().toInt()
                thread {
                    for (i in 0..seconds) {
                        Thread.sleep(1000)
                        runOnUiThread {
                            binding.timer.text = i.toString()
                        }
                    }
                    timerIsStarted = false
                }
            }
        }

        binding.nextButton.run {
            setOnClickListener {
                finish()
            }
            text = context.getString(R.string.go_to_coroutines)
        }
    }
}