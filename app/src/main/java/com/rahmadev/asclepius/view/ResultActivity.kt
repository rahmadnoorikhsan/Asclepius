package com.rahmadev.asclepius.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahmadev.asclepius.adapter.ArticleAdapter
import com.rahmadev.asclepius.databinding.ActivityResultBinding
import com.rahmadev.asclepius.domain.model.Article
import com.rahmadev.asclepius.domain.model.Result
import com.rahmadev.asclepius.viewmodel.ResultViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var articleAdapter: ArticleAdapter
    private val viewModel by viewModels<ResultViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        @Suppress("DEPRECATION")
        val result = if (VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("result", Result::class.java)
        } else {
            intent.getParcelableExtra("result")
        }

        binding.apply {
            resultImage.setImageURI(result?.imageUri)
            resultText.text = result?.label
            resultScore.text = result?.score
        }

        viewModel.getArticles().observe(this@ResultActivity) {
            val data = it.data?.filter { article ->
                article.title != "[Removed]"
            }
            if (data != null) {
                setupRecycleArticle(data)
            }
        }
    }

    private fun setupRecycleArticle(articles: List<Article>) {
        articleAdapter = ArticleAdapter {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(intent)
        }
        binding.rvArticle.apply {
            adapter = articleAdapter
            layoutManager =
                LinearLayoutManager(this@ResultActivity, LinearLayoutManager.HORIZONTAL, false)
        }
        articleAdapter.submitList(articles)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}