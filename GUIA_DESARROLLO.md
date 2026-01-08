# Guía de Desarrollo: Buscador de Películas

Este documento detalla los pasos seguidos para la creación de la aplicación "ApkExmFinalPeliculas" utilizando Kotlin y la API de OMDb.

## Paso 1: Configuración del Proyecto y Dependencias

1. Crear un nuevo proyecto en Android Studio con "Empty Views Activity".
2. En `build.gradle.kts (Module: app)`, habilitar **ViewBinding** y añadir las librerías necesarias:
   ```kotlin
   buildFeatures { viewBinding = true }

   dependencies {
       implementation("com.squareup.retrofit2:retrofit:2.9.0")
       implementation("com.squareup.retrofit2:converter-gson:2.9.0")
       implementation("io.coil-kt:coil:2.6.0")
       // ... otras dependencias por defecto
   }
   ```
3. Sincronizar el proyecto con Gradle.

## Paso 2: Permisos y Manifiesto

En `AndroidManifest.xml`, añadir el permiso de internet para permitir las llamadas a la API:
```xml
<uses-permission android:name="android.permission.INTERNET" />
<application
    ...
    android:usesCleartextTraffic="true">
    ...
</application>
```

## Paso 3: Modelos de Datos (Data Classes)

Crear las clases que representen la respuesta JSON de OMDb:
- `MovieResponse.kt`: Contiene la lista de películas (`Search`), el resultado y posibles errores.
- `Movie.kt`: Información básica (Título, Año, imdbID, Póster) para el listado.
- `MovieDetail.kt`: Información extendida para la pantalla de detalles.

## Paso 4: Capa de Red (Networking)

1. **MovieApi.kt**: Definir la interfaz con los métodos `@GET("/")` para buscar películas (`s`) y obtener detalles por ID (`i`).
2. **RetrofitClient.kt**: Configurar un objeto Singleton para inicializar Retrofit con la `BASE_URL = "https://www.omdbapi.com/"`.

## Paso 5: Diseño de Interfaces (Layouts)

1. **activity_main.xml**: Incluye un `EditText` para la búsqueda, un `Button` y un `RecyclerView`.
2. **item_movie.xml**: Diseño de cada fila del listado (Imagen a la izquierda, textos a la derecha).
3. **activity_detail.xml**: Diseño detallado con `ScrollView` que muestra el póster grande y toda la información técnica de la película.

## Paso 6: Implementación del Adaptador

Crear `MovieAdapter.kt` para:
- Inflar el layout `item_movie.xml`.
- Usar **Coil** para cargar la imagen del póster.
- Gestionar el clic en cada película para navegar al detalle.

## Paso 7: Lógica de las Actividades

### MainActivity.kt
- Configurar el `RecyclerView` con un `LinearLayoutManager`.
- Implementar el clic del botón buscar: llamar a `RetrofitClient.instance.searchMovies(...)`.
- En el `onResponse`, filtrar los resultados y cargarlos en el adaptador.
- Abrir `DetailActivity` pasando el `imdbID` y la `apiKey` mediante `intent.putExtra`.

### DetailActivity.kt
- Recuperar los extras del intent.
- Llamar a `RetrofitClient.instance.getMovieDetails(...)`.
- En el `onResponse`, mapear los datos del objeto `MovieDetail` a los `TextView` y cargar la imagen con Coil.
- Implementar el botón "Volver" con `finish()`.

## Paso 8: Recursos Adicionales
- Definir colores y strings en `res/values/strings.xml`.
- Crear fondos redondeados en `res/drawable/` para mejorar la estética visual.
