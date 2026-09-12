package com.sena.crud.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CrudNavBar(
    currentRoute: String,
    onNavigateToDetail: () -> Unit,
    onNavigateToList: () -> Unit,
    onNavigateToUpdate: () -> Unit,
    onNavigateToDelete: () -> Unit,
    onNavigateToCreate: () -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBarItem(
            selected = currentRoute == "create",
            onClick = onNavigateToCreate,
            icon = { Text("➕", style = MaterialTheme.typography.titleLarge) },
            label = { Text("Crear") }
        )
        NavigationBarItem(
            selected = currentRoute.startsWith("detail"),
            onClick = onNavigateToDetail,
            icon = { Text("🔍", style = MaterialTheme.typography.titleLarge) },
            label = { Text("ID") }
        )
        NavigationBarItem(
            selected = currentRoute == "list",
            onClick = onNavigateToList,
            icon = { Text("📋", style = MaterialTheme.typography.titleLarge) },
            label = { Text("Todos") }
        )
        NavigationBarItem(
            selected = currentRoute.startsWith("update"),
            onClick = onNavigateToUpdate,
            icon = { Text("✏️", style = MaterialTheme.typography.titleLarge) },
            label = { Text("Editar") }
        )
        NavigationBarItem(
            selected = currentRoute.startsWith("delete"),
            onClick = onNavigateToDelete,
            icon = { Text("🗑️", style = MaterialTheme.typography.titleLarge) },
            label = { Text("Borrar") }
        )
    }
}
