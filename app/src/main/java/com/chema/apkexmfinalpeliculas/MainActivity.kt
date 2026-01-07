package com.chema.apkexmfinalpeliculas

import android.content.Intent
import android.os.Bundle
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
    // API Key configurada
    private var apiKey = "1a65d88f" 

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajuste para la barra de estado
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
                Toast.makeText(this, "Por favor ingrese un título", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchMovies(query: String) {
        RetrofitClient.instance.searchMovies(apiKey, query).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
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
                    } else {
                        Toast.makeText(this@MainActivity, "Respuesta vacía del servidor", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error en la consulta: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
