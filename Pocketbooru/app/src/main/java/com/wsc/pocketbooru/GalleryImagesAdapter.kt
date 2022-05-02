package com.wsc.pocketbooru

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class GalleryImagesAdapter(val images: List<GalleryImages>, val context: Context) : RecyclerView.Adapter<GalleryImagesAdapter.ViewHolder>() {

    class ViewHolder(rootLayout: View) : RecyclerView.ViewHolder(rootLayout) {
        val image: ImageView = rootLayout.findViewById(R.id.image)
        val card: CardView = rootLayout.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val rootLayout: View = layoutInflater.inflate(R.layout.image_squares, parent, false)

        val viewHolder = ViewHolder(rootLayout)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentImages = images[position]

        //holder.bind(currentImages.get(position), itemClickListener)

        if (currentImages.prevURL.isNotEmpty()) {
            Picasso.get().setIndicatorsEnabled(true)

            Picasso
                .get()
                .load(currentImages.prevURL)
                .into(holder.image)

            holder.card.setOnClickListener { view: View ->
                Toast.makeText(context, "Clicked on $position", Toast.LENGTH_SHORT).show()
                val intent: Intent = Intent(context, GalleryActivity::class.java)
                intent.putExtra("URL", currentImages.URL)
                intent.putExtra("prevURL", currentImages.prevURL)

                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}