package com.wsc.pocketbooru

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

    fun retrieveSearchImages(search: String, apiKey: String): List<SearchImages> {
        val search = search

        val request: Request = Request.Builder()
            //.url("https://danbooru.donmai.us/profile.json?api_key=$apiKey")
            .url("https://danbooru.donmai.us/posts?tags=$search")
            .get()
            .build()

        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody: String? = response.body?.string()

        if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            val images = mutableListOf<SearchImages>()
            val json: JSONObject = JSONObject(responseBody)
            val searches: JSONArray = json.getJSONArray("article")

            for (i in 0 until searches.length()) {
                val curr: JSONObject = searches.getJSONObject(i)

                val url: String = curr.getString("img src")

                val image = SearchImages(
                    url = url
                )

                images.add(image)
            }

            return images
        }

        return listOf()
    }
}