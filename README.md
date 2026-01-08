# Buscador de Películas (ApkExmFinalPeliculas)

Aplicación Android nativa desarrollada en Kotlin que permite buscar películas utilizando la API de OMDb (Open Movie Database). Los usuarios pueden realizar búsquedas por título y ver detalles específicos de cada película seleccionada.

## 🚀 Características

- **Búsqueda en Tiempo Real:** Interfaz sencilla para buscar películas por su nombre.
- **Listado de Resultados:** Visualización de resultados en un `RecyclerView` con títulos, años y pósters.
- **Detalle de Película:** Pantalla dedicada que muestra información extendida:
    - Sinopsis (Plot)
    - Director
    - Género
    - Duración
    - País de origen
    - Imagen del póster a pantalla completa (proporcional).
- **Diseño Personalizado:** Fondo temático y componentes visuales redondeados.

## 🛠️ Tecnologías Utilizadas

- **Kotlin:** Lenguaje de programación principal.
- **Retrofit 2:** Cliente HTTP para la comunicación con la API REST de OMDb.
- **Gson:** Conversión de JSON a objetos Data Class de Kotlin.
- **Coil:** Librería moderna y ligera para la carga de imágenes mediante URLs.
- **ViewBinding:** Para una interacción segura y eficiente con los elementos del layout.
- **ConstraintLayout:** Para el diseño de interfaces flexibles y adaptables.

## 📦 Estructura del Proyecto

- `MainActivity.kt`: Punto de entrada que gestiona la búsqueda y el listado.
- `DetailActivity.kt`: Muestra la información detallada de una película mediante su ID de IMDb.
- `MovieAdapter.kt`: Adaptador para gestionar la visualización de elementos en el RecyclerView.
- `RetrofitClient.kt`: Configuración Singleton de Retrofit.
- `MovieApi.kt`: Definición de los endpoints de la API.
- `models/`: Clases de datos (`MovieResponse`, `MovieDetail`) para el mapeo de la API.

## 🔑 Configuración

La aplicación utiliza la API Key `1a65d88f`. En caso de querer cambiarla, se puede modificar en la constante `apiKey` dentro de `MainActivity.kt`.

## 📸 Capturas de Pantalla (Opcional)
*(Espacio para añadir capturas de la aplicación en funcionamiento)*

---
Desarrollado como proyecto final de práctica para el examen de Android.