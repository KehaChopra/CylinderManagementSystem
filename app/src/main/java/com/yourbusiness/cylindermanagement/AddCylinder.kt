package com.yourbusiness.cylindermanagement



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCylinderScreen(
    onBackClick: () -> Unit = {},
    onAddCylinder: (String, String, String, String, String, String) -> Unit = { _, _, _, _, _, _ -> }
) {
    val enAccent = Color(0xFF5E8BC7)

    var selectedGasType by remember { mutableStateOf("") }
    var selectedVolumeType by remember { mutableStateOf("") }
    var serialNumber by remember { mutableStateOf("") }
    var batchNumber by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf("") }
    var remarks by remember { mutableStateOf("") }

    var gasTypeExpanded by remember { mutableStateOf(false) }
    var volumeTypeExpanded by remember { mutableStateOf(false) }
    var statusExpanded by remember { mutableStateOf(false) }

    val gasTypes = listOf("Oxygen", "Nitrogen", "Argon", "CO2", "Helium")
    val volumeTypes = listOf("Small", "Medium", "Large", "Extra Large")
    val statusOptions = listOf("Active", "Inactive", "Under Maintenance", "Retired")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add Cylinder",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = enAccent
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Gas Type Dropdown
            Column {
                Text(
                    text = "Gas Type",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = gasTypeExpanded,
                    onExpandedChange = { gasTypeExpanded = it }
                ) {
                    OutlinedTextField(
                        value = selectedGasType,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Select Gas Type", color = Color.Gray) },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown"
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = gasTypeExpanded,
                        onDismissRequest = { gasTypeExpanded = false }
                    ) {
                        gasTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    selectedGasType = type
                                    gasTypeExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Volume Type Dropdown
            Column {
                Text(
                    text = "Volume Type",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = volumeTypeExpanded,
                    onExpandedChange = { volumeTypeExpanded = it }
                ) {
                    OutlinedTextField(
                        value = selectedVolumeType,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Select Volume Type", color = Color.Gray) },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown"
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = volumeTypeExpanded,
                        onDismissRequest = { volumeTypeExpanded = false }
                    ) {
                        volumeTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    selectedVolumeType = type
                                    volumeTypeExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Serial Number Input
            Column {
                Text(
                    text = "Serial Number",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = serialNumber,
                    onValueChange = { serialNumber = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
            }

            // Batch Number Input
            Column {
                Text(
                    text = "Batch Number",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = batchNumber,
                    onValueChange = { batchNumber = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
            }

            // Status Dropdown
            Column {
                Text(
                    text = "Status",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = statusExpanded,
                    onExpandedChange = { statusExpanded = it }
                ) {
                    OutlinedTextField(
                        value = selectedStatus,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = { Text("Select Status", color = Color.Gray) },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown"
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = statusExpanded,
                        onDismissRequest = { statusExpanded = false }
                    ) {
                        statusOptions.forEach { status ->
                            DropdownMenuItem(
                                text = { Text(status) },
                                onClick = {
                                    selectedStatus = status
                                    statusExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Remarks Input
            Column {
                Text(
                    text = "Remarks",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = remarks,
                    onValueChange = { remarks = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    maxLines = 5
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Add Cylinder Button
            Button(
                onClick = {
                    onAddCylinder(
                        selectedGasType,
                        selectedVolumeType,
                        serialNumber,
                        batchNumber,
                        selectedStatus,
                        remarks
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = enAccent
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Add Cylinder",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    }
}