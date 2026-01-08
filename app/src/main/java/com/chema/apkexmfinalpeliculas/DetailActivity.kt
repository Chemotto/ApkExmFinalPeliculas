package com.chema.apkexmfinalpeliculas

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.chema.apkexmfinalpeliculas.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Botón volver
        binding.btnBack.setOnClickListener {
            finish()
        }

        val imdbId = intent.getStringExtra("IMDB_ID")
        val apiKey = intent.getStringExtra("API_KEY") ?: ""

        if (imdbId != null && apiKey.isNotEmpty()) {
            fetchMovieDetail(imdbId, apiKey)
        } else {
            Toast.makeText(this, "Error cargando detalles", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchMovieDetail(imdbId: String, apiKey: String) {
        RetrofitClient.instance.getMovieDetails(apiKey, imdbId).enqueue(object : Callback<MovieDetail> {
            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                if (response.isSuccessful) {
                    val movie = response.body()
                    if (movie != null) {
                        updateUI(movie)
                    } else {
                         Toast.makeText(this@DetailActivity, "No se encontraron detalles", Toast.LENGTH_SHORT).show()
                    }
                } else {
                     Toast.makeText(this@DetailActivity, "Error en la respuesta: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                Toast.makeText(this@DetailActivity, "Fallo de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUI(movie: MovieDetail) {
        binding.tvDetailTitle.text = movie.title ?: "N/A"
        binding.tvDetailYear.text = movie.year ?: "N/A"
        binding.tvDetailPlot.text = movie.plot ?: "Sin sinopsis disponible"
        binding.tvDetailRuntime.text = movie.runtime ?: "N/A"
        binding.tvDetailDirector.text = movie.director ?: "N/A"
        binding.tvDetailGenre.text = movie.genre ?: "N/A"
        binding.tvDetailCountry.text = movie.country ?: "N/A"

        if (!movie.poster.isNullOrEmpty() && movie.poster != "N/A") {
            // Usando Coil para cargar la imagen en el detalle
            binding.ivPosterDetail.load(movie.poster) {
                crossfade(true)
            }
        }
    }
}
