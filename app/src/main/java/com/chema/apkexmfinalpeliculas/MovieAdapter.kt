package com.chema.apkexmfinalpeliculas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chema.apkexmfinalpeliculas.databinding.ItemMovieBinding

class MovieAdapter(
    private val movies: List<Movie>,
    private val onMovieClick: (String) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = movies.size

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.tvTitle.text = movie.title ?: "Sin título"
            binding.tvYear.text = movie.year ?: "Sin año"
            Glide.with(binding.root.context)
                .load(movie.poster)
                .into(binding.ivPoster)

            binding.root.setOnClickListener {
                movie.imdbId?.let { id -> onMovieClick(id) }
            }
        }
    }
}