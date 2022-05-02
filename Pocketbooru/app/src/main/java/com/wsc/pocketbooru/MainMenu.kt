package com.wsc.pocketbooru

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

class MainMenu : AppCompatActivity() {

    private lateinit var search: EditText
    private lateinit var searchBtn: Button
    private lateinit var galleryBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        search = findViewById(R.id.search)
        searchBtn = findViewById(R.id.search_btn)
        galleryBtn = findViewById(R.id.saved_btn)

        val username: String? = getIntent().getStringExtra("Username")

        searchBtn.setOnClickListener {view: View ->
            Log.d("MainMenu", "Search clicked!")

            val searchTerm = search.getText().toString()

            val intent: Intent = Intent(this, SearchActivity::class.java)
            intent.putExtra("SEARCH", searchTerm)
            intent.putExtra("Username", username)
            startActivity(intent)
        }

        galleryBtn.setOnClickListener {view: View ->
            Log.d("MainMenu", "Gallery clicked!")

            val intent: Intent = Intent(this, GalleryActivity::class.java)
            intent.putExtra("USERNAME", username)
            startActivity(intent)
        }

        searchBtn.isEnabled = false
        search.addTextChangedListener(textWatcher)
    }

    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            val inputtedSearch: String = search.text.toString()

            val enableButton: Boolean = inputtedSearch.isNotBlank()

            searchBtn.isEnabled = enableButton
        }

        override fun afterTextChanged(p0: Editable?) {}

    }

}