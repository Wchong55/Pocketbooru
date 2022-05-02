package com.wsc.pocketbooru

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class ImageActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var save_Btn: Button
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        firebaseDatabase = FirebaseDatabase.getInstance()

        val imageURL: String? = getIntent().getStringExtra("URL")
        val prevURL: String? = getIntent().getStringExtra("prevURL")
        val dir: String? = getIntent().getStringExtra("Username")
        val url: String = imageURL.toString()

        imageView = findViewById(R.id.image)
        save_Btn = findViewById(R.id.saveBtn)

        save_Btn.setOnClickListener {view: View ->
            Toast.makeText(this, "Clicked on save", Toast.LENGTH_SHORT).show()

            val reference = firebaseDatabase.getReference("images/$dir")

            val image = GalleryImages(
                prevURL = prevURL.toString(),
                URL = url
            )

            reference.push().setValue(image)
        }

        Picasso
            .get()
            .load(imageURL)
            .into(imageView)
    }
}