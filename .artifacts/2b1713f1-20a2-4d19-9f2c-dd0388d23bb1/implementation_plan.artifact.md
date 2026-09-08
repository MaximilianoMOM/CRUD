# Implementación de Funcionalidad Update (Actualización) en Proyecto CRUD

Este plan detalla los cambios necesarios para implementar la operación de **Actualización (Update)** en el proyecto Android siguiendo la arquitectura Clean Architecture, MVVM y Dagger Hilt existente.

## User Review Required

> [!IMPORTANT]
> Se añadirá el endpoint PUT de DummyJSON (`https://dummyjson.com/products/{id}`) para actualizar productos, junto con su respectivo UseCase, métodos en el Repositorio, ViewModel y UI en Compose.

## Open Questions

- ¿Deseas un formulario editable completo o un botón de prueba que actualice un campo específico (ej. título o precio) del producto actual? Se propondrá un botón de actualización directa y campos editables simples en `ProductDetails`.

## Proposed Changes

### Data Layer

#### [MODIFY] [ProductApiService.kt](file:///C:/Android/CRUD_ANDROID/app/src/main/java/com/sena/crud/data/remote/api/ProductApiService.kt)
- Añadir método suspendido con `@PUT("products/{id}")` para actualizar un producto enviando el objeto DTO o request body.

#### [MODIFY] [ProductRepositoryImpl.kt](file:///C:/Android/CRUD_ANDROID/app/src/main/java/com/sena/crud/data/repository/ProductRepositoryImpl.kt)
- Implementar `updateProduct(id: Int, product: ProductModel)` llamando a la API y mapeando el resultado.

### Domain Layer

#### [MODIFY] [ProductRepository.kt](file:///C:/Android/CRUD_ANDROID/app/src/main/java/com/sena/crud/domain/repository/ProductRepository.kt)
- Declarar la función `updateProduct(id: Int, product: ProductModel): ProductModel`.

#### [NEW] [UpdateProductUseCase.kt](file:///C:/Android/CRUD_ANDROID/app/src/main/java/com/sena/crud/domain/useCase/UpdateProductUseCase.kt)
- Crear el caso de uso `UpdateProductUseCase` inyectando `ProductRepository`.

### UI / Presentation Layer

#### [MODIFY] [ProductUIState.kt](file:///C:/Android/CRUD_ANDROID/app/src/main/java/com/sena/crud/ui/state/ProductUIState.kt)
- Añadir campos de estado para control de actualización (`isUpdating`, `updateSuccess`, `updateErrorMessage`).

#### [MODIFY] [ProductViewModel.kt](file:///C:/Android/CRUD_ANDROID/app/src/main/java/com/sena/crud/ui/viewModel/ProductViewModel.kt)
- Inyectar `UpdateProductUseCase` y añadir la función `updateProduct(id: Int, product: ProductModel)`.

#### [MODIFY] [ProductDetails.kt](file:///C:/Android/CRUD_ANDROID/app/src/main/java/com/sena/crud/ui/section/ProductDetails.kt)
- Añadir un botón o interfaz interactiva para probar la actualización de datos del producto.

## Verification Plan

### Automated Tests
- Compilación del proyecto (`gradle_build("app:assembleDebug")`).

### Manual Verification
- Desplegar la aplicación en un emulador o dispositivo y verificar la carga y actualización exitosa de un producto.
