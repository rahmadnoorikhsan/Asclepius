package com.rahmadev.asclepius.view

import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rahmadev.asclepius.databinding.ActivityResultBinding
import com.rahmadev.asclepius.model.Result

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("result", Result::class.java)
        } else {
            intent.getParcelableExtra("result")
        }

        binding.apply {
            resultImage.setImageURI(result?.imageUri)
            resultText.text = "${result?.label} ${result?.score}"
        }
    }
}