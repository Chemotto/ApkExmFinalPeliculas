package com.chema.apkexmfinalpeliculas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.chema.apkexmfinalpeliculas.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val apiKey = "1a65d88f"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rvMovies.layoutManager = LinearLayoutManager(this)

        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                searchMovies(query)
            } else {
                Toast.makeText(this, getString(R.string.search_hint), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchMovies(query: String) {
        binding.progressBar.visibility = View.VISIBLE
        RetrofitClient.instance.searchMovies(apiKey, query).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val movieResponse = response.body()
                    if (movieResponse != null) {
                        if (movieResponse.response == "True") {
                            val movies = movieResponse.movies ?: emptyList()
                            val adapter = MovieAdapter(movies.filter { it.imdbId != null }) { imdbId ->
                                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                                intent.putExtra("IMDB_ID", imdbId)
                                intent.putExtra("API_KEY", apiKey)
                                startActivity(intent)
                            }
                            binding.rvMovies.adapter = adapter
                        } else {
                            val errorMessage = movieResponse.error ?: "No se encontraron resultados"
                            Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
                            binding.rvMovies.adapter = null
                        }
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
