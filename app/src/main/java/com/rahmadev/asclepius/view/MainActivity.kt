package com.rahmadev.asclepius.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.rahmadev.asclepius.R
import com.rahmadev.asclepius.databinding.ActivityMainBinding
import com.rahmadev.asclepius.domain.model.Result
import com.rahmadev.asclepius.helper.ImageClassifierHelper
import com.rahmadev.asclepius.viewmodel.MainViewModel
import com.yalantis.ucrop.UCrop
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.io.File
import java.text.NumberFormat

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private lateinit var result: Result
    private var currentImageUri: Uri? = null
    private val viewModel by viewModels<MainViewModel>()

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                val date = SystemClock.uptimeMillis()
                val outputUri = File(filesDir, "$date.jpg").toUri()
                val listUri = listOf(uri, outputUri)
                cropImage.launch(listUri)
            } else {
                showToast(getString(R.string.image_required))
            }
        }

    private val uCropContract = object : ActivityResultContract<List<Uri>, Uri>() {
        override fun createIntent(context: Context, input: List<Uri>): Intent {
            val inputUri = input[0]
            val outPut = input[1]

            val uCrop = UCrop.of(inputUri, outPut)
            return uCrop.getIntent(context)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri {
            val intentResult = intent ?: Intent()
            return UCrop.getOutput(intentResult)
                ?: Uri.parse("android.resource://$packageName/drawable/ic_place_holder")
        }
    }

    private val cropImage = registerForActivityResult(uCropContract) { uri ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            showToast(getString(R.string.image_required))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.apply {
            galleryButton.setOnClickListener {
                startGallery()
            }
            analyzeButton.setOnClickListener {
                if (currentImageUri != Uri.parse("android.resource://$packageName/drawable/ic_place_holder")) {
                    analyzeImage()
                } else {
                    showToast(getString(R.string.image_required))
                }
            }
        }
    }

    private fun startGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun showImage() {
        binding.previewImageView.setImageURI(currentImageUri)
    }

    private fun analyzeImage() {
        currentImageUri?.let { uri ->
            imageClassifierHelper = ImageClassifierHelper(
                context = this,
                classifierListener = object : ImageClassifierHelper.ClassifierListener {
                    override fun onError(error: String) {
                        runOnUiThread {
                            showToast(error)
                        }
                    }

                    override fun onResults(results: List<Classifications>?) {
                        runOnUiThread {
                            results?.let { classification ->
                                if (classification.isNotEmpty() && classification[0].categories.isNotEmpty()) {
                                    val label = classification[0].categories[0].label
                                    val score = NumberFormat.getPercentInstance()
                                        .format(classification[0].categories[0].score).trim()

                                    result = Result(
                                        imageUri = uri,
                                        label = label,
                                        score = score
                                    )

                                    viewModel.insertResult(result)
                                    moveToResult()
                                }
                            }
                        }
                    }
                }
            )
            imageClassifierHelper.classifyStaticImage(uri)
        }
    }

    private fun moveToResult() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("result", result)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(this, HistoryActivity::class.java))

        return super.onOptionsItemSelected(item)
    }
}