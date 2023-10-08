package com.test.core.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.test.core.R
import com.test.core.databinding.RvMovieBinding
import com.test.core.domain.model.MovieModel

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ListViewHolder>() {
    private var listData = ArrayList<MovieModel>()

    fun setData(newListData: List<MovieModel>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_movie, parent, false))

    override fun getItemCount() = if (listData.size > 10) 10 else listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = RvMovieBinding.bind(itemView)
        fun bind(data: MovieModel) {
            with(binding) {
                tvTitle.text = data.title
                tvDate.text = data.release_date
                Glide.with(itemView.context)
                    .load(itemView.context.getString(R.string.baseUrlImage, data.poster_path))
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.isVisible = false
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            dataSource: com.bumptech.glide.load.DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.isVisible = false
                            return false
                        }

                    })
                    .into(poster)
            }
        }
    }
}