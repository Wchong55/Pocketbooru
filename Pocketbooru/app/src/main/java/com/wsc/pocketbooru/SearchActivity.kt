package com.wsc.pocketbooru

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.doAsync

class SearchActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var nextBtn: Button
    private lateinit var prevBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val search: String? = getIntent().getStringExtra("SEARCH")

        val title: String = getString(R.string.search_title, search)
        setTitle(title)

        recyclerView = findViewById(R.id.recyclerview)
        nextBtn = findViewById(R.id.next_btn)
        prevBtn = findViewById(R.id.prev_btn)

        var pageNum = 1

        val imagesManager = ImageManager()
        val apiKey = getString(R.string.danbooru_key)

        prevBtn.isEnabled = false

        doAsync {
            val images: List<SearchImages> = try {
                imagesManager.retrieveSearchImages(search.toString(), pageNum, apiKey)
            } catch(exception: Exception) {
                Log.e("SearchActivity", "Retrieving images failed", exception)
                listOf<SearchImages>()
            }
            runOnUiThread {
                if (images.isNotEmpty()) {
                    val adapter = SearchImagesAdapter(images, this@SearchActivity)
                    recyclerView.layoutManager = GridLayoutManager(this@SearchActivity, 2)
                    recyclerView.setAdapter(adapter)
                }
                else {
                    Toast.makeText(
                        this@SearchActivity,
                        "Failed to retrieve Images!",
                        Toast.LENGTH_LONG).show()
                }
            }
        }

        prevBtn.setOnClickListener {
            doAsync {
                val images: List<SearchImages> = try {
                    imagesManager.retrieveSearchImages(search.toString(), pageNum-1, apiKey)
                } catch(exception: Exception) {
                    Log.e("SearchActivity", "Retrieving images failed", exception)
                    listOf<SearchImages>()
                }
                runOnUiThread {
                    pageNum = pageNum - 1
                    Toast.makeText(this@SearchActivity, "Page $pageNum", Toast.LENGTH_SHORT).show()
                    if (pageNum == 1) {
                        prevBtn.isEnabled = false
                    }
                    if (images.isNotEmpty()) {
                        val adapter = SearchImagesAdapter(images, this@SearchActivity)
                        recyclerView.layoutManager = GridLayoutManager(this@SearchActivity, 2)
                        recyclerView.setAdapter(adapter)
                    }
                    else {
                        Toast.makeText(
                            this@SearchActivity,
                            "Failed to retrieve Images!",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        nextBtn.setOnClickListener {
            doAsync {
                val images: List<SearchImages> = try {
                    imagesManager.retrieveSearchImages(search.toString(), pageNum+1, apiKey)
                } catch(exception: Exception) {
                    Log.e("SearchActivity", "Retrieving images failed", exception)
                    listOf<SearchImages>()
                }
                runOnUiThread {
                    pageNum = pageNum + 1
                    Toast.makeText(this@SearchActivity, "Page $pageNum", Toast.LENGTH_SHORT).show()
                    prevBtn.isEnabled = true

                    if (images.isNotEmpty()) {
                        val adapter = SearchImagesAdapter(images, this@SearchActivity)
                        recyclerView.layoutManager = GridLayoutManager(this@SearchActivity, 2)
                        recyclerView.setAdapter(adapter)
                    }
                    else {
                        Toast.makeText(
                            this@SearchActivity,
                            "Failed to retrieve Images!",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}