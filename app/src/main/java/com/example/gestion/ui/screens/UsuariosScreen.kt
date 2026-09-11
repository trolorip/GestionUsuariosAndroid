package com.example.gestion.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items as gridItems
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestion.data.UsuarioRepository
import com.example.gestion.model.Usuario

@Composable
fun UsuariosScreen(
    onCerrarSesion: () -> Unit
) {

    var ordenarPor by remember {
        mutableStateOf("Nombre")
    }

    var menuExpandido by remember {
        mutableStateOf(false)
    }

    var tipoVista by remember {
        mutableStateOf("Tabla")
    }

    val usuariosOrdenados =
        if (ordenarPor == "Nombre") {

            UsuarioRepository.usuarios.sortedBy {
                it.nombre.lowercase()
            }

        } else {

            UsuarioRepository.usuarios.sortedBy {
                it.correo.lowercase()
            }
        }

    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {

            Text(
                text = "Usuarios registrados",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Total de usuarios: ${UsuarioRepository.usuarios.size}"
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // COMBO BOX
            Text(
                text = "Ordenar por:",
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Box {

                OutlinedButton(
                    onClick = {
                        menuExpandido = true
                    }
                ) {

                    Text(
                        text = ordenarPor
                    )
                }

                DropdownMenu(
                    expanded = menuExpandido,
                    onDismissRequest = {
                        menuExpandido = false
                    }
                ) {

                    DropdownMenuItem(
                        text = {
                            Text("Nombre")
                        },
                        onClick = {
                            ordenarPor = "Nombre"
                            menuExpandido = false
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Text("Correo")
                        },
                        onClick = {
                            ordenarPor = "Correo"
                            menuExpandido = false
                        }
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            // RADIO BUTTONS
            Text(
                text = "Visualización:",
                fontWeight = FontWeight.SemiBold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = tipoVista == "Tabla",
                    onClick = {
                        tipoVista = "Tabla"
                    }
                )

                Text("Tabla")

                Spacer(
                    modifier = Modifier.padding(8.dp)
                )

                RadioButton(
                    selected = tipoVista == "Grilla",
                    onClick = {
                        tipoVista = "Grilla"
                    }
                )

                Text("Grilla")
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            if (tipoVista == "Tabla") {

                TablaUsuarios(
                    usuarios = usuariosOrdenados,
                    modifier = Modifier.weight(1f)
                )

            } else {

                GrillaUsuarios(
                    usuarios = usuariosOrdenados,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Button(
                onClick = onCerrarSesion,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "CERRAR SESIÓN"
                )
            }
        }
    }
}

@Composable
fun TablaUsuarios(
    usuarios: List<Usuario>,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {

            Text(
                text = "Nombre",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Correo",
                modifier = Modifier.weight(1.5f),
                fontWeight = FontWeight.Bold
            )
        }

        HorizontalDivider()

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            items(usuarios) { usuario ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {

                    Text(
                        text = usuario.nombre,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = usuario.correo,
                        modifier = Modifier.weight(1.5f)
                    )
                }

                HorizontalDivider()
            }
        }
    }
}

@Composable
fun GrillaUsuarios(
    usuarios: List<Usuario>,
    modifier: Modifier = Modifier
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement =
            Arrangement.spacedBy(8.dp),
        verticalArrangement =
            Arrangement.spacedBy(8.dp)
    ) {

        gridItems(usuarios) { usuario ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            ) {

                Column(
                    modifier = Modifier.padding(12.dp)
                ) {

                    Text(
                        text = usuario.nombre,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = usuario.correo,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}