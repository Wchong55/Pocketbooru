package com.wsc.pocketbooru

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ImageAdapter(val images: List<SearchImages>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    class ViewHolder(rootLayout: View) : RecyclerView.ViewHolder(rootLayout) {
        val image: ImageView = rootLayout.findViewById(R.id.image)
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

        if (currentImages.URL.isNotEmpty()) {
            Picasso.get().setIndicatorsEnabled(true)

            Picasso
                .get()
                .load(currentImages.URL)
                .into(holder.image)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}