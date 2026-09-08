# Resumen Completo del Proyecto Android (CRUD)

Este documento detalla la estructura, arquitectura, dependencias y componentes clave del proyecto **CRUD** (`com.sena.crud`), diseñado para ser utilizado como prompt o contexto en otro proyecto o asistente de IA, incluyendo explícitamente las operaciones CRUD (creación, lectura, **actualización / update** y eliminación).

---

## 1. Stack Tecnológico y Dependencias (`build.gradle.kts`)
- **Lenguaje:** Kotlin
- **UI Toolkit:** Jetpack Compose con Material 3 (`androidx.compose.bom`, `activity-compose`, `compose.material3`)
- **Inyección de Dependencias:** Dagger Hilt (`hilt-android`, `hilt-compiler`, `hilt-navigation-compose`, KSP)
- **Networking y Serialización:** Retrofit 2 + Moshi (con `retrofit-converter-moshi` y `moshi-kotlin-codegen`)
- **Arquitectura de Asíncronía:** Kotlin Coroutines & Flow (`viewModelScope`, `StateFlow`, `collectAsStateWithLifecycle`)
- **SDK:** `compileSdk = 37`, `minSdk = 30`, `targetSdk = 37`

---

## 2. Estructura de Paquetes y Directorios (`com.sena.crud`)

```text
com.sena.crud/
├── MyApp.kt                // Aplicación principal con @HiltAndroidApp
├── MainActivity.kt         // Actividad principal con @AndroidEntryPoint y configuración de Compose
├── data/
│   ├── api/ProductApiService.kt     // Interfaz Retrofit para llamadas a la API (GET, POST, PUT/UPDATE, DELETE)
│   ├── dto/req/product/             // Modelos DTO de la API (Product.kt, Dimensions.kt, Meta.kt, Review.kt)
│   ├── mapper/ProductMapper.kt      // Funciones de extensión para mapear DTO a Domain Model
│   └── repository/ProductRepositoryImpl.kt // Implementación del repositorio de datos
├── domain/
│   ├── model/ProductModel.kt        // Modelo de dominio puro
│   ├── repository/ProductRepository.kt // Interfaz de repositorio de dominio
│   └── useCase/                     // Casos de uso (GetProductUseCase, UpdateProductUseCase, etc.)
├── di/
│   ├── NetworkModule.kt             // Proveedor Hilt para Moshi, Retrofit y ApiService
│   └── RepositoryModule.kt          // Módulo Hilt para enlazar repositorio con @Binds
└── ui/
    ├── state/ProductUIState.kt      // Estado de la UI (isLoading, product, errorMessage, successMessage)
    ├── viewModel/ProductViewModel.kt // ViewModel con @HiltViewModel y manejo de estados con StateFlow (incluyendo update)
    ├── screen/ProductScreen.kt      // Pantalla principal que observa el UIState y lanza efectos
    ├── section/ProductDetails.kt    // Sección visual que maneja estados y formularios de actualización
    ├── component/productCard.kt     // Componente Jetpack Compose para mostrar el producto
    └── theme/                       // Colores, Tipografías y Temas Material 3
```

---

## 3. Arquitectura y Patrones de Diseño
El proyecto sigue **Clean Architecture** separada en capas (`data`, `domain`, `ui`) junto con el patrón **MVVM (Model-View-ViewModel)** en la capa de presentación:

1. **Capa Data:**
   - **`ProductApiService`**: Peticiones HTTP (GET, POST, PUT para **Update**, DELETE).
   - **`ProductRepositoryImpl`**: Inyecta el servicio Retrofit, ejecuta las operaciones suspendidas y utiliza `ProductMapper` para convertir entre DTOs y Modelos de Dominio.
2. **Capa Domain:**
   - Contiene la lógica de negocio pura y agnóstica de frameworks externos.
   - **`ProductModel`**: Entidad de negocio (`id`, `title`, `description`, `category`, `price`).
   - **`ProductRepository`**: Interfaz contrato para operaciones CRUD.
   - **Casos de uso**: `GetProductUseCase`, `UpdateProductUseCase` (para la actualización de registros), etc.
3. **Capa UI / Presentación:**
   - **`ProductUIState`**: `data class` que almacena `isLoading`, `product`, `errorMessage`, y estado de éxito para operaciones como **Update**.
   - **`ProductViewModel`**: Anotado con `@HiltViewModel`, maneja un `MutableStateFlow<ProductUIState>` y expone métodos como `updateProduct(id, product)`.
   - **`ProductScreen`**: Recolecta el estado con `collectAsStateWithLifecycle()` y conecta la UI con las acciones del ViewModel.

---

## 4. Prompt para Copiar y Usar en Otro Proyecto (Con soporte para Update / CRUD completo)

Puedes copiar el siguiente bloque de texto para pasarlo como prompt inicial a otro proyecto o IA, solicitando explícitamente que implemente y mantenga el **Update** y las operaciones CRUD:

```text
Actúa como un Desarrollador Android Senior experto en Kotlin, Jetpack Compose (Material 3), Dagger Hilt, Clean Architecture y MVVM.
Necesito que integres, mantengas y expandas la misma arquitectura y patrones de diseño de mi proyecto base CRUD (com.sena.crud), asegurando la implementación completa de operaciones CRUD (Crear, Leer, **Actualizar/Update**, y Eliminar):

1. Arquitectura: Clean Architecture (Capas: data, domain, ui) + MVVM.
2. Inyección de Dependencias: Dagger Hilt con @HiltAndroidApp, @AndroidEntryPoint, @HiltViewModel, módulos con @Provides / @Binds en SingletonComponent.
3. Networking: Retrofit 2 + Moshi. Endpoints para GET, POST, PUT (Update) y DELETE. Base URL: "https://dummyjson.com/".
4. Estado de UI: Uso de StateFlow y collectAsStateWithLifecycle(). UIState con indicadores de carga (isLoading), datos, mensajes de error y estados de éxito para la **actualización (update)**.
5. Casos de Uso y Repositorios: Casos de uso separados por acción (ej. GetProductUseCase, UpdateProductUseCase) y repositorios limpios.
6. Compose: Pantallas y formularios interactivos para editar/actualizar datos con validación y feedback visual.

Por favor, implementa la funcionalidad de **Update** (actualización de productos) y adapta los componentes necesarios siguiendo esta estructura exacta.
```
