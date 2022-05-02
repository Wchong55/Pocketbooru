package com.wsc.pocketbooru

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject

class ImageManager {
    private val okHttpClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        builder.addInterceptor(loggingInterceptor)

        okHttpClient = builder.build()
    }

    fun retrieveSearchImages(search: String, page:Int, apiKey: String): List<SearchImages> {
        val searchTerm = search

        val request: Request = Request.Builder()
            //.url("https://danbooru.donmai.us/profile.json?api_key=$apiKey")
            .url("https://danbooru.donmai.us/posts.json?page=$page&tags=$searchTerm")
            .get()
            .build()

        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody: String? = response.body?.string()

        if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            val images = mutableListOf<SearchImages>()
            val jsonArray: JSONArray = JSONArray(responseBody)

            for (i in 0 until jsonArray.length()) {
                val curr: JSONObject = jsonArray.getJSONObject(i)
                Log.d("SearchActivity", curr.getString("id"))

                val url: String = curr.getString("file_url")
                val previewURL: String = curr.getString("preview_file_url")

                val image = SearchImages(
                    prevURL = previewURL,
                    URL = url
                )

                images.add(image)
            }

            return images
        }

        return listOf()
    }

}