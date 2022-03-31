package com.example.multithreadingdemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.multithreadingdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var timerIsStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            if (binding.editTime.text.isNotEmpty() && !timerIsStarted) {
                timerIsStarted = true
                lifecycleScope.launch {
                    val seconds = binding.editTime.text.toString().toInt()
                    for (i in 0..seconds) {
                        delay(1000)
                        binding.timer.text = i.toString()
                    }
                    timerIsStarted = false
                }
            }
        }

        binding.nextButton.run {
            setOnClickListener {
                startActivity(Intent(this@MainActivity, ThreadActivity::class.java))
            }
            text = context.getString(R.string.go_to_threads)
        }
    }
}