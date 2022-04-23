package com.wsc.pocketbooru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val search: String? = getIntent().getStringExtra("SEARCH")

        val title: String = getString(R.string.search_title, search)
        setTitle(title)

        recyclerView = findViewById(R.id.recyclerview)

        var pageNum = 1

        val imagesManager = ImageManager()
        val apiKey = getString(R.string.danbooru_key)

        val images: List<SearchImages> = try {
            imagesManager.retrieveSearchImages(search.toString(), pageNum, apiKey)
        } catch(exception: Exception) {
            Log.e("SearchActivity", "Retrieving images failed", exception)
            listOf<SearchImages>()
        }

        runOnUiThread {
            if (images.isNotEmpty()) {
                val adapter = SearchImagesAdapter(images)
                recyclerView.setAdapter(adapter)
                recyclerView.layoutManager = LinearLayoutManager(this@SearchActivity)
            }
            else {
                Toast.makeText(
                    this@SearchActivity,
                    "Failed to retrieve Images!",
                    Toast.LENGTH_LONG).show()
            }
        }
        /*doAsync {
            val images: List<SearchImages> = try {
                imagesManager.retrieveSearchImages(search.toString(), pageNum, apiKey)
            } catch(exception: Exception) {
                Log.e("SearchActivity", "Retrieving images failed", exception)
                listOf<SearchImages>()
            }

            runOnUiThread {
                if (images.isNotEmpty()) {
                    //val adapter =
                }
            }
        }*/
    }
}