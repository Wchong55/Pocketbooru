package com.wsc.pocketbooru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val search: String? = getIntent().getStringExtra("SEARCH")

        val title: String = getString(R.string.search_title, search)
        setTitle(title)

        val imagesManager = ImageManager()
        val apiKey = getString(R.string.danbooru_key)

        doAsync {
            val images: List<SearchImages> = try {
                imagesManager.retrieveSearchImages(search.toString(), apiKey)
            } catch(exception: Exception) {
                Log.e("SearchActivity", "Retrieving images failed", exception)
                listOf<SearchImages>()
            }

            runOnUiThread {
                if (images.isNotEmpty()) {
                    val adapter =
                }
            }
        }
    }
}