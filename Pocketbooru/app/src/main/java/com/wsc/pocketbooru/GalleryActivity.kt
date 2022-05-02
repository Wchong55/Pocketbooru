package com.wsc.pocketbooru

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import org.jetbrains.anko.doAsync

class GalleryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var storage: FirebaseStorage

    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        firebaseDatabase = FirebaseDatabase.getInstance()

        recyclerView = findViewById(R.id.recyclerview)

        val directory: String? = getIntent().getStringExtra("Username")

        val imagesManager = ImageManager()
        //var storageRef: StorageReference = storage.reference
        //var userRef: StorageReference? = storageRef.child("$directory")

        /*var imageRef = userRef.child("$directory/images")

        val images: List<GalleryImages> = try {
            getImagesFromFirebase(directory.toString())
        } catch(exception: Exception) {
            Log.e("GalleryActivity", "Retrieving images failed", exception)
            listOf<GalleryImages>()
        }*/

        getImagesFromFirebase(directory.toString())
    }

    private fun getImagesFromFirebase(dir: String): List<GalleryImages> {
        val reference = firebaseDatabase.getReference("images/$dir")
        reference.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@GalleryActivity,
                    "Failed to retrieve images",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val images = mutableListOf<GalleryImages>()
                snapshot.children.forEach {childSnapshot: DataSnapshot ->
                    try {
                        val image = childSnapshot.getValue(GalleryImages::class.java)
                        if(image != null) {
                            images.add(image)
                        }
                    } catch (exception: Exception) {
                        Log.e("GalleryActivity", "Failed to read image", exception)
                    }
                }
                val adapter = GalleryImagesAdapter(images, this@GalleryActivity)
                recyclerView.layoutManager = GridLayoutManager(this@GalleryActivity, 2)
                recyclerView.setAdapter(adapter)
            }
        })

        return listOf()
    }
}