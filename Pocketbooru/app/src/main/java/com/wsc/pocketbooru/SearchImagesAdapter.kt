package com.wsc.pocketbooru

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
//import com.squareup.picasso.Picasso


class SearchImagesAdapter(val images: List<SearchImages>) : RecyclerView.Adapter<SearchImagesAdapter.ViewHolder>() {

    class ViewHolder(rootLayout: View) : RecyclerView.ViewHolder(rootLayout) {
        val image: TextView = rootLayout.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val rootLayout: View = layoutInflater.inflate(R.layout.image_squares, parent, false)

        val viewHolder = ViewHolder(rootLayout)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentImages = images[position]

        if (currentImages.url.isNotEmpty()) {
            //Picasso.get().setIndicatorsEnabled(true)

            /*Picasso
                .get()
                .load(currentImages.url)
                .into(holder.imageView)*/
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}